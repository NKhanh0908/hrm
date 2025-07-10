package com.project.hrm.recruitment.service;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.othersDTO.InterviewLetter;
import com.project.hrm.recruitment.dto.applyDTO.*;
import com.project.hrm.recruitment.entity.Apply;
import com.project.hrm.recruitment.enums.ApplyStatus;
import org.springframework.stereotype.Service;

@Service
public interface ApplyService {
    ApplyDTO create(ApplyCreateDTO applyCreateDTO);

    ApplyDTO update(ApplyUpdateDTO applyUpdateDTO);

    ApplyDTO interview(InterviewLetter interviewLetter);

    ApplyDTO rejectApply(Integer applyId);

    ApplyDTO hiredApply(Integer applyId, JobOfferDetailsDTO details);

    ApplyDTO updateStatus(Integer id, ApplyStatus status);

    ApplyDTO getById(Integer id);

    Apply getEntityById(Integer id);

    Integer getRoleIdByApplyId(Integer applyId);

    PageDTO<ApplyDTO> filter(ApplyFilter applyFilter, int page, int size);


}
