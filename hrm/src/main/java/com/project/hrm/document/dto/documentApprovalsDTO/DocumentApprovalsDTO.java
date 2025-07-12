package com.project.hrm.document.dto.documentApprovalsDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentApprovalsDTO {
    private Integer id;
    private LocalDateTime approvalDate;
    private String status;
    private String reason;
    private Integer documentId;
    private Integer requestedById;
    private String requestedByName;
    private Integer approvedById;
    private String approverByName;
    private String documentName;
    private LocalDateTime requestedAt;

}
