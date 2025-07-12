package com.project.hrm.payroll.services;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.payroll.dto.approvalsDTO.ApprovalsCreateDTO;
import com.project.hrm.payroll.dto.approvalsDTO.ApprovalsDTO;
import com.project.hrm.payroll.dto.approvalsDTO.ApprovalsFilter;
import com.project.hrm.payroll.dto.approvalsDTO.ApprovalsUpdateDTO;
import com.project.hrm.payroll.entities.Approvals;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface ApprovalsService {
    ApprovalsDTO create(ApprovalsCreateDTO approvalsCreateDTO);

    ApprovalsDTO update(ApprovalsUpdateDTO approvalsUpdateDTO);

    void delete(Integer Id);

    Boolean checkExistence(Integer Id);

    ApprovalsDTO getById(Integer id);

    Approvals getEntityById(Integer id);

    PageDTO<ApprovalsDTO> filter(ApprovalsFilter approvalsFilter, int page, int size);

    PageDTO<ApprovalsDTO> filterByApprovalDateRange(LocalDateTime fromDate, LocalDateTime toDate, int page, int size);
}
