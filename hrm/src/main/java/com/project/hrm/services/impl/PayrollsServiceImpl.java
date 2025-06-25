package com.project.hrm.services.impl;

import com.project.hrm.dto.payrollsDTO.*;
import com.project.hrm.entities.Payrolls;
import com.project.hrm.mapper.PayrollsMapper;
import com.project.hrm.repositories.PayrollsRepository;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.services.PayPeriodsService;
import com.project.hrm.services.PayrollsService;
import com.project.hrm.specifications.PayrollsSpecifications;
import com.project.hrm.utils.IdGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PayrollsServiceImpl implements PayrollsService {
    private final PayrollsRepository payrollsRepository;
    private final PayrollsMapper payrollsMapper;
    private final EmployeeService employeeService;
    private final PayPeriodsService payPeriodsService;


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

        payrolls.setId(IdGenerator.getGenerationId());

        payrolls.setEmployee(employeeService.getEntityById(payrolls.getEmployee().getId()));

        payrolls.setPayPeriod(payPeriodsService.getEntityById(payrolls.getPayPeriod().getId()));

        return payrollsMapper.toPayrollsDTO(payrollsRepository.save(payrolls));
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

        if (payrollsUpdateDTO.getTotalIncome() != null && payrollsUpdateDTO.getTotalIncome().compareTo(BigDecimal.ZERO) > 0) {
            payrolls.setTotalIncome(payrollsUpdateDTO.getTotalIncome());
        }

        if (payrollsUpdateDTO.getTotalDeduction() != null && payrollsUpdateDTO.getTotalDeduction().compareTo(BigDecimal.ZERO) > 0) {
            payrolls.setTotalIncome(payrollsUpdateDTO.getTotalIncome());
        }

        if (payrollsUpdateDTO.getStatus() != null){
            payrolls.setStatus(payrollsUpdateDTO.getStatus());
        }

        if (payrollsUpdateDTO.getNetSalary() != null && payrollsUpdateDTO.getNetSalary().compareTo(BigDecimal.ZERO) > 0) {
            payrolls.setNetSalary(payrollsUpdateDTO.getNetSalary());
        } else {
            BigDecimal income = payrollsUpdateDTO.getTotalIncome();
            BigDecimal deduction = payrollsUpdateDTO.getTotalDeduction();

            if (income != null && deduction != null) {
                payrolls.setNetSalary(income.subtract(deduction));
            } else {
                log.warn("Cannot calculate netSalary due to missing totalIncome or totalDeduction");
                payrolls.setNetSalary(BigDecimal.ZERO); // hoặc giữ nguyên giá trị cũ nếu muốn
            }
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
        log.info("Get ParollsDTO By Id: {}", id);
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
        log.info("Get Parolls By Id: {}", id);
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
}
