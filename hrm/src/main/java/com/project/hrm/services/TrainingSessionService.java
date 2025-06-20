package com.project.hrm.services;

import com.project.hrm.dto.trainingSession.TrainingSessionCreateDTO;
import com.project.hrm.dto.trainingSession.TrainingSessionDTO;
import com.project.hrm.dto.trainingSession.TrainingSessionFilter;
import com.project.hrm.dto.trainingSession.TrainingSessionUpdateDTO;
import com.project.hrm.entities.TrainingSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TrainingSessionService {
    TrainingSessionDTO create(TrainingSessionCreateDTO trainingSessionCreateDTO);

    TrainingSessionDTO update(TrainingSessionUpdateDTO trainingSessionUpdateDTO);

    TrainingSessionDTO getDTOById(Integer id);

    TrainingSession getEntityById(Integer id);

    List<TrainingSessionDTO> getAllByTrainingProgramId(Integer trainingProgramId);

    List<TrainingSessionDTO> filter(TrainingSessionFilter trainingSessionFilter, int page, int size);
}
