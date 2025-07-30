package com.project.hrm.payroll.services.impl;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.employee.dto.attendanceDTO.AttendanceResponseForPayrollDTO;
import com.project.hrm.employee.dto.dayOffDTO.DayOffResponseForPayrollDTO;
import com.project.hrm.employee.dto.employeeDTO.EmployeeDTO;
import com.project.hrm.employee.service.*;
import com.project.hrm.payroll.dto.payPeriodsDTO.PayPeriodsCreateDTO;
import com.project.hrm.payroll.dto.payrollComponentsDTO.PayrollComponentsDTO;
import com.project.hrm.payroll.dto.payrollsDTO.PayrollsCreateDTO;
import com.project.hrm.payroll.dto.payrollsDTO.PayrollsResponseDTO;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.payroll.entities.PayPeriods;
import com.project.hrm.payroll.entities.PayrollComponents;
import com.project.hrm.payroll.entities.Payrolls;
import com.project.hrm.payroll.enums.PayPeriodStatus;
import com.project.hrm.payroll.enums.PayrollComponentType;
import com.project.hrm.payroll.enums.PayrollStatus;
import com.project.hrm.payroll.enums.ScaleOfTaxation;
import com.project.hrm.payroll.mapper.PayPeriodMapper;
import com.project.hrm.payroll.mapper.PayrollComponentMapper;
import com.project.hrm.payroll.mapper.PayrollsMapper;
import com.project.hrm.payroll.repositories.PayrollComponentsRepository;
import com.project.hrm.payroll.repositories.PayrollsRepository;
import com.project.hrm.payroll.services.PayPeriodsService;
import com.project.hrm.payroll.services.PayrollCalculationService;
import com.project.hrm.payroll.services.PayrollComponentsService;
import com.project.hrm.systemRegulation.enums.SystemRegulationKey;
import com.project.hrm.systemRegulation.service.SystemRegulationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class PayrollCalculationServiceImpl implements PayrollCalculationService {
    private final PayrollsMapper payrollsMapper;
    private final PayPeriodMapper payPeriodMapper;
    private final SystemRegulationService systemRegulationService;
    private final PayPeriodsService payPeriodsService;
    private final AttendanceService attendanceService;
    private final ContractService contractService;
    private final DayOffService dayOffService;
    private final DependentService dependentService;
    private final EmployeeService employeeService;
    private final PayrollsRepository payrollsRepository;
    private final PayrollComponentsService payrollComponentsService;
    private final PayrollComponentsRepository payrollComponentsRepository;
    private final PayrollComponentMapper payrollComponentMapper;

    @Override
    public PayrollsResponseDTO createPayrollForEmployee(PayrollsCreateDTO payrollsCreateDTO) {
        log.info("Get Payroll For Employee ID: {}", payrollsCreateDTO.getEmployeeId());

        String workdaysPerMonth = systemRegulationService.getValue(SystemRegulationKey.WORKDAYS_PER_MONTH);
        String hourlyPerOneDay = systemRegulationService.getValue(SystemRegulationKey.HOURLY_PER_ONE_DAY);
        String overtimeRate = systemRegulationService.getValue(SystemRegulationKey.OVERTIME_RATE);
        String dependentDeductionSys = systemRegulationService.getValue(SystemRegulationKey.DEPENDENT_DEDUCTION);
        String selfDeduction = systemRegulationService.getValue(SystemRegulationKey.SELF_DEDUCTION);


        Payrolls payroll = createForCalculation(payrollsCreateDTO);

        Double baseSalary = contractService.getCurrentActiveContract(payrollsCreateDTO.getEmployeeId()).getBaseSalary();

        PayPeriods payPeriods = payPeriodsService.getEntityById(payroll.getPayPeriod().getId());

        float summaryRegularTime = attendanceService.getTotalRegularTimeAttendanceByPayPeriodsForEmployee(payroll.getEmployee().getId(), payPeriods);
        float summaryOverTime = attendanceService.getTotalOverTimeAttendanceByPayPeriodsForEmployee(payroll.getEmployee().getId(), payPeriods);

        int dayOff = dayOffService.countDayOffByEmployeeId(payroll.getEmployee().getId(), payPeriods.getStartDate(), payPeriods.getEndDate());
        int dayOffNotAccept = dayOffService.countDayOffByEmployeeIdStatus(payroll.getEmployee().getId(), payPeriods.getStartDate(), payPeriods.getEndDate());

        List<PayrollComponents>  payrollComponentsList = payrollComponentsService.createComponents(payroll);

        List<PayrollComponents> payrollComponentsAdditionList = payrollComponentsList.stream()
                .filter(payrollComponents ->
                        payrollComponents.getType()
                                .equals(PayrollComponentType.SUBSIDY) ||
                                payrollComponents.getType()
                                        .equals(PayrollComponentType.REWARD))
                .toList();
        List<PayrollComponents> payrollComponentsDeductionList = payrollComponentsList.stream()
                .filter(payrollComponents ->
                        payrollComponents.getType()
                                .equals(PayrollComponentType.DEDUCTION) ||
                                payrollComponents.getType()
                                        .equals(PayrollComponentType.TAX) ||
                                payrollComponents.getType()
                                        .equals(PayrollComponentType.INSURANCE))
                .toList();

        //Received by attendance, reward and subsidy
        BigDecimal totalAmountReceived = totalAmountReceived(
                baseSalary, summaryRegularTime, summaryOverTime,
                payrollComponentsAdditionList, workdaysPerMonth, hourlyPerOneDay, overtimeRate
        );

        //Deduction by disciplinary action
        BigDecimal totalAmountDisciplinary = calculationDeductionComponents(payrollComponentsDeductionList,baseSalary);

        //Deduction by Insurance
        BigDecimal totalAmountInsurance = totalAmountInsurance(payrollComponentsDeductionList,baseSalary);


        //Tax Person income
        //Total income and use calculate tax
        BigDecimal totalIncome = totalAmountReceived.subtract(totalAmountDisciplinary);

        //Deduction for tax
        int countDependent = dependentService.countDependentsOfEmployee(payroll.getEmployee().getId());
        BigDecimal dependentDeduction = BigDecimal.valueOf(Long.parseLong(dependentDeductionSys));

        BigDecimal totalDeductionForTaxByDependent = BigDecimal.valueOf(countDependent).multiply(dependentDeduction);

        BigDecimal totalDeductionForSelfEmployee = BigDecimal.valueOf(Long.parseLong(selfDeduction));

        BigDecimal totalDeductionForTax = totalDeductionForTaxByDependent.add(totalDeductionForSelfEmployee);

        //Deduction by Tax
        BigDecimal totalAmountTax = BigDecimal.ZERO;
        if (totalIncome.subtract(totalDeductionForTax).compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal totalIncomeForTax = totalIncome.subtract(totalDeductionForTax);
            totalAmountTax = totalAmountTaxPersonalIncomeTax(totalIncomeForTax, payrollComponentsDeductionList);
        }

        List<PayrollComponentsDTO> payrollComponentsDTOAdditionList = payrollComponentsAdditionList.stream()
                .map(payrollComponentMapper::toPayrollComponentsDTO)
                .collect(Collectors.toList());

        List<PayrollComponentsDTO> payrollComponentsDTODeductionList = payrollComponentsDeductionList.stream()
                .map(payrollComponentMapper::toPayrollComponentsDTO)
                .collect(Collectors.toList());


        //Net salary
        BigDecimal netSalary = totalIncome.subtract(totalAmountTax.add(totalAmountInsurance));

        //Entity for response
        AttendanceResponseForPayrollDTO attendanceResponseForPayrollDTO = new AttendanceResponseForPayrollDTO(summaryRegularTime, summaryOverTime);
        DayOffResponseForPayrollDTO dayOffResponseForPayrollDTO = new DayOffResponseForPayrollDTO(dayOff, dayOffNotAccept);

        payrollComponentsRepository.saveAll(Stream.of(payrollComponentsAdditionList,
                        payrollComponentsDeductionList)
                .flatMap(Collection::stream)
                .toList());
        return new PayrollsResponseDTO(
                payroll.getId(),
                payroll.getEmployee().getId(),
                payroll.getPayPeriod().getId(),
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

    @Transactional
    @Override
    public List<PayrollsResponseDTO> createBatchPayrolls(List<PayrollsCreateDTO> payrollsCreateDTOList) {
        log.info("Creating batch payrolls for {} employees", payrollsCreateDTOList.size());

        if (payrollsCreateDTOList.isEmpty()) {
            return new ArrayList<>();
        }

        List<PayrollsResponseDTO> results = new ArrayList<>();
        List<PayrollComponents> payrollComponentsSave = new ArrayList<>();

        String workdaysPerMonth = systemRegulationService.getValue(SystemRegulationKey.WORKDAYS_PER_MONTH);
        String hourlyPerOneDay = systemRegulationService.getValue(SystemRegulationKey.HOURLY_PER_ONE_DAY);
        String overtimeRate = systemRegulationService.getValue(SystemRegulationKey.OVERTIME_RATE);
        String dependentDeduction = systemRegulationService.getValue(SystemRegulationKey.DEPENDENT_DEDUCTION);
        String selfDeduction = systemRegulationService.getValue(SystemRegulationKey.SELF_DEDUCTION);

        PayrollsCreateDTO firstPayroll = payrollsCreateDTOList.getFirst();

        PayPeriods payPeriods = payPeriodsService.getOrCreatePayPeriod(firstPayroll.getStartDate(), firstPayroll.getEndDate());
        log.info("Using pay period: {} - {}", payPeriods.getStartDate(), payPeriods.getEndDate());

        List<Integer> employeeIds = payrollsCreateDTOList.stream()
                .map(PayrollsCreateDTO::getEmployeeId)
                .distinct()
                .toList();

        Map<Integer,Employees> employeesMap = employeeService.getBatchEmployeeForPayPeriod(employeeIds);

        log.info("Using employees: {}", employeesMap);

        Map<Integer, Payrolls> payrollsMap = createWithPeriodAndEmployee(payrollsCreateDTOList, employeesMap, payPeriods);

        List<Payrolls> createdPayrolls = employeeIds.stream()
                .filter(payrollsMap::containsKey)
                .map(payrollsMap::get)
                .collect(Collectors.toList());

        log.info("Creating batch for {} payrolls  ", createdPayrolls);

        log.info("Preloading data for {} unique employees", employeeIds.size());

        Map<Integer, Double> employeeBaseSalaries = contractService.getBaseSalariesForEmployees(employeeIds);
        Map<Integer, Float> employeeRegularTimes = attendanceService.getBatchTotalRegularTime(employeeIds, payPeriods);
        Map<Integer, Float> employeeOverTimes = attendanceService.getBatchTotalOverTime(employeeIds, payPeriods);
        Map<Integer, Integer> employeeDayOffs = dayOffService.getBatchDayOffCount(employeeIds, payPeriods);
        Map<Integer, Integer> employeeDayOffNotAccepts = dayOffService.getBatchDayOffNotAcceptCount(employeeIds, payPeriods);
        Map<Integer, Integer> dependentCountMap = dependentService.getDependentCountsForEmployees(employeeIds);

        log.info("Creating batch components for {} payrolls", createdPayrolls.size());
        Map<Integer, List<PayrollComponents>> componentsMap = payrollComponentsService.createBatchComponents(createdPayrolls);

        for (Payrolls payroll : createdPayrolls) {
            try{
                Integer employeeId = payroll.getEmployee().getId();

                Double baseSalary = employeeBaseSalaries.get(employeeId);

                if (baseSalary == null) {
                    log.warn("No base salary found for employee {}", employeeId);
                    continue;
                }

                Float summaryRegularTime = employeeRegularTimes.getOrDefault(employeeId, 0.0f);
                Float summaryOverTime = employeeOverTimes.getOrDefault(employeeId, 0.0f);
                Integer dayOff = employeeDayOffs.getOrDefault(employeeId, 0);
                Integer dayOffNotAccept = employeeDayOffNotAccepts.getOrDefault(employeeId, 0);
                Integer countDependent = dependentCountMap.getOrDefault(employeeId, 0);

                List<PayrollComponents> payrollComponentsList = componentsMap.getOrDefault(payroll.getId(), new ArrayList<>());

                List<PayrollComponents> payrollComponentsAdditionList = payrollComponentsList.stream()
                        .filter(payrollComponents ->
                                payrollComponents.getType().equals(PayrollComponentType.SUBSIDY) ||
                                        payrollComponents.getType().equals(PayrollComponentType.REWARD))
                        .toList();

                List<PayrollComponents> payrollComponentsDeductionList = payrollComponentsList.stream()
                        .filter(payrollComponents ->
                                payrollComponents.getType().equals(PayrollComponentType.DEDUCTION) ||
                                        payrollComponents.getType().equals(PayrollComponentType.TAX) ||
                                        payrollComponents.getType().equals(PayrollComponentType.INSURANCE))
                        .toList();

                BigDecimal totalAmountReceived = totalAmountReceived(
                        baseSalary, summaryRegularTime, summaryOverTime,
                        payrollComponentsAdditionList, workdaysPerMonth, hourlyPerOneDay, overtimeRate
                );

                BigDecimal totalAmountDisciplinary = calculationDeductionComponents(payrollComponentsDeductionList, baseSalary);
                BigDecimal totalAmountInsurance = totalAmountInsurance(payrollComponentsDeductionList, baseSalary);

                BigDecimal totalIncome = totalAmountReceived.subtract(totalAmountDisciplinary);

                BigDecimal dependentDeductionBigDecimal = BigDecimal.valueOf(Long.parseLong(dependentDeduction));
                BigDecimal totalDeductionForTaxByDependent = BigDecimal.valueOf(countDependent).multiply(dependentDeductionBigDecimal);
                BigDecimal totalDeductionForSelfEmployee = BigDecimal.valueOf(Long.parseLong(selfDeduction));

                BigDecimal totalDeductionForTax = totalDeductionForTaxByDependent.add(totalDeductionForSelfEmployee);

                BigDecimal totalAmountTax = BigDecimal.ZERO;
                if (totalIncome.subtract(totalDeductionForTax).compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal totalIncomeForTax = totalIncome.subtract(totalDeductionForTax);
                    totalAmountTax = totalAmountTaxPersonalIncomeTax(totalIncomeForTax, payrollComponentsDeductionList);
                }

                BigDecimal netSalary = totalIncome.subtract(totalAmountTax.add(totalAmountInsurance));

                payrollComponentsSave.addAll(payrollComponentsDeductionList);
                payrollComponentsSave.addAll(payrollComponentsAdditionList);

                AttendanceResponseForPayrollDTO attendanceResponseForPayrollDTO =
                        new AttendanceResponseForPayrollDTO(summaryRegularTime, summaryOverTime);
                DayOffResponseForPayrollDTO dayOffResponseForPayrollDTO =
                        new DayOffResponseForPayrollDTO(dayOff, dayOffNotAccept);

                List<PayrollComponentsDTO> payrollComponentsDTOAdditionList = payrollComponentsAdditionList.stream()
                        .map(payrollComponentMapper::toPayrollComponentsDTO)
                        .collect(Collectors.toList());

                List<PayrollComponentsDTO> payrollComponentsDTODeductionList = payrollComponentsDeductionList.stream()
                        .map(payrollComponentMapper::toPayrollComponentsDTO)
                        .collect(Collectors.toList());

                PayrollsResponseDTO responseDTO = new PayrollsResponseDTO(
                        payroll.getId(),
                        payroll.getEmployee().getId(),
                        payroll.getPayPeriod().getId(),
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
                results.add(responseDTO);
                log.debug("Successfully created payroll response for employee {}", employeeId);
            } catch (Exception e) {
                log.error("Error creating payroll for employee {}: {}",
                        payroll.getEmployee().getId(), e.getMessage(), e);
            }
        }

        log.info("Batch payroll creation completed. Successfully created {} out of {} payrolls",
                results.size(), payrollsCreateDTOList.size());
        payrollComponentsRepository.saveAll(payrollComponentsSave);
        return results;
    }

    @Transactional
    @Override
    public List<PayrollsResponseDTO> createBatchPayrollsByDepartment(Integer departmentId, PayrollsCreateDTO template) {
        log.info("Creating batch payrolls for department {}", departmentId);

        // Lấy tất cả nhân viên trong phòng ban
        List<Employees> employees = employeeService.getAllEmployeesByDepartmentId(departmentId);
        log.info("Employees page content: {}", employees);
        // Trích xuất danh sách ID từ Page
        List<Integer> employeeIds = employees.stream()
                .map(Employees::getId)
                .toList();

        log.info("Creating batch payrolls for list employee {}", employeeIds);

        // Tạo PayrollsCreateDTO cho mỗi nhân viên
        List<PayrollsCreateDTO> payrollsCreateDTOList = employeeIds.stream()
                .map(employeeId -> {
                    PayrollsCreateDTO dto = new PayrollsCreateDTO();
                    dto.setEmployeeId(employeeId);
                    dto.setStartDate(template.getStartDate());
                    dto.setStatus(PayrollStatus.PENDING);
                    dto.setEndDate(template.getEndDate());
                    return dto;
                })
                .toList();

        log.info("Creating batch payrolls for list create dto {}", payrollsCreateDTOList);

        return createBatchPayrolls(payrollsCreateDTOList);
    }

    @Transactional
    @Override
    public List<PayrollsResponseDTO> createBatchPayrollsForAllEmployees(PayrollsCreateDTO template) {
        log.info("Creating batch payrolls for all employees");

        // Lấy tất cả ID nhân viên đang hoạt động
        List<Integer> employeeIds = employeeService.getAllActiveEmployeeIds();

        // Tạo PayrollsCreateDTO cho mỗi nhân viên
        List<PayrollsCreateDTO> payrollsCreateDTOList = employeeIds.stream()
                .map(employeeId -> {
                    PayrollsCreateDTO dto = new PayrollsCreateDTO();
                    dto.setEmployeeId(employeeId);
                    dto.setStartDate(template.getStartDate());
                    dto.setEndDate(template.getEndDate());
                    return dto;
                })
                .toList();

        return createBatchPayrolls(payrollsCreateDTOList);
    }

    private BigDecimal totalAmountTaxPersonalIncomeTax(BigDecimal taxableIncome, List<PayrollComponents> payrollComponentsTax) {
        BigDecimal totalTax = BigDecimal.ZERO;
        for (ScaleOfTaxation level : ScaleOfTaxation.values()) {
            totalTax = totalTax.add(level.calculateTax(taxableIncome));
        }

        // Lấy một phần tử PayrollComponentsDTO có type là TAX
        PayrollComponents payrollComponentOpt = payrollComponentsTax.stream()
                .filter(payrollComponentDTO ->
                        payrollComponentDTO.getType().equals(PayrollComponentType.TAX))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No PayrollComponent with type TAX found"));

        payrollComponentOpt.setAmount(totalTax.compareTo(BigDecimal.ZERO) > 0 ? totalTax : BigDecimal.ZERO);
        return totalTax;
    }

    private BigDecimal totalAmountReceived(Double baseSalary,
                                           float summaryRegularTime,
                                           float summaryOverTime,
                                           List<PayrollComponents>  payrollComponentsList,
                                           String workdaysPerMonth,
                                           String hourlyPerOneDay,
                                           String overtimeRate){

        log.info("Summary Total Income");
        Double totalStandardHoursInMonth = Double.parseDouble(workdaysPerMonth) * Double.parseDouble(hourlyPerOneDay);
        double hourlyRate = baseSalary / totalStandardHoursInMonth;

        //Total charge by the hour worked
        BigDecimal totalIncomeForRegularTime = BigDecimal.valueOf(hourlyRate * summaryRegularTime);

        BigDecimal totalIncomeForOverTime = BigDecimal.valueOf(hourlyRate * summaryOverTime * Double.parseDouble(overtimeRate));

        //Total fees according to bonuses and allowances
        BigDecimal totalSubsidyAndRewardIncome = calculationSubsidyAndRewardComponents(payrollComponentsList, baseSalary);

        return totalIncomeForRegularTime.add(totalSubsidyAndRewardIncome.add(totalIncomeForOverTime));
    }

    private BigDecimal totalAmountInsurance(List<PayrollComponents> payrollComponentsDeductionList, Double baseSalary){
        log.info("Summary Insurance Total");

        BigDecimal totalInsurance = BigDecimal.ZERO;

        for (PayrollComponents payrollComponent : payrollComponentsDeductionList) {
            if (payrollComponent.getType().equals(PayrollComponentType.INSURANCE)) {
                totalInsurance = getBigDecimal(baseSalary, totalInsurance, payrollComponent);
            }
        }
        return totalInsurance;
    }

    private BigDecimal calculationSubsidyAndRewardComponents(List<PayrollComponents> payrollComponentsList, Double baseSalary) {
        log.info("Calculation Subsidy and Reward Components");
        BigDecimal addMoneyComponent = BigDecimal.ZERO;

        for (PayrollComponents payrollComponents : payrollComponentsList) {
            addMoneyComponent = getBigDecimal(baseSalary, addMoneyComponent, payrollComponents);
        }

        return addMoneyComponent;
    }

    private BigDecimal calculationDeductionComponents(List<PayrollComponents> payrollComponentsList, Double baseSalary) {
        log.info("Calculation Deduction Components");
        BigDecimal deductionComponent = BigDecimal.ZERO;

        for (PayrollComponents payrollComponents : payrollComponentsList) {
            if (payrollComponents.getType().equals(PayrollComponentType.TAX) || payrollComponents.getType().equals(PayrollComponentType.INSURANCE)) {
                log.info("Component {} is income tax or insurance will be calculated later.", payrollComponents.getId());
                continue;
            }
            if (payrollComponents.getType().equals(PayrollComponentType.DEDUCTION)) {
                deductionComponent = getBigDecimal(baseSalary, deductionComponent, payrollComponents);
            }
        }

        return deductionComponent;
    }

    private BigDecimal getBigDecimal(Double baseSalary, BigDecimal amountComponent, PayrollComponents payrollComponents) {
        log.info("Calculation Subsidy and Reward Components: {}", payrollComponents);
        if (Boolean.TRUE.equals(payrollComponents.getIsPercentage())) {
            BigDecimal baseSalaryDecimal = BigDecimal.valueOf(baseSalary);
            float percentage = payrollComponents.getPercentage();
            BigDecimal calculated = baseSalaryDecimal.multiply(BigDecimal.valueOf(percentage))
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            amountComponent = amountComponent.add(calculated);
        } else if (payrollComponents.getAmount() != null) {
            amountComponent = amountComponent.add(payrollComponents.getAmount());
        } else {
            // Các dòng còn lại mà hem có amount/percentage là sai á
            throw new IllegalArgumentException(
                    "Component ID " + payrollComponents.getId() + " is missing amount and percentage.");
        }
        return amountComponent;
    }

    private Payrolls createForCalculation(PayrollsCreateDTO payrollsCreateDTO) {
        log.info("Create PayrollsDTO from Calculation");
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

        return payrollsRepository.save(payrolls);
    }

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
        payrollsRepository.saveAll(payrolls);

        for (Payrolls payroll : payrolls) {
            Integer employeeId = payroll.getEmployee().getId();
            payrollsMap.put(employeeId, payroll);
        }

        log.info("Create Payrolls with Employees {}", payrollsMap);

        return payrollsMap;
    }
}
