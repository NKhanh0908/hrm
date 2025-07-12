package com.project.hrm.document.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTypes {
    @Id
    private Integer id;
    private String name;
    private String description;
}
