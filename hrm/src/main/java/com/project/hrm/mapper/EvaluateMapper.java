package com.project.hrm.mapper;

import com.project.hrm.dto.evaluateDTO.EvaluateCreateDTO;
import com.project.hrm.dto.evaluateDTO.EvaluateDTO;
import com.project.hrm.entities.Evaluate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EvaluateMapper {
    @Autowired
    private CandidateProfileMapper candidateProfileMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    public Evaluate conventCreateToEntity(EvaluateCreateDTO evaluateCreateDTO){
        return Evaluate.builder()
                .evaluate(evaluateCreateDTO.getEvaluate())
                .feedbackAt(LocalDateTime.now())
                .feedback(evaluateCreateDTO.getFeedback())
                .build();
    }

    public EvaluateDTO toEvaluateDTO(Evaluate evaluate){
        return EvaluateDTO.builder()
                .id(evaluate.getId())
                .candidateProfileDTO(candidateProfileMapper.toCandidateProfileDTO(evaluate.getCandidateProfile()))
                .employeeDTO(employeeMapper.toEmployeeDTO(evaluate.getCreateBy()))
                .evaluate(evaluate.getEvaluate())
                .feedback(evaluate.getFeedback())
                .feedbackAt(evaluate.getFeedbackAt())
                .build();
    }
}
