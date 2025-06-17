package com.project.hrm.services;

import com.project.hrm.dto.trainingRequestDTO.TrainingRequestCreateDTO;
import com.project.hrm.dto.trainingRequestDTO.TrainingRequestDTO;
import com.project.hrm.dto.trainingRequestDTO.TrainingRequestFilter;
import com.project.hrm.entities.TrainingRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TrainingRequestService {
    TrainingRequestDTO create(TrainingRequestCreateDTO trainingRequestCreateDTO);

    TrainingRequest getEntityById(Integer id);

    TrainingRequestDTO getDTOById(Integer id);

    List<TrainingRequestDTO> filter(TrainingRequestFilter trainingRequestFilter, int page, int size);
}
