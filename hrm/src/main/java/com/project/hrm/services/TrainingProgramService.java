package com.project.hrm.services;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.trainingProgramDTO.TrainingProgramCreateDTO;
import com.project.hrm.dto.trainingProgramDTO.TrainingProgramDTO;
import com.project.hrm.dto.trainingProgramDTO.TrainingProgramFilter;
import com.project.hrm.dto.trainingProgramDTO.TrainingProgramUpdateDTO;
import com.project.hrm.entities.TrainingProgram;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TrainingProgramService {
    TrainingProgramDTO create(TrainingProgramCreateDTO trainingProgramCreateDTO);

    TrainingProgramDTO update(TrainingProgramUpdateDTO trainingProgramUpdateDTO);

    TrainingProgram getEntityById(Integer id);

    TrainingProgramDTO getDTOById(Integer id);

    PageDTO<TrainingProgramDTO> filter(TrainingProgramFilter trainingProgramFilter, int page, int size);
}
