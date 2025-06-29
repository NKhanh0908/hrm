package com.project.hrm.services;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.approvalsDTO.ApprovalsCreateDTO;
import com.project.hrm.dto.approvalsDTO.ApprovalsDTO;
import com.project.hrm.dto.approvalsDTO.ApprovalsFilter;
import com.project.hrm.dto.approvalsDTO.ApprovalsUpdateDTO;
import com.project.hrm.entities.Approvals;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
