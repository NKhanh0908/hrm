package com.project.hrm.dto.documentAccessesDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentAccessesCreateDTO {
    private String accessLevel;
    private Integer documentId;
    private Integer employeeId;

}
