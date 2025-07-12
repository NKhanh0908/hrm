package com.project.hrm.document.dto.documentTypeDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTypeUpdateDTO {
    @NotNull(message = "Id document type is required")
    private Integer id;

    @NotBlank(message = "Name document type is required")
    @Size(max = 100, message = "Name document must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Description document type is required")
    @Size(max = 500, message = "Description document must not exceed 500 characters")
    private String description;
}
