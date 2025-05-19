package com.project.hrm.entities;

import com.project.hrm.enums.StatusEmployee;
import com.project.hrm.utils.IdGenerator;
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
    private Integer id = IdGenerator.getGenerationId();
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
    private StatusEmployee status;

    public Employees(Employees employees) {
        this.id = IdGenerator.getGenerationId();
        this.firstName = employees.getFirstName();
        this.lastName = employees.getLastName();
        this.phone = employees.getPhone();
        this.gender = employees.getGender();
        this.position = employees.getPosition();
        this.image = employees.getImage();
        this.citizenIdentificationCard = employees.getCitizenIdentificationCard();
        this.status = employees.getStatus();
        this.address = employees.getAddress();
        this.dateOfBirth = employees.getDateOfBirth();
        this.email = employees.getEmail();
    }

    public String fullName(){
        return firstName + " " + lastName;
    }
}
