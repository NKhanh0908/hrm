package com.project.hrm.performentEmployee.service;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.performentEmployee.dto.assignedWorkPersonDTO.AssignedWorkPersonCreateDTO;
import com.project.hrm.performentEmployee.dto.assignedWorkPersonDTO.AssignedWorkPersonDTO;
import com.project.hrm.performentEmployee.dto.assignedWorkPersonDTO.AssignedWorkPersonUpdateDTO;
import com.project.hrm.performentEmployee.entity.AssignedWorkPerson;
import org.springframework.stereotype.Service;

@Service
public interface AssignedWorkPersonService {
    AssignedWorkPersonDTO create(AssignedWorkPersonCreateDTO assignedWorkPersonCreateDTO);

    AssignedWorkPersonDTO update(AssignedWorkPersonUpdateDTO assignedWorkPersonUpdateDTO);

    AssignedWorkPerson getEntityById(Integer id);

    AssignedWorkPersonDTO getDtoById(Integer id);

    PageDTO<AssignedWorkPersonDTO> filterByEmployeeId(Integer employeeId, int page, int size);
}
