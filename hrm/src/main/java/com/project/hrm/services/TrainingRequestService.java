package com.project.hrm.services;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.trainingRequestDTO.TrainingRequestCreateDTO;
import com.project.hrm.dto.trainingRequestDTO.TrainingRequestDTO;
import com.project.hrm.dto.trainingRequestDTO.TrainingRequestFilter;
import com.project.hrm.dto.trainingRequestDTO.TrainingRequestUpdateDTO;
import com.project.hrm.entities.TrainingRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TrainingRequestService {
    TrainingRequestDTO create(TrainingRequestCreateDTO trainingRequestCreateDTO);

    TrainingRequestDTO update(TrainingRequestUpdateDTO trainingRequestUpdateDTO);

    TrainingRequestDTO updateStatus(Integer id, String status);

    TrainingRequest getEntityById(Integer id);

    TrainingRequestDTO getDTOById(Integer id);

    PageDTO<TrainingRequestDTO> filter(TrainingRequestFilter trainingRequestFilter, int page, int size);
}
