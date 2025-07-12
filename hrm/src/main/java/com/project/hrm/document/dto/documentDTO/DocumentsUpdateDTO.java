package com.project.hrm.document.dto.documentDTO;

import jakarta.validation.constraints.NotBlank;
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
public class DocumentsUpdateDTO {

    @NotNull(message = "Document ID is required")
    private Integer id;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "Document type ID is required")
    private Integer documentTypeId;

    @NotNull(message = "Uploader ID is required")
    private Integer uploadedById;

    @NotNull(message = "Document status is required")
    @Pattern(
            regexp = "PENDING|APPROVED|REJECTED|ARCHIVED|DELETED",
            message = "Status must be one of: PENDING, APPROVED, REJECTED, ARCHIVED, DELETED"
    )
    private String documentStatus;
}
