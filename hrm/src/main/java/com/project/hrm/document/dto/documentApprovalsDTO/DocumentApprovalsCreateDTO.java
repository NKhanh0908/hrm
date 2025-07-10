package com.project.hrm.document.dto.documentApprovalsDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentApprovalsCreateDTO {
    private String status;
    private Integer documentId;
    private String reason;
}
