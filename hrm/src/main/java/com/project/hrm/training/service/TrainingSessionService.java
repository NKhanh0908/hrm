package com.project.hrm.training.service;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.training.dto.trainingSession.TrainingSessionCreateDTO;
import com.project.hrm.training.dto.trainingSession.TrainingSessionDTO;
import com.project.hrm.training.dto.trainingSession.TrainingSessionFilter;
import com.project.hrm.training.dto.trainingSession.TrainingSessionUpdateDTO;
import com.project.hrm.training.entity.TrainingSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TrainingSessionService {
    TrainingSessionDTO create(TrainingSessionCreateDTO trainingSessionCreateDTO);

    TrainingSessionDTO update(TrainingSessionUpdateDTO trainingSessionUpdateDTO);

    TrainingSessionDTO getDTOById(Integer id);

    TrainingSession getEntityById(Integer id);

    List<TrainingSessionDTO> getAllByTrainingProgramId(Integer trainingProgramId);

    PageDTO<TrainingSessionDTO> filter(TrainingSessionFilter trainingSessionFilter, int page, int size);
}
