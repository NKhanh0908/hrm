package com.project.hrm.services;

import com.project.hrm.recruitment.dto.applyDTO.JobOfferDetailsDTO;
import com.project.hrm.dto.othersDTO.InfoApply;
import com.project.hrm.dto.othersDTO.InterviewLetter;
import org.springframework.stereotype.Service;

@Service
public interface MailService{
    void notificationInterview(InfoApply infoApply, InterviewLetter interviewLetter);

    void notificationForHired(InfoApply infoApply, JobOfferDetailsDTO jobOfferDetailsDTO);

    void notificationForRejection(InfoApply infoApply);
}
