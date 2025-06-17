package com.project.hrm.services;

import com.project.hrm.dto.trainingEnrollmentDTO.TrainingEnrollmentCreateDTO;
import com.project.hrm.dto.trainingEnrollmentDTO.TrainingEnrollmentDTO;
import com.project.hrm.dto.trainingEnrollmentDTO.TrainingEnrollmentFilter;
import com.project.hrm.entities.TrainingEnrollment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TrainingEnrollmentService {
    TrainingEnrollmentDTO create(TrainingEnrollmentCreateDTO trainingEnrollmentCreateDTO);

    TrainingEnrollment getEntityById(Integer id);

    TrainingEnrollmentDTO getDTOById(Integer id);

    List<TrainingEnrollmentDTO> filter(TrainingEnrollmentFilter trainingEnrollmentFilter, int page, int size);
}
