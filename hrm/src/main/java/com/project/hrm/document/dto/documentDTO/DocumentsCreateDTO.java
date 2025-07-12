package com.project.hrm.document.dto.documentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentsCreateDTO {
    private String title;
    private String description;
    private MultipartFile file;
    private String documentStatus;
    private Integer documentTypeId;
    private Integer uploadedById;
}
