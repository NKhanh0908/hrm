package com.project.hrm.services.impl;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.dependentDTO.DependentCreateDTO;
import com.project.hrm.dto.dependentDTO.DependentDTO;
import com.project.hrm.dto.dependentDTO.DependentUpdateDTO;
import com.project.hrm.entities.Dependent;
import com.project.hrm.mapper.DependentMapper;
import com.project.hrm.repositories.DependentRepository;
import com.project.hrm.services.DependentService;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.utils.IdGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class DependentServiceImpl implements DependentService {
    private final DependentMapper dependentMapper;
    private final DependentRepository dependentRepository;
    private final EmployeeService employeeService;

    /**
     * Creates a new {@link Dependent} entity from the provided {@link DependentCreateDTO}.
     *
     * @param dependentCreateDTO the DTO containing dependent creation data
     * @return the created {@link DependentDTO} after being persisted
     */
    @Transactional
    @Override
    public DependentDTO create(DependentCreateDTO dependentCreateDTO) {
        log.info("Create Dependent");

        Dependent dependent = dependentMapper.toEntityFromCreateDTO(dependentCreateDTO);
        dependent.setEmployee(employeeService.getEntityById(dependentCreateDTO.getEmployeeId()));
        return dependentMapper.toDTO(dependentRepository.save(dependent));
    }

    /**
     * Updates an existing {@link Dependent} entity using data from the given {@link DependentUpdateDTO}.
     *
     * @param dependentUpdateDTO the DTO containing updated dependent information
     * @return the updated {@link DependentDTO}
     * @throws EntityNotFoundException if the dependent or related employee is not found
     */
    @Transactional
    @Override
    public DependentDTO update(DependentUpdateDTO dependentUpdateDTO) {
        log.info("Update Dependent with ID {}", dependentUpdateDTO.getId());

        Dependent dependent = getEntityById(dependentUpdateDTO.getId());

        if (dependentUpdateDTO.getName() != null && !dependentUpdateDTO.getName().isEmpty()) {
            dependent.setName(dependentUpdateDTO.getName());
        }

        if (dependentUpdateDTO.getBirthDate() != null) {
            dependent.setBirthDate(dependentUpdateDTO.getBirthDate());
        }

        if (dependentUpdateDTO.getRelationship() != null && !dependentUpdateDTO.getRelationship().isEmpty()) {
            dependent.setRelationship(dependentUpdateDTO.getRelationship());
        }

        if (dependentUpdateDTO.getEmployeeId() != null && employeeService.checkExists(dependentUpdateDTO.getEmployeeId())) {
            dependent.setEmployee(employeeService.getEntityById(dependentUpdateDTO.getEmployeeId()));
        }
        return dependentMapper.toDTO(dependentRepository.save(dependent));
    }

    /**
     * Deletes a {@link Dependent} entity by its ID.
     *
     * @param dependentId the ID of the dependent to delete
     * @throws EntityNotFoundException if no dependent is found with the given ID
     */
    @Transactional
    @Override
    public void delete(Integer dependentId) {
        log.info("Delete Dependent with dependentId: {}", dependentId);
        if(checkExists(dependentId)){
            dependentRepository.deleteById(dependentId);
        }else{
            throw new EntityNotFoundException("Dependent with id: " + dependentId + " not found");
        }
    }

    /**
     * Checks if a {@link Dependent} entity exists by its ID.
     *
     * @param dependentId the ID to check
     * @return true if the dependent exists, false otherwise
     */
    @Transactional(readOnly = true)
    @Override
    public Boolean checkExists(Integer dependentId) {
        log.info("Check Exists Dependent by ID: {}", dependentId);

        return dependentRepository.existsById(dependentId);
    }

    /**
     * Retrieves a {@link DependentDTO} by its ID.
     *
     * @param id the ID of the dependent
     * @return the corresponding {@link DependentDTO}
     * @throws EntityNotFoundException if the dependent is not found
     */
    @Transactional(readOnly = true)
    @Override
    public DependentDTO getById(Integer id) {
        log.info("Get Dependent DTO by ID: {}", id);

        return dependentMapper.toDTO(getEntityById(id));
    }

    /**
     * Retrieves a raw {@link Dependent} entity by its ID.
     *
     * @param id the ID of the dependent
     * @return the corresponding {@link Dependent} entity
     * @throws EntityNotFoundException if the dependent is not found
     */
    @Transactional(readOnly = true)
    @Override
    public Dependent getEntityById(Integer id) {
        log.info("Get Dependent Entity by ID: {}", id);

        return dependentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dependent not found with id: " + id));
    }

    /**
     * Retrieves a list of {@link DependentDTO} entities for a specific employee.
     *
     * @param employeeId the ID of the employee
     * @return a list of {@link DependentDTO} associated with the employee
     */
    @Transactional(readOnly = true)
    @Override
    public List<DependentDTO> getDependentsByEmployeeId(Integer employeeId) {
        List<Dependent> dependentList = dependentRepository.findByEmployeeId(employeeId);
        return dependentList.stream()
                .map(dependentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PageDTO<DependentDTO> getAllDependents(int page, int size) {
        log.info("Get all Dependents with page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<Dependent> dependentPage = dependentRepository.findAll(pageable);
        return dependentMapper.toDependentPageDTO(dependentPage);
    }

    @Override
    public int countDependentsOfEmployee(Integer employeeId) {
        return dependentRepository.countByEmployeeId(employeeId);
    }

    @Transactional(readOnly = true)
    @Override
    public Map<Integer, Integer> getDependentCountsForEmployees(List<Integer> employeeIds) {
        log.info("Getting dependent counts for {} employees", employeeIds.size());
        Map<Integer, Integer> result = new java.util.HashMap<>();

        List<Object[]> batchResults = dependentRepository.getBatchDependentCount(employeeIds);

        for (Object[] row : batchResults) {
            Integer employeeId = (Integer) row[0];
            Integer dependentCount = ((Number) row[1]).intValue();
            result.put(employeeId, dependentCount);
        }

        return result;
    }

}
