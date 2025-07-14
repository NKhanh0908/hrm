package com.project.hrm.document.dto.documentApproverDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentApproverCreateDTO {
    @NotNull(message = "Document ID is required")
    private Integer documentId;

    @NotNull(message = "Approver ID is required")
    private Integer approverId;
    private boolean canApprove = true;
}
