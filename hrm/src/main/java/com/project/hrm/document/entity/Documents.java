package com.project.hrm.document.entity;

import com.project.hrm.department.entity.Departments;
import com.project.hrm.document.enums.DocumentScope;
import com.project.hrm.employee.entity.Employees;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Departments department;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentScope documentScope;
}
