package com.project.hrm.services;

import com.project.hrm.dto.assignmentDTO.AssignmentCreateDTO;
import com.project.hrm.dto.assignmentDTO.AssignmentDTO;
import com.project.hrm.dto.assignmentDTO.AssignmentFilter;
import com.project.hrm.dto.assignmentDTO.AssignmentUpdateDTO;
import com.project.hrm.entities.Assignment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AssignmentService {
    AssignmentDTO create(AssignmentCreateDTO assignmentCreateDTO);

    AssignmentDTO update(AssignmentUpdateDTO assignmentUpdateDTO);

    List<AssignmentDTO> filter(AssignmentFilter assignmentFilter, int page, int size);

    Assignment getEntityById(Integer id);
}
