package com.project.hrm.dto.documentAccessesDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentAccessesDTO {
    private Integer id;
    private String documentName;
    private String documentType;
    private String accessLevel;
    private String employeeName;
    private String employeeEmail;
    private String status;
    private Integer documentId;
    private Integer employeeId;
    private LocalDateTime createdAt;
}
