package com.project.hrm.entities;

import com.project.hrm.enums.EmployeeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String dateOfBirth;
    private String address;
    private String position;
    private String image;
    private String citizenIdentificationCard;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmployeeStatus status;

    public String fullName() {
        return firstName + " " + lastName;
    }
}
