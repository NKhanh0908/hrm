package com.project.hrm.services;

import com.project.hrm.dto.applyDTO.*;
import com.project.hrm.dto.othersDTO.InterviewLetter;
import com.project.hrm.entities.Apply;
import com.project.hrm.enums.ApplyStatus;
import org.springframework.stereotype.Service;

import java.util.List;

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

    List<ApplyDTO> filter(ApplyFilter applyFilter, int page, int size);


}
