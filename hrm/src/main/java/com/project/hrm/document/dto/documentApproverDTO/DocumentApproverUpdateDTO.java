package com.project.hrm.document.dto.documentApproverDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentApproverUpdateDTO {
    private Integer id;
    private Integer documentId;
    private Integer approverId;
    private boolean canApprove = true;
    private String approverName;
}
