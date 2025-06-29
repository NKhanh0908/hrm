package com.project.hrm.services;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.assignedWorkPersonDTO.AssignedWorkPersonCreateDTO;
import com.project.hrm.dto.assignedWorkPersonDTO.AssignedWorkPersonDTO;
import com.project.hrm.dto.assignedWorkPersonDTO.AssignedWorkPersonUpdateDTO;
import com.project.hrm.entities.AssignedWorkPerson;
import org.springframework.stereotype.Service;

@Service
public interface AssignedWorkPersonService {
    AssignedWorkPersonDTO create(AssignedWorkPersonCreateDTO assignedWorkPersonCreateDTO);

    AssignedWorkPersonDTO update(AssignedWorkPersonUpdateDTO assignedWorkPersonUpdateDTO);

    AssignedWorkPerson getEntityById(Integer id);

    AssignedWorkPersonDTO getDtoById(Integer id);

    PageDTO<AssignedWorkPersonDTO> filterByEmployeeId(Integer employeeId, int page, int size);
}
