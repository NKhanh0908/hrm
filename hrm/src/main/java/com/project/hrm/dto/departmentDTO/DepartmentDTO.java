package com.project.hrm.dto.departmentDTO;

import com.project.hrm.dto.employeeDTO.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
    private Integer id;
    private String departmentName;
    private String description;
    private String address;
    private String email;
    private String phone;
    private EmployeeDTO deanDTO;
    //private List<EmployeeDTO> employeeDTOList;
}
