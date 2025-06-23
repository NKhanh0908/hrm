package com.project.hrm.dto.documentApprovalsDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentApprovalsUpdateDTO {
    private Integer id;
    private String status;
    private Integer documentId;
    private Integer employeeId;
    private String reason;
}
