package com.project.hrm.department.dto.departmentDTO;

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
    // private List<EmployeeDTO> employeeDTOList;
}
