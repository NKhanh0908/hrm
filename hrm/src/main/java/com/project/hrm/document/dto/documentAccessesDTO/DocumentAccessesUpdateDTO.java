package com.project.hrm.document.dto.documentAccessesDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentAccessesUpdateDTO {
    @NotNull(message = "Access record ID is required")
    private Integer id;

    @NotNull(message = "Access level is required")
    @Pattern(
            regexp = "VIEW|DOWNLOAD|EDIT|DELETE|FULL_ACCESS|APPROVE",
            message = "Access level must be one of: VIEW, DOWNLOAD, EDIT, DELETE, FULL_ACCESS, APPROVE"
    )
    private String accessLevel;

    @NotNull(message = "Document ID is required")
    private Integer documentId;

    @NotNull(message = "Employee ID is required")
    private Integer employeeId;
}
