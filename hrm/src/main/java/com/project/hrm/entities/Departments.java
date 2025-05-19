package com.project.hrm.entities;

import com.project.hrm.utils.IdGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Departments {
    @Id
    private Integer id = IdGenerator.getGenerationId();

    private String departmentName;
    private String description;
    private String address;
    private String email;
    private String phone;

    public Departments(Departments departments) {
        this.id = IdGenerator.getGenerationId();
        this.departmentName = departments.getDepartmentName();
        this.description = departments.getDescription();
        this.email = departments.getEmail();
        this.address = departments.getAddress();
        this.phone = departments.getPhone();
    }
}
