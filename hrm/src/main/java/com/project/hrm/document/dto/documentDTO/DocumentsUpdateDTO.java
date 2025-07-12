package com.project.hrm.document.dto.documentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentsUpdateDTO {
    private Integer id;
    private String title;
    private String description;
    private Integer documentTypeId;
    private Integer uploadedById;
    private String documentStatus;
}
