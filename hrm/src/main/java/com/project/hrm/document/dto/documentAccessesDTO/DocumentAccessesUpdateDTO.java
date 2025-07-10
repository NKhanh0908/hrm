package com.project.hrm.document.dto.documentAccessesDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentAccessesUpdateDTO {
    private Integer id;
    private String accessLevel;
    private Integer documentId;
    private Integer employeeId;
}
