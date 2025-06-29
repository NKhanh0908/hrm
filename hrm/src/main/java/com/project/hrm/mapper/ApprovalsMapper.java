package com.project.hrm.mapper;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.approvalsDTO.ApprovalsCreateDTO;
import com.project.hrm.dto.approvalsDTO.ApprovalsDTO;
import com.project.hrm.entities.Approvals;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ApprovalsMapper {

    public Approvals toEntity(ApprovalsDTO approvalsDTO){
        return Approvals.builder()
                .id(approvalsDTO.getId())
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
                .approvalDate(approvalsCreateDTO.getApprovalDate())
                .comment(approvalsCreateDTO.getComment())
                .status(approvalsCreateDTO.getStatus())
                .build();
    }

    public PageDTO<ApprovalsDTO> toApprovalsPageDTO(Page<Approvals> page) {
        return PageDTO.<ApprovalsDTO>builder()
                .content(
                        page.getContent()
                                .stream()
                                .map(this::toDTO)      // dùng toDTO(Approvals)
                                .toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
