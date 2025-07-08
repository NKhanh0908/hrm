package com.project.hrm.services.impl;

import com.project.hrm.dto.attendanceDTO.AttendanceResponseForPayrollDTO;
import com.project.hrm.dto.dayOffDTO.DayOffResponseForPayrollDTO;
import com.project.hrm.dto.payPeriodsDTO.PayPeriodsCreateDTO;
import com.project.hrm.dto.payrollComponentsDTO.PayrollComponentsDTO;
import com.project.hrm.dto.payrollsDTO.*;
import com.project.hrm.entities.*;
import com.project.hrm.enums.PayPeriodStatus;
import com.project.hrm.enums.PayrollComponentType;
import com.project.hrm.enums.ScaleOfTaxation;
import com.project.hrm.enums.SystemRegulationKey;
import com.project.hrm.mapper.PayPeriodMapper;
import com.project.hrm.mapper.PayrollsMapper;
import com.project.hrm.repositories.PayrollComponentsRepository;
import com.project.hrm.repositories.PayrollsRepository;
import com.project.hrm.services.*;
import com.project.hrm.specifications.PayrollsSpecifications;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class PayrollsServiceImpl implements PayrollsService {
    private final PayrollsRepository payrollsRepository;
    private final PayrollsMapper payrollsMapper;
    private final EmployeeService employeeService;
    private final PayPeriodsService payPeriodsService;
    private final PayPeriodMapper payPeriodMapper;
    private final PayrollCalculationService payrollCalculationService;


    /**
     * Creates a new {@link Payrolls} entity based on the given {@link PayrollsCreateDTO}.
     *
     * @param payrollsCreateDTO the DTO containing necessary data to create a payroll
     * @return the created {@link PayrollsDTO}
     */
    @Transactional
    @Override
    public PayrollsDTO create(PayrollsCreateDTO payrollsCreateDTO) {
        log.info("Create PayrollsDTO");
        Payrolls payrolls = payrollsMapper.toPayrollsFromCreateDTO(payrollsCreateDTO);

        payrolls.setEmployee(employeeService.getEntityById(payrollsCreateDTO.getEmployeeId()));

        if (payPeriodsService.getPayPeriodsByDate(payrollsCreateDTO.getStartDate(), payrollsCreateDTO.getEndDate()) != null) {
            payrolls.setPayPeriod(payPeriodsService.getPayPeriodsByDate(payrollsCreateDTO.getStartDate(), payrollsCreateDTO.getEndDate()));
        } else {
            PayPeriodsCreateDTO payPeriodsCreateDTO = new PayPeriodsCreateDTO();
            payPeriodsCreateDTO.setStartDate(payrollsCreateDTO.getStartDate());
            payPeriodsCreateDTO.setEndDate(payrollsCreateDTO.getEndDate());
            payPeriodsCreateDTO.setStatus(PayPeriodStatus.valueOf("OPEN"));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'T'MM-yyyy");
            String formatted = payPeriodsCreateDTO.getStartDate().format(formatter);

            payPeriodsCreateDTO.setPayPeriodCode(formatted);

            PayPeriods payPeriods = payPeriodMapper.toPayPeriods(payPeriodsService.create(payPeriodsCreateDTO));

            payrolls.setPayPeriod(payPeriods);
        }

        return payrollsMapper.toPayrollsDTO(payrollsRepository.save(payrolls));
    }

    @Override
    public Map<Integer, Payrolls> createWithPeriodAndEmployee(List<PayrollsCreateDTO> payrollsCreateDTO, Map<Integer, Employees> employeesList, PayPeriods payPeriod) {
        log.info("Create Payrolls with Employees for PayPeriods");
        Map<Integer, Payrolls> payrollsMap = new HashMap<>();

        List<Payrolls> payrolls = payrollsCreateDTO.stream()
                .map(dto -> {
                    Payrolls payroll = payrollsMapper.toPayrollsFromCreateDTO(dto);
                    Employees employee = employeesList.get(dto.getEmployeeId());
                    if (employee != null) {
                        payroll.setEmployee(employee);
                        payroll.setPayPeriod(payPeriod);
                    }
                    return payroll;
                })
                .filter(payroll -> payroll.getEmployee() != null) // Lọc bỏ các payroll không có employee hợp lệ
                .toList();

        for (Payrolls payroll : payrolls) {
            Integer employeeId = payroll.getEmployee().getId();
            payrollsMap.put(employeeId, payroll);
        }

        return payrollsMap;
    }

    /**
     * Updates an existing {@link Payrolls} entity using the provided {@link PayrollsUpdateDTO}.
     * Performs netSalary calculation if not explicitly provided.
     *
     * @param payrollsUpdateDTO the DTO containing updated payroll information
     * @return the updated {@link PayrollsDTO}
     * @throws EntityNotFoundException if the payroll or any referenced entity is not found
     */
    @Transactional
    @Override
    public PayrollsDTO update(PayrollsUpdateDTO payrollsUpdateDTO) {
        log.info("Update PayrollsDTO");

        Payrolls payrolls = getEntityById(payrollsUpdateDTO.getId());

        if (payrollsUpdateDTO.getEmployeeId() != null && employeeService.checkExists(payrollsUpdateDTO.getEmployeeId())) {
            payrolls.setEmployee(employeeService.getEntityById(payrollsUpdateDTO.getEmployeeId()));
        }

        if (payrollsUpdateDTO.getPayPeriodId() != null && payPeriodsService.checkExistence(payrollsUpdateDTO.getPayPeriodId())) {
            payrolls.setPayPeriod(payPeriodsService.getEntityById(payrollsUpdateDTO.getPayPeriodId()));
        }

        if (payrollsUpdateDTO.getStatus() != null){
            payrolls.setStatus(payrollsUpdateDTO.getStatus());
        }

        return payrollsMapper.toPayrollsDTO(payrollsRepository.save(payrolls));
    }

    /**
     * Deletes a {@link Payrolls} entity by its ID.
     *
     * @param Id the ID of the payroll to delete
     * @throws EntityNotFoundException if the payroll is not found
     */
    @Transactional
    @Override
    public void delete(Integer Id) {
        log.info("Delete PayrollsDTO");
        if (checkExistence(Id)) {
            payrollsRepository.deleteById(Id);
        } else {
            throw new EntityNotFoundException("Payrolls not found with id: " + Id);
        }

    }

    /**
     * Checks whether a {@link Payrolls} entity exists by its ID.
     *
     * @param Id the ID to check
     * @return true if the payroll exists, false otherwise
     */
    @Transactional(readOnly = true)
    @Override
    public Boolean checkExistence(Integer Id) {
        log.info("Check existence of Payrolls with Id: {}", Id);
        return payrollsRepository.existsById(Id);
    }

    /**
     * Retrieves a {@link PayrollsDTO} by its ID.
     *
     * @param id the ID of the payroll to retrieve
     * @return the corresponding {@link PayrollsDTO}
     * @throws EntityNotFoundException if the payroll is not found
     */
    @Transactional(readOnly = true)
    @Override
    public PayrollsDTO getById(Integer id) {
        log.info("Get PayrollsDTO By Id: {}", id);
        return payrollsMapper.toPayrollsDTO(getEntityById(id));
    }

    /**
     * Retrieves the {@link Payrolls} entity directly by ID.
     *
     * @param id the ID of the payroll
     * @return the corresponding {@link Payrolls} entity
     * @throws EntityNotFoundException if not found
     */
    @Transactional(readOnly = true)
    @Override
    public Payrolls getEntityById(Integer id) {
        log.info("Get Payrolls By Id: {}", id);
        return payrollsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payrolls Not Found By Id: " + id));
    }

    /**
     * Filters payroll records based on criteria specified in the {@link PayrollsFilter}.
     *
     * @param payrollFilter filter object containing search conditions
     * @param page          the page number (zero-based)
     * @param size          number of records per page
     * @return a list of matching {@link PayrollsDTO}
     */
    @Transactional(readOnly = true)
    @Override
    public List<PayrollsDTO> filter(PayrollsFilter payrollFilter, int page, int size) {
        log.info("Filter PayrollsDTO");

        Specification<Payrolls> payrollsSpecification = PayrollsSpecifications.filter(payrollFilter);

        Pageable pageable = PageRequest.of(page, size);

        return payrollsMapper.convertToPageEntityToPageDTO(payrollsRepository.findAll(payrollsSpecification, pageable));
    }

    /**
     * Filters payrolls based on income, deduction, and net salary ranges.
     *
     * @param payrollsFilterWithRange attribute for search
     * @param page          the page number (zero-based)
     * @param size          number of records per page
     * @return a list of {@link PayrollsDTO} within the given ranges
     */
    @Transactional(readOnly = true)
    @Override
    public List<PayrollsDTO> filterWithRange(PayrollsFilterWithRange payrollsFilterWithRange, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<Payrolls> payrollsSpecification = PayrollsSpecifications.filterWithRange(payrollsFilterWithRange);

        return payrollsMapper.convertToPageEntityToPageDTO(payrollsRepository.findAll(payrollsSpecification, pageable));
    }

    @Transactional
    @Override
    public PayrollsResponseDTO createPayrollForEmployee(PayrollsCreateDTO payrollsCreateDTO) {
        return payrollCalculationService.createPayrollForEmployee(payrollsCreateDTO);
    }

    @Override
    public List<PayrollsResponseDTO> createPayrollsForDepartment(Integer departmentId, PayrollsCreateDTO payrollsCreateDOTTemplate) {
        return payrollCalculationService.createBatchPayrollsByDepartment(departmentId, payrollsCreateDOTTemplate);
    }

    @Override
    public List<PayrollsResponseDTO> createPayrollsForAllEmployee(PayrollsCreateDTO payrollsCreateDTOTemplate) {
        return payrollCalculationService.createBatchPayrollsForAllEmployees(payrollsCreateDTOTemplate);
    }

}
