package com.project.hrm.payrollManager.services;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.payrollManager.dto.approvalsDTO.ApprovalsCreateDTO;
import com.project.hrm.payrollManager.dto.approvalsDTO.ApprovalsDTO;
import com.project.hrm.payrollManager.dto.approvalsDTO.ApprovalsFilter;
import com.project.hrm.payrollManager.dto.approvalsDTO.ApprovalsUpdateDTO;
import com.project.hrm.payrollManager.entities.Approvals;
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
