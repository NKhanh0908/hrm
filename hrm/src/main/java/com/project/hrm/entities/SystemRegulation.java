package com.project.hrm.entities;


import com.project.hrm.enums.SystemRegulationKey;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "system_regulation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemRegulation {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "system_regulation_key", nullable = false, unique = true)
    private SystemRegulationKey key; // Mã định danh quy định

    @Column(name = "system_regulation_value", nullable = false)
    private String value; // Giá trị của quy định

    @Column(name = "description")
    private String description; // Mô tả quy định
}