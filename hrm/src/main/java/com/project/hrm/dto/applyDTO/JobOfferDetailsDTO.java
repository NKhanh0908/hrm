package com.project.hrm.dto.applyDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobOfferDetailsDTO {
    private LocalDateTime reportDateTime;
    private String reportLocation;
    private String contactPersonName;
    private String contactEmail;
    private String contactPhone;
}
