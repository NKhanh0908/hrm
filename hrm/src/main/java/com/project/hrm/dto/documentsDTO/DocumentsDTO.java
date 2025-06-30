package com.project.hrm.dto.documentsDTO;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class DocumentsDTO {
    private Integer id;
    private String title;
    private String description;
    private String filePath;
    private String fileType;
    private Integer fileSize;
    private LocalDateTime uploadDate;
    private String documentStatus;
    private Integer documentTypeId;
    private Integer uploadedById;
}
