package com.project.hrm.entities;

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
    private Integer id;
    private String departmentName;
    private String description;
    private String address;
    private String email;
    private String phone;
}
