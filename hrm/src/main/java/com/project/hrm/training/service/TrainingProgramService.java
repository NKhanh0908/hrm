package com.project.hrm.training.service;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.training.dto.trainingProgramDTO.TrainingProgramCreateDTO;
import com.project.hrm.training.dto.trainingProgramDTO.TrainingProgramDTO;
import com.project.hrm.training.dto.trainingProgramDTO.TrainingProgramFilter;
import com.project.hrm.training.dto.trainingProgramDTO.TrainingProgramUpdateDTO;
import com.project.hrm.training.entity.TrainingProgram;
import org.springframework.stereotype.Service;

@Service
public interface TrainingProgramService {
    TrainingProgramDTO create(TrainingProgramCreateDTO trainingProgramCreateDTO);

    TrainingProgramDTO update(TrainingProgramUpdateDTO trainingProgramUpdateDTO);

    TrainingProgram getEntityById(Integer id);

    TrainingProgramDTO getDTOById(Integer id);

    PageDTO<TrainingProgramDTO> filter(TrainingProgramFilter trainingProgramFilter, int page, int size);
}
