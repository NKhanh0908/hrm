package com.project.hrm.services;

import com.project.hrm.dto.assignedWorkPersonDTO.AssignedWorkPersonCreateDTO;
import com.project.hrm.dto.assignedWorkPersonDTO.AssignedWorkPersonDTO;
import com.project.hrm.dto.assignedWorkPersonDTO.AssignedWorkPersonUpdateDTO;
import com.project.hrm.entities.AssignedWorkPerson;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AssignedWorkPersonService {
    AssignedWorkPersonDTO create(AssignedWorkPersonCreateDTO assignedWorkPersonCreateDTO);

    AssignedWorkPersonDTO update(AssignedWorkPersonUpdateDTO assignedWorkPersonUpdateDTO);

    AssignedWorkPerson getEntityById(Integer id);

    AssignedWorkPersonDTO getDtoById(Integer id);

    List<AssignedWorkPersonDTO> filterByEmployeeId(Integer employeeId);
}
