package com.project.hrm.services;

import com.project.hrm.dto.systemRegulationDTO.SystemRegulationCreateDTO;
import com.project.hrm.dto.systemRegulationDTO.SystemRegulationDTO;
import com.project.hrm.dto.systemRegulationDTO.SystemRegulationUpdateDTO;
import com.project.hrm.entities.SystemRegulation;
import com.project.hrm.enums.SystemRegulationKey;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SystemRegulationService {

    List<SystemRegulationDTO> getAllSystemRegulations();

    String getValue(SystemRegulationKey key);

    void setValue(SystemRegulationKey key, String value);

    SystemRegulationDTO createSystemRegulation(SystemRegulationCreateDTO systemRegulationCreateDTO);

    SystemRegulationDTO updateSystemRegulation(SystemRegulationUpdateDTO systemRegulationUpdateDTO);

    void deleteSystemRegulation(SystemRegulationKey key);

    boolean checkExistence(SystemRegulationKey key);

    SystemRegulation getEntity(SystemRegulationKey key);
}