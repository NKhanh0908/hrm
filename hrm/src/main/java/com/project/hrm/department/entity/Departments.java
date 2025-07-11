package com.project.hrm.department.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "getDepartmentsByEmployeeId",
                procedureName = "get_departments_by_employee_id",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "employee_id", type = Integer.class),
                }
        )
})

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Departments {
    @Id
    private Integer id;
    private String departmentName;
    private String description;
    private String address;
    private String email;
    private String phone;

}
