package com.project.hrm.services;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.feedbackEmployeeDTO.FeedbackEmployeeCreateDTO;
import com.project.hrm.dto.feedbackEmployeeDTO.FeedbackEmployeeDTO;
import com.project.hrm.dto.feedbackEmployeeDTO.FeedbackEmployeeFilter;
import com.project.hrm.dto.feedbackEmployeeDTO.FeedbackEmployeeUpdateDTO;
import com.project.hrm.entities.FeedbackEmployee;
import org.springframework.stereotype.Service;

@Service
public interface FeedbackEmployeeService {
    FeedbackEmployeeDTO create(FeedbackEmployeeCreateDTO feedbackEmployeeCreateDTO);

    FeedbackEmployeeDTO update(FeedbackEmployeeUpdateDTO feedbackEmployeeUpdateDTO);

    FeedbackEmployee getFeedbackEmployeeById(Integer id);

    FeedbackEmployeeDTO getDTOById(Integer id);

    PageDTO<FeedbackEmployeeDTO> filter(FeedbackEmployeeFilter feedbackEmployeeFilter, int page, int size);
}
