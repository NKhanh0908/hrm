package com.project.hrm.training.service;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.training.dto.trainingEnrollmentDTO.TrainingEnrollmentCreateDTO;
import com.project.hrm.training.dto.trainingEnrollmentDTO.TrainingEnrollmentDTO;
import com.project.hrm.training.dto.trainingEnrollmentDTO.TrainingEnrollmentFilter;
import com.project.hrm.training.dto.trainingEnrollmentDTO.TrainingEnrollmentUpdateDTO;
import com.project.hrm.training.entity.TrainingEnrollment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TrainingEnrollmentService {
    TrainingEnrollmentDTO create(TrainingEnrollmentCreateDTO trainingEnrollmentCreateDTO);

    TrainingEnrollmentDTO update(TrainingEnrollmentUpdateDTO trainingEnrollmentUpdateDTO);

    TrainingEnrollmentDTO updateStatus(Integer id, String status);

    List<TrainingEnrollmentDTO> generateTrainingEnroll(Integer requestedProgramId, Integer trainingRequest);

    TrainingEnrollment getEntityById(Integer id);

    TrainingEnrollmentDTO getDTOById(Integer id);

    PageDTO<TrainingEnrollmentDTO> filter(TrainingEnrollmentFilter trainingEnrollmentFilter, int page, int size);
}
