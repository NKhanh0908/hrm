package com.project.hrm.document.dto.documentApprovalsDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentApprovalsCreateDTO {

    @NotNull(message = "Document ID is required")
    private Integer documentId;

    @Size(max = 500, message = "Reason must not exceed 500 characters")
    private String reason;
}
