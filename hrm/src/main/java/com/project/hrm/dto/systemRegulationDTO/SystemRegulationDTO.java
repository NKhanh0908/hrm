package com.project.hrm.dto.systemRegulationDTO;

import com.project.hrm.enums.SystemRegulationKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemRegulationDTO {
    private SystemRegulationKey key;
    private String value;
    private String description;
}
