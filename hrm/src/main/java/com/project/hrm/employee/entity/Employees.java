package com.project.hrm.employee.entity;

import com.project.hrm.department.entity.Role;
import com.project.hrm.employee.enums.EmployeeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "Employees.getTotalEmployeeByDepartment",
                procedureName = "get_total_employee_by_department"
        ),
        @NamedStoredProcedureQuery(
                name = "Employees.getTotalEmployeeByRole",
                procedureName = "get_total_employee_by_role"
        ),
        @NamedStoredProcedureQuery(
                name = "Employees.getTotalEmployeeByDepartmentAndRole",
                procedureName = "get_total_employee_by_department_and_role"
        ),
        @NamedStoredProcedureQuery(
                name = "Employees.getEmployeesByDepartmentId",
                procedureName = "get_employees_by_department_id",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "dep_id", type = Integer.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "limit_size", type = Integer.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "offset_val", type = Integer.class)
                }
        )
})

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employees {
    @Id
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String gender;
    private LocalDate dateOfBirth;
    private String address;
    private String image;
    private String citizenIdentificationCard;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmployeeStatus status;

    @ManyToOne
    @JoinColumn
    private Role role;

    public String fullName() {
        return firstName + " " + lastName;
    }
}
