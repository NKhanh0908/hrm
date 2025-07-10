package com.project.hrm.document.entity;

import com.project.hrm.entities.Employees;
import com.project.hrm.document.enums.DocumentsStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Documents {
    @Id
    private Integer id;
    private String title;
    private String description;
    private String filePath;
    private String fileType;
    private Long fileSize;
    private LocalDateTime uploadDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentsStatus documentStatus;

    @ManyToOne
    @JoinColumn
    private DocumentTypes documentTypes;

    @ManyToOne
    @JoinColumn
    private Employees uploadedBy;
}
