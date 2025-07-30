package com.project.hrm.statistics.service;

import com.project.hrm.employee.enums.ContractStatus;
import com.project.hrm.statistics.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StatisticsService {
    List<TotalEmployeeByDepartment> getTotalEmployeeByDepartment();

    List<TotalEmployeeByRole> getTotalEmployeeByRole();

    List<TotalEmployeeByDepartmentAndRole> getTotalEmployeeByDepartmentAndRole();

    List<TotalContractByStatus> getTotalContractByStatus();

    List<TotalContractByStatusAndSalary> getTotalContractByStatusAndSalary(ContractStatus contractStatus);


}
