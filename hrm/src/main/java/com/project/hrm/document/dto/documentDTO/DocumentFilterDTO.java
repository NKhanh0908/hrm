package com.project.hrm.document.dto.documentDTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data

public class DocumentFilterDTO {
    public String status;
    public LocalDateTime startDate;
    public LocalDateTime endDate;
    public Integer documentId;
}
