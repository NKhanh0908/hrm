package com.project.hrm.performentEmployee.service;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.performentEmployee.dto.feedbackEmployeeDTO.FeedbackEmployeeCreateDTO;
import com.project.hrm.performentEmployee.dto.feedbackEmployeeDTO.FeedbackEmployeeDTO;
import com.project.hrm.performentEmployee.dto.feedbackEmployeeDTO.FeedbackEmployeeFilter;
import com.project.hrm.performentEmployee.dto.feedbackEmployeeDTO.FeedbackEmployeeUpdateDTO;
import com.project.hrm.performentEmployee.entity.FeedbackEmployee;
import org.springframework.stereotype.Service;

@Service
public interface FeedbackEmployeeService {
    FeedbackEmployeeDTO create(FeedbackEmployeeCreateDTO feedbackEmployeeCreateDTO);

    FeedbackEmployeeDTO update(FeedbackEmployeeUpdateDTO feedbackEmployeeUpdateDTO);

    FeedbackEmployee getFeedbackEmployeeById(Integer id);

    FeedbackEmployeeDTO getDTOById(Integer id);

    PageDTO<FeedbackEmployeeDTO> filter(FeedbackEmployeeFilter feedbackEmployeeFilter, int page, int size);
}
