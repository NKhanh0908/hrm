package com.project.hrm.mapper;

import com.project.hrm.dto.approvalsDTO.ApprovalsCreateDTO;
import com.project.hrm.dto.approvalsDTO.ApprovalsDTO;
import com.project.hrm.entities.Approvals;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.services.PayrollsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ApprovalsMapper {
    private final EmployeeService employeeService;
    private final PayrollsService payrollsService;

    public Approvals toEntity(ApprovalsDTO approvalsDTO){
        return Approvals.builder()
                .id(approvalsDTO.getId())
                .employeeReview(employeeService.getEntityById(approvalsDTO.getEmployeeReviewId()))
                .payroll(payrollsService.getEntityById(approvalsDTO.getPayrollId()))
                .approvalDate(approvalsDTO.getApprovalDate())
                .comment(approvalsDTO.getComment())
                .status(approvalsDTO.getStatus())
                .build();
    }

    public ApprovalsDTO toDTO(Approvals approvals){
        return ApprovalsDTO.builder()
                .id(approvals.getId())
                .employeeReviewId(approvals.getEmployeeReview().getId())
                .payrollId(approvals.getPayroll().getId())
                .approvalDate(approvals.getApprovalDate())
                .comment(approvals.getComment())
                .status(approvals.getStatus())
                .build();
    }

    public Approvals toEntityFromCreateDTO(ApprovalsCreateDTO approvalsCreateDTO){
        return Approvals.builder()
                .employeeReview(employeeService.getEntityById(approvalsCreateDTO.getEmployeeReviewId()))
                .payroll(payrollsService.getEntityById(approvalsCreateDTO.getPayrollId()))
                .approvalDate(approvalsCreateDTO.getApprovalDate())
                .comment(approvalsCreateDTO.getComment())
                .status(approvalsCreateDTO.getStatus())
                .build();
    }
}
