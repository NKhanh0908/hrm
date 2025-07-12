package com.project.hrm.systemRegulation.service;

import com.project.hrm.systemRegulation.dto.systemRegulationDTO.SystemRegulationCreateDTO;
import com.project.hrm.systemRegulation.dto.systemRegulationDTO.SystemRegulationDTO;
import com.project.hrm.systemRegulation.dto.systemRegulationDTO.SystemRegulationUpdateDTO;
import com.project.hrm.systemRegulation.entity.SystemRegulation;
import com.project.hrm.systemRegulation.enums.SystemRegulationKey;
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