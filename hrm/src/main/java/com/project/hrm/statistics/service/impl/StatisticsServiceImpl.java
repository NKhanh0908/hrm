package com.project.hrm.statistics.service.impl;

import com.project.hrm.employee.enums.ContractStatus;
import com.project.hrm.employee.repository.ContractRepository;
import com.project.hrm.employee.repository.EmployeeRepository;
import com.project.hrm.statistics.dto.*;
import com.project.hrm.statistics.service.StatisticsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final EmployeeRepository employeeRepository;
    private final ContractRepository contractRepository;

    /**
     * Retrieves the total number of employees grouped by department.
     *
     * @return a list of {@link TotalEmployeeByDepartment} containing department name and employee count
     */
    @Transactional(readOnly = true)
    @Override
    public List<TotalEmployeeByDepartment> getTotalEmployeeByDepartment() {
        log.info("Fetching total employees by department");
        return employeeRepository.getTotalEmployeeByDepartment();
    }

    /**
     * Retrieves the total number of employees grouped by role.
     *
     * @return a list of {@link TotalEmployeeByRole} containing role name and employee count
     */
    @Transactional(readOnly = true)
    @Override
    public List<TotalEmployeeByRole> getTotalEmployeeByRole() {
        log.info("Fetching total employees by role");
        return employeeRepository.getTotalEmployeeByRole();
    }

    /**
     * Retrieves the total number of employees grouped by both department and role.
     *
     * @return a list of {@link TotalEmployeeByDepartmentAndRole} containing department, role, and employee count
     */
    @Transactional(readOnly = true)
    @Override
    public List<TotalEmployeeByDepartmentAndRole> getTotalEmployeeByDepartmentAndRole() {
        log.info("Fetching total employees by department and role");
        return employeeRepository.getTotalEmployeeByDepartmentAndRole();
    }

    /**
     * Retrieves the total number of contracts grouped by contract status.
     *
     * @return a list of {@link TotalContractByStatus} containing contract status and count
     */
    @Transactional(readOnly = true)
    @Override
    public List<TotalContractByStatus> getTotalContractByStatus() {
        log.info("Fetching total contracts by status");
        return contractRepository.getTotalContractByStatus();
    }

    /**
     * Retrieves the total number of contracts grouped by status and aggregated by salary.
     *
     * @param contractStatus the contract status to filter by
     * @return a list of {@link TotalContractByStatusAndSalary} containing status, total contracts and total salary
     */
    @Transactional(readOnly = true)
    @Override
    public List<TotalContractByStatusAndSalary> getTotalContractByStatusAndSalary(ContractStatus contractStatus) {
        log.info("Fetching total contracts by status [{}] and aggregated salary", contractStatus);
        return contractRepository.getTotalContractByStatusAndSalary(contractStatus.name());
    }

}
