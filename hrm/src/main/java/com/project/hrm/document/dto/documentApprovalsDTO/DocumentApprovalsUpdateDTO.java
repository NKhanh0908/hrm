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
public class DocumentApprovalsUpdateDTO {
    @NotNull(message = "Approval record ID is required")
    private Integer id;

    @NotNull(message = "Approval status is required")
    @Pattern(
            regexp = "PENDING|APPROVED|REJECTED|CANCELLED|EXPIRED",
            message = "Status must be one of: PENDING, APPROVED, REJECTED, CANCELLED, EXPIRED"
    )
    private String status;

    @NotNull(message = "Document ID is required")
    private Integer documentId;

    @NotNull(message = "Employee ID is required")
    private Integer employeeId;

    @Size(max = 500, message = "Reason must not exceed 500 characters")
    private String reason;
}
