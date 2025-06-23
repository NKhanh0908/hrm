package com.project.hrm.services;

import com.project.hrm.dto.feedbackEmployeeDTO.FeedbackEmployeeCreateDTO;
import com.project.hrm.dto.feedbackEmployeeDTO.FeedbackEmployeeDTO;
import com.project.hrm.dto.feedbackEmployeeDTO.FeedbackEmployeeFilter;
import com.project.hrm.dto.feedbackEmployeeDTO.FeedbackEmployeeUpdateDTO;
import com.project.hrm.entities.FeedbackEmployee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FeedbackEmployeeService {
    FeedbackEmployeeDTO create(FeedbackEmployeeCreateDTO feedbackEmployeeCreateDTO);

    FeedbackEmployeeDTO update(FeedbackEmployeeUpdateDTO feedbackEmployeeUpdateDTO);

    FeedbackEmployee getFeedbackEmployeeById(Integer id);

    FeedbackEmployeeDTO getDTOById(Integer id);

    List<FeedbackEmployeeDTO> filter(FeedbackEmployeeFilter feedbackEmployeeFilter, int page, int size);
}
