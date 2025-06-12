package com.project.hrm.services;

import com.project.hrm.dto.statisticsDTO.*;
import com.project.hrm.enums.ContractStatus;
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
