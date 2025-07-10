package com.project.hrm.training.service;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.training.dto.trainingRequestDTO.TrainingRequestCreateDTO;
import com.project.hrm.training.dto.trainingRequestDTO.TrainingRequestDTO;
import com.project.hrm.training.dto.trainingRequestDTO.TrainingRequestFilter;
import com.project.hrm.training.dto.trainingRequestDTO.TrainingRequestUpdateDTO;
import com.project.hrm.training.entity.TrainingRequest;
import org.springframework.stereotype.Service;

@Service
public interface TrainingRequestService {
    TrainingRequestDTO create(TrainingRequestCreateDTO trainingRequestCreateDTO);

    TrainingRequestDTO update(TrainingRequestUpdateDTO trainingRequestUpdateDTO);

    TrainingRequestDTO updateStatus(Integer id, String status);

    TrainingRequest getEntityById(Integer id);

    TrainingRequestDTO getDTOById(Integer id);

    PageDTO<TrainingRequestDTO> filter(TrainingRequestFilter trainingRequestFilter, int page, int size);
}
