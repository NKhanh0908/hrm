package com.project.hrm.services.impl;

import com.project.hrm.dto.attendanceDTO.AttendanceResponseForPayrollDTO;
import com.project.hrm.dto.dayOffDTO.DayOffResponseForPayrollDTO;
import com.project.hrm.dto.payPeriodsDTO.PayPeriodsCreateDTO;
import com.project.hrm.dto.payrollComponentsDTO.PayrollComponentsDTO;
import com.project.hrm.dto.payrollComponentsDTO.PayrollComponentsFilter;
import com.project.hrm.dto.payrollsDTO.*;
import com.project.hrm.entities.PayPeriods;
import com.project.hrm.entities.PayrollComponents;
import com.project.hrm.entities.Payrolls;
import com.project.hrm.entities.SystemRegulation;
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
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class PayrollsServiceImpl implements PayrollsService {
    private final PayrollsRepository payrollsRepository;
    private final PayrollsMapper payrollsMapper;
    private final EmployeeService employeeService;
    private final PayPeriodsService payPeriodsService;
    private final PayPeriodMapper payPeriodMapper;
    private final ContractService contractService;
    private final AttendanceService attendanceService;
    private final DayOffService dayOffService;
    private final SystemRegulationService systemRegulationService;
    private final PayrollComponentsService payrollComponentsService;
    private final DependentService dependentService;
    private final PayrollComponentsRepository payrollComponentsRepository;


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
    public PayrollsResponseDTO getPayrollForEmployee(PayrollsCreateDTO payrollsCreateDTO) {
        log.info("Get Payroll For Employee ID: {}", payrollsCreateDTO.getEmployeeId());

        PayrollsDTO payrollsDTO = create(payrollsCreateDTO);

        Double baseSalary = contractService.getCurrentActiveContract(payrollsCreateDTO.getEmployeeId()).getBaseSalary();

        PayPeriods payPeriods = payPeriodsService.getEntityById(payrollsDTO.getPayPeriodId());

        float summaryRegularTime = attendanceService.getTotalRegularTimeAttendanceByPayPeriodsForEmployee(payrollsDTO.getEmployeeId(), payPeriods);
        float summaryOverTime = attendanceService.getTotalOverTimeAttendanceByPayPeriodsForEmployee(payrollsDTO.getEmployeeId(), payPeriods);

        int dayOff = dayOffService.countDayOffByEmployeeId(payrollsDTO.getEmployeeId(), payPeriods.getStartDate(), payPeriods.getEndDate());
        int dayOffNotAccept = dayOffService.countDayOffByEmployeeIdStatus(payrollsDTO.getEmployeeId(), payPeriods.getStartDate(), payPeriods.getEndDate());

        List<PayrollComponentsDTO>  payrollComponentsDTOList = payrollComponentsService.createComponents(getEntityById(payrollsDTO.getPayPeriodId()));

        List<PayrollComponentsDTO> payrollComponentsDTOAdditionList = payrollComponentsDTOList.stream()
                .filter(payrollComponentsDTO ->
                        payrollComponentsDTO.getType()
                                .equals(PayrollComponentType.SUBSIDY) ||
                        payrollComponentsDTO.getType()
                                .equals(PayrollComponentType.REWARD))
                .toList();
        List<PayrollComponentsDTO> payrollComponentsDTODeductionList = payrollComponentsDTOList.stream()
                .filter(payrollComponentsDTO ->
                        payrollComponentsDTO.getType()
                                .equals(PayrollComponentType.DEDUCTION) ||
                        payrollComponentsDTO.getType()
                                .equals(PayrollComponentType.TAX) ||
                        payrollComponentsDTO.getType()
                                .equals(PayrollComponentType.INSURANCE))
                .toList();

        //Received by attendance, reward and subsidy
        BigDecimal totalAmountReceived = totalAmountReceived(baseSalary, summaryRegularTime, summaryOverTime, payrollComponentsDTOAdditionList);

        //Deduction by disciplinary action
        BigDecimal totalAmountDisciplinary = calculationDeductionComponents(payrollComponentsDTODeductionList,baseSalary);

        //Deduction by Insurance
        BigDecimal totalAmountInsurance = totalAmountInsurance(payrollComponentsDTODeductionList,baseSalary);


        //Tax Person income
        //Total income and use calculate tax
        BigDecimal totalIncome = totalAmountReceived.subtract(totalAmountDisciplinary);

        //Deduction for tax
        int countDependent = dependentService.countDependentsOfEmployee(payrollsDTO.getEmployeeId());
        BigDecimal dependentDeduction = BigDecimal.valueOf(
                Long.parseLong(systemRegulationService.getValue(SystemRegulationKey.DEPENDENT_DEDUCTION))
        );

        BigDecimal totalDeductionForTaxByDependent = BigDecimal.valueOf(countDependent).multiply(dependentDeduction);

        BigDecimal totalDeductionForSelfEmployee = BigDecimal.valueOf(
                Long.parseLong(systemRegulationService.getValue(SystemRegulationKey.SELF_DEDUCTION))
        );

        BigDecimal totalDeductionForTax = totalDeductionForTaxByDependent.add(totalDeductionForSelfEmployee);

        //Deduction by Tax
        BigDecimal totalAmountTax = BigDecimal.ZERO;
        if (totalIncome.subtract(totalDeductionForTax).compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal totalIncomeForTax = totalIncome.subtract(totalDeductionForTax);
            totalAmountTax = totalAmountTaxPersonalIncomeTax(totalIncomeForTax, payrollsDTO.getId());
        }

        //Net salary
        BigDecimal netSalary = totalIncome.subtract(totalAmountTax.add(totalAmountInsurance));

        //Entity for response
        AttendanceResponseForPayrollDTO attendanceResponseForPayrollDTO = new AttendanceResponseForPayrollDTO(summaryRegularTime, summaryOverTime);
        DayOffResponseForPayrollDTO dayOffResponseForPayrollDTO = new DayOffResponseForPayrollDTO(dayOff, dayOffNotAccept);

        return new PayrollsResponseDTO(
                payrollsDTO.getId(),
                payrollsDTO.getEmployeeId(),
                payrollsDTO.getPayPeriodId(),
                baseSalary,
                payrollComponentsDTOAdditionList,
                payrollComponentsDTODeductionList,
                attendanceResponseForPayrollDTO,
                dayOffResponseForPayrollDTO,
                countDependent,
                totalDeductionForTaxByDependent,
                totalAmountReceived,
                totalAmountDisciplinary,
                totalAmountInsurance,
                totalAmountTax,
                netSalary
        );
    }

    private BigDecimal totalAmountTaxPersonalIncomeTax(BigDecimal taxableIncome, Integer payrollId) {
        BigDecimal totalTax = BigDecimal.ZERO;
        for (ScaleOfTaxation level : ScaleOfTaxation.values()) {
            totalTax = totalTax.add(level.taxCalculation(taxableIncome));
        }

        PayrollComponents payrollComponents = payrollComponentsService.getPayrollIdAndType(payrollId, PayrollComponentType.TAX);
        payrollComponents.setAmount(totalTax);
        payrollComponentsRepository.save(payrollComponents);

        return totalTax;
    }

    private BigDecimal totalAmountReceived(Double baseSalary, float summaryRegularTime, float summaryOverTime, List<PayrollComponentsDTO>  payrollComponentsDTOList){
        log.info("Summary Total Income");
        Double totalStandardHoursInMonth = Double.valueOf(systemRegulationService.getValue(SystemRegulationKey.WORKDAYS_PER_MONTH));
        double hourlyRate = baseSalary / totalStandardHoursInMonth;

        //Total charge by the hour worked
        BigDecimal totalIncomeForRegularTime = BigDecimal.valueOf(hourlyRate * summaryRegularTime);

        double overTimeRate = Double.parseDouble(systemRegulationService.getValue(SystemRegulationKey.OVERTIME_RATE));
        BigDecimal totalIncomeForOverTime = BigDecimal.valueOf(hourlyRate * summaryOverTime * overTimeRate);

        //Total fees according to bonuses and allowances
        BigDecimal totalSubsidyAndRewardIncome = calculationSubsidyAndRewardComponents(payrollComponentsDTOList, baseSalary);

        return totalIncomeForRegularTime.add(totalSubsidyAndRewardIncome.add(totalIncomeForOverTime));
    }


    private BigDecimal totalAmountInsurance(List<PayrollComponentsDTO> payrollComponentsDTODeductionList, Double baseSalary){
        log.info("Summary Insurance Total");

        BigDecimal totalInsurance = BigDecimal.ZERO;

        for (PayrollComponentsDTO payrollComponent : payrollComponentsDTODeductionList) {
            if (payrollComponent.getType().equals(PayrollComponentType.INSURANCE)) {
                totalInsurance = getBigDecimal(baseSalary, totalInsurance, payrollComponent);
            }
        }
        return totalInsurance;
    }

//    private BigDecimal totalAmountTax(List<PayrollComponentsDTO> payrollComponentsDTODeductionList, Double baseSalary){
//        log.info("Summary Tax Total");
//
//        BigDecimal totalTax = BigDecimal.ZERO;
//
//        for (PayrollComponentsDTO payrollComponent : payrollComponentsDTODeductionList) {
//            if (payrollComponent.getType().equals(PayrollComponentType.TAX)) {
//                totalTax = getBigDecimal(baseSalary, totalTax, payrollComponent);
//            }
//        }
//        return totalTax;
//    }


    private BigDecimal calculationSubsidyAndRewardComponents(List<PayrollComponentsDTO> payrollComponentsDTOList, Double baseSalary) {
        log.info("Calculation Subsidy and Reward Components");
        BigDecimal addMoneyComponent = BigDecimal.ZERO;

        for (PayrollComponentsDTO payrollComponentsDTO : payrollComponentsDTOList) {
            addMoneyComponent = getBigDecimal(baseSalary, addMoneyComponent, payrollComponentsDTO);
        }

        return addMoneyComponent;
    }

    private BigDecimal calculationDeductionComponents(List<PayrollComponentsDTO> payrollComponentsDTOList, Double baseSalary) {
        log.info("Calculation Deduction Components");
        BigDecimal deductionComponent = BigDecimal.ZERO;

        for (PayrollComponentsDTO payrollComponentsDTO : payrollComponentsDTOList) {
            if (payrollComponentsDTO.getType().equals(PayrollComponentType.TAX) || payrollComponentsDTO.getType().equals(PayrollComponentType.INSURANCE)) {
                log.info("Component {} is income tax or insurance will be calculated later.", payrollComponentsDTO.getId());
                continue;
            }
            if (payrollComponentsDTO.getType().equals(PayrollComponentType.DEDUCTION)) {
                deductionComponent = getBigDecimal(baseSalary, deductionComponent, payrollComponentsDTO);
            }
        }

        return deductionComponent;
    }

    private BigDecimal getBigDecimal(Double baseSalary, BigDecimal amountComponent, PayrollComponentsDTO payrollComponentsDTO) {
        if (Boolean.TRUE.equals(payrollComponentsDTO.getIsPercentage())) {
            BigDecimal baseSalaryDecimal = BigDecimal.valueOf(baseSalary);
            float percentage = payrollComponentsDTO.getPercentage();
            BigDecimal calculated = baseSalaryDecimal.multiply(BigDecimal.valueOf(percentage))
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            amountComponent = amountComponent.add(calculated);
        } else if (payrollComponentsDTO.getAmount() != null) {
            amountComponent = amountComponent.add(payrollComponentsDTO.getAmount());
        } else {
            // Các dòng còn lại mà hem có amount/percentage là sai
            throw new IllegalArgumentException(
                    "Component ID " + payrollComponentsDTO.getId() + " is missing amount and percentage.");
        }
        return amountComponent;
    }

}
