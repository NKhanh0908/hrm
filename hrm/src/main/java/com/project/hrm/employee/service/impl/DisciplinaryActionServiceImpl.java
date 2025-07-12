package com.project.hrm.employee.service.impl;

import com.project.hrm.employee.dto.disciplinaryActionDTO.DisciplinaryActionCreateDTO;
import com.project.hrm.employee.dto.disciplinaryActionDTO.DisciplinaryActionDTO;
import com.project.hrm.employee.dto.disciplinaryActionDTO.DisciplinaryActionUpdateDTO;
import com.project.hrm.employee.entity.DisciplinaryAction;
import com.project.hrm.employee.mapper.DisciplinaryActionMapper;
import com.project.hrm.employee.repository.DisciplinaryActionRepository;
import com.project.hrm.employee.service.DisciplinaryActionService;
import com.project.hrm.employee.service.EmployeeService;
import com.project.hrm.payroll.services.RegulationsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class DisciplinaryActionServiceImpl implements DisciplinaryActionService {
    private final DisciplinaryActionMapper disciplinaryActionMapper;
    private final EmployeeService employeeService;
    private final RegulationsService regulationsService;
    private final DisciplinaryActionRepository disciplinaryActionRepository;

    @Transactional
    @Override
    public DisciplinaryActionDTO createDisciplinaryAction(DisciplinaryActionCreateDTO disciplinaryActionCreateDTO) {
        log.info("Create disciplinary action: {}", disciplinaryActionCreateDTO);
        DisciplinaryAction disciplinaryAction = disciplinaryActionMapper.toEntityFromCreateDTO(disciplinaryActionCreateDTO);

        disciplinaryAction.setEmployee(employeeService.getEntityById(disciplinaryActionCreateDTO.getEmployeeId()));

        disciplinaryAction.setRegulation(regulationsService.getEntityById(disciplinaryActionCreateDTO.getRegulationId()));


        return disciplinaryActionMapper.toDTO(disciplinaryActionRepository.save(disciplinaryAction));
    }

    @Transactional
    @Override
    public DisciplinaryActionDTO updateDisciplinaryAction(DisciplinaryActionUpdateDTO disciplinaryActionUpdateDTO) {
        log.info("Update disciplinary action: {}", disciplinaryActionUpdateDTO);

        DisciplinaryAction disciplinaryAction = getEntity(disciplinaryActionUpdateDTO.getId());

        if (disciplinaryActionUpdateDTO.getEmployeeId() != null && employeeService.checkExists(disciplinaryActionUpdateDTO.getEmployeeId())) {
            disciplinaryAction.setEmployee(employeeService.getEntityById(disciplinaryActionUpdateDTO.getEmployeeId()));
        }

        if (disciplinaryActionUpdateDTO.getRegulationId() != null && regulationsService.checkExists(disciplinaryActionUpdateDTO.getRegulationId())) {
            disciplinaryAction.setRegulation(regulationsService.getEntityById(disciplinaryActionUpdateDTO.getRegulationId()));
        }

        if (disciplinaryActionUpdateDTO.getDescription() != null && !disciplinaryActionUpdateDTO.getDescription().trim().isEmpty()) {
            disciplinaryAction.setDescription(disciplinaryActionUpdateDTO.getDescription());
        }

        if (disciplinaryActionUpdateDTO.getDate() != null) {
            disciplinaryAction.setDate(disciplinaryActionUpdateDTO.getDate());
        }

        if (disciplinaryActionUpdateDTO.getPenaltyAmount() != null) {
            disciplinaryAction.setPenaltyAmount(disciplinaryActionUpdateDTO.getPenaltyAmount());
        } else {
            disciplinaryAction.setPenaltyAmount(null);
        }

        if (disciplinaryActionUpdateDTO.getResolved() != null) {
            disciplinaryAction.setResolved(disciplinaryActionUpdateDTO.getResolved());
        }

        if (disciplinaryActionUpdateDTO.getSeverity() != null) {
            disciplinaryAction.setSeverity(disciplinaryActionUpdateDTO.getSeverity());
        }
        return disciplinaryActionMapper.toDTO(disciplinaryActionRepository.save(disciplinaryAction));
    }

    @Transactional
    @Override
    public void deleteDisciplinaryAction(Integer id) {
        log.info("Delete disciplinary action: {}", id);

        if (checkExist(id)){
            disciplinaryActionRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Disciplinary action with id " + id + " not found");
        }

    }

    @Transactional(readOnly = true)
    @Override
    public DisciplinaryActionDTO getDTO(Integer id) {
        log.info("Get disciplinary action DTO by id: {}", id);
        return disciplinaryActionMapper.toDTO(getEntity(id));
    }

    @Transactional(readOnly = true)
    @Override
    public DisciplinaryAction getEntity(Integer id) {
        log.info("Get disciplinary action by id: {}", id);
        return disciplinaryActionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Disciplinary action not found with id: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean checkExist(Integer id) {
        log.info("Check exist disciplinary action: {}", id);
        return disciplinaryActionRepository.existsById(id);
    }

    @Override
    public List<DisciplinaryActionDTO> getDisciplinaryActionByEmployeeIdAndDate(Integer id, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Get disciplinary action by employee id and date: {}, {} - {}", id, startDate, endDate);

        List<DisciplinaryAction> disciplinaryActionList = disciplinaryActionRepository.findAllByEmployeeIdAndDateBetween(id, startDate, endDate);
        return disciplinaryActionList.stream()
                .map(disciplinaryActionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Map<Integer, List<DisciplinaryActionDTO>> getBatchDisciplinaryActions(List<Integer> employeeIds, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Getting batch disciplinary actions for {} employees", employeeIds.size());
        Map<Integer, List<DisciplinaryActionDTO>> result = new java.util.HashMap<>();

        List<Object[]> batchResults = disciplinaryActionRepository.getBatchDisciplinaryActions(employeeIds, startDate, endDate);

        for (Object[] row : batchResults) {
            Integer employeeId = (Integer) row[0];
            DisciplinaryAction disciplinaryAction = (DisciplinaryAction) row[1];

            result.computeIfAbsent(employeeId, k -> new java.util.ArrayList<>())
                    .add(disciplinaryActionMapper.toDTO(disciplinaryAction));
        }

        return result;
    }
}
