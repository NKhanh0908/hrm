package com.project.hrm.mapper;

import com.project.hrm.dto.approvalsDTO.ApprovalsCreateDTO;
import com.project.hrm.dto.approvalsDTO.ApprovalsDTO;
import com.project.hrm.entities.Approvals;
import com.project.hrm.repositories.EmployeeRepository;
import com.project.hrm.repositories.PayrollsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ApprovalsMapper {
    private final EmployeeRepository employeeRepository;
    private final PayrollsRepository payrollsRepository;

    public Approvals toEntity(ApprovalsDTO approvalsDTO){
        return Approvals.builder()
                .id(approvalsDTO.getId())
                .employeeReview(employeeRepository.findById(approvalsDTO.getEmployeeReviewId()).orElse(null))
                .payroll(payrollsRepository.findById(approvalsDTO.getPayrollId())
                        .orElseThrow(() -> new EntityNotFoundException("Payroll not found with ID = " + approvalsDTO.getPayrollId())))
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
                .employeeReview(employeeRepository.findById(approvalsCreateDTO.getEmployeeReviewId()).orElse(null))
                .payroll(payrollsRepository.findById(approvalsCreateDTO.getPayrollId())
                        .orElseThrow(() -> new EntityNotFoundException("Payroll not found with ID = " + approvalsCreateDTO.getPayrollId())))
                .approvalDate(approvalsCreateDTO.getApprovalDate())
                .comment(approvalsCreateDTO.getComment())
                .status(approvalsCreateDTO.getStatus())
                .build();
    }
}
