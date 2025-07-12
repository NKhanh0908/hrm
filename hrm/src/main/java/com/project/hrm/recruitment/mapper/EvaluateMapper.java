package com.project.hrm.recruitment.mapper;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.employee.mapper.EmployeeMapper;
import com.project.hrm.recruitment.dto.evaluateDTO.EvaluateCreateDTO;
import com.project.hrm.recruitment.dto.evaluateDTO.EvaluateDTO;
import com.project.hrm.recruitment.entity.CandidateProfile;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.recruitment.entity.Evaluate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EvaluateMapper {
    private final CandidateProfileMapper candidateProfileMapper;
    private final EmployeeMapper employeeMapper;

    public Evaluate conventCreateToEntity(EvaluateCreateDTO evaluateCreateDTO, Employees employees, CandidateProfile candidateProfile){
        return Evaluate.builder()
                .evaluate(evaluateCreateDTO.getEvaluate())
                .feedbackAt(LocalDateTime.now())
                .feedback(evaluateCreateDTO.getFeedback())
                .evaluate(evaluateCreateDTO.getEvaluate())
                .createBy(employees)
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

    public List<EvaluateDTO> convertPageToList(Page<Evaluate> evaluatePage){
        return evaluatePage.getContent()
                .stream()
                .map(this::toEvaluateDTO)
                .collect(Collectors.toList());
    }

    public PageDTO<EvaluateDTO> toEvaluatePageDTO(Page<Evaluate> page) {
        return PageDTO.<EvaluateDTO>builder()
                .content(page.getContent().stream()
                        .map(this::toEvaluateDTO)
                        .toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
