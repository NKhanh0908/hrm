package com.project.hrm.services.impl;

import com.project.hrm.dto.systemRegulationDTO.SystemRegulationCreateDTO;
import com.project.hrm.dto.systemRegulationDTO.SystemRegulationDTO;
import com.project.hrm.dto.systemRegulationDTO.SystemRegulationUpdateDTO;
import com.project.hrm.entities.SystemRegulation;
import com.project.hrm.enums.SystemRegulationKey;
import com.project.hrm.mapper.SystemRegulationMapper;
import com.project.hrm.repositories.SystemRegulationRepository;
import com.project.hrm.services.SystemRegulationService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class SystemRegulationServiceImpl implements SystemRegulationService {

    private final SystemRegulationRepository systemRegulationRepository;
    private final SystemRegulationMapper systemRegulationMapper;

    @Override
    public List<SystemRegulationDTO> getAllSystemRegulations() {
        log.info("get all system regulations");
        return systemRegulationRepository.findAll()
                .stream()
                .map(systemRegulationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "systemRegulations", key = "#key")
    public String getValue(SystemRegulationKey key) {
        log.info("Get system regulation value: {}", key);
        return systemRegulationRepository.findById(key)
                .map(SystemRegulation::getValue)
                .orElseThrow(() -> new EntityNotFoundException("System Regulation not found: " + key));
    }

    @Transactional
    @Override
    @CacheEvict(value = "systemRegulations", key = "#key")
    public void setValue(SystemRegulationKey key, String value) {
        SystemRegulation regulation = systemRegulationRepository.findById(key)
                .orElseThrow(() -> new EntityNotFoundException("Regulation not found: " + key));
        regulation.setValue(value);
        systemRegulationRepository.save(regulation);
    }

    @Transactional
    @Override
    @CacheEvict(value = "systemRegulations", allEntries = true)
    public SystemRegulationDTO createSystemRegulation(SystemRegulationCreateDTO systemRegulationCreateDTO) {
        log.info("Create system regulation: {}", systemRegulationCreateDTO);

        if (checkExistence(systemRegulationCreateDTO.getKey())){
            throw new EntityExistsException("System Regulation already exists: " + systemRegulationCreateDTO.getKey());
        }
        SystemRegulation regulation = new SystemRegulation();
        regulation.setValue(systemRegulationCreateDTO.getValue());
        regulation.setKey(systemRegulationCreateDTO.getKey());
        regulation.setDescription(systemRegulationCreateDTO.getDescription());
        return systemRegulationMapper.toDTO(systemRegulationRepository.save(regulation));
    }


    @Transactional
    @Override
    @CacheEvict(value = "systemRegulations", key = "#systemRegulationUpdateDTO.key")
    public SystemRegulationDTO updateSystemRegulation(SystemRegulationUpdateDTO systemRegulationUpdateDTO) {
        log.info("Update system regulation: {}", systemRegulationUpdateDTO);
        if (!checkExistence(systemRegulationUpdateDTO.getKey())){
            throw new EntityExistsException("System Regulation not found: " + systemRegulationUpdateDTO.getKey());
        }

        SystemRegulation systemRegulation = getEntity(systemRegulationUpdateDTO.getKey());

        if (!systemRegulation.getDescription().equals(systemRegulationUpdateDTO.getDescription())){
            systemRegulation.setDescription(systemRegulationUpdateDTO.getDescription());
        }

        if (!systemRegulation.getValue().equals(systemRegulationUpdateDTO.getValue())){
            systemRegulation.setValue(systemRegulationUpdateDTO.getValue());
        }

        systemRegulationRepository.save(systemRegulation);
        return systemRegulationMapper.toDTO(systemRegulation);

    }

    @Override
    @CacheEvict(value = "systemRegulations", key = "#key")
    public void deleteSystemRegulation(SystemRegulationKey key) {
        log.info("Deleting system regulation:{} ",key);
    }

    @Override
    public boolean checkExistence(SystemRegulationKey key) {
        log.info("Check existence of system regulation:{} ",key);
        return systemRegulationRepository.existsById(key);
    }

    @Override
    public SystemRegulation getEntity(SystemRegulationKey key) {
        log.info("Get system regulation:{} ",key);
        return systemRegulationRepository.findById(key)
                .orElseThrow(() -> new EntityNotFoundException("System Regulation not found: " + key));
    }
}