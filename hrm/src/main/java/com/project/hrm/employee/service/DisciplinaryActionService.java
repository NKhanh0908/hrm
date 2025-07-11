package com.project.hrm.employee.service;

import com.project.hrm.employee.dto.disciplinaryActionDTO.DisciplinaryActionCreateDTO;
import com.project.hrm.employee.dto.disciplinaryActionDTO.DisciplinaryActionDTO;
import com.project.hrm.employee.dto.disciplinaryActionDTO.DisciplinaryActionUpdateDTO;
import com.project.hrm.employee.entity.DisciplinaryAction;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public interface DisciplinaryActionService {

    DisciplinaryActionDTO createDisciplinaryAction(DisciplinaryActionCreateDTO disciplinaryActionCreateDTO);

    DisciplinaryActionDTO updateDisciplinaryAction(DisciplinaryActionUpdateDTO disciplinaryActionUpdateDTO);

    void deleteDisciplinaryAction(Integer id);

    DisciplinaryActionDTO getDTO(Integer id);

    DisciplinaryAction getEntity(Integer id);

    Boolean checkExist(Integer id);

    List<DisciplinaryActionDTO> getDisciplinaryActionByEmployeeIdAndDate(Integer id, LocalDateTime startDate, LocalDateTime endDate);

    Map<Integer, List<DisciplinaryActionDTO>> getBatchDisciplinaryActions(List<Integer> employeeIds, LocalDateTime startDate, LocalDateTime endDate);


}
