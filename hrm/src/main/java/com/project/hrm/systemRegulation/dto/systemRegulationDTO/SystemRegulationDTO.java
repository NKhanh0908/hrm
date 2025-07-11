package com.project.hrm.systemRegulation.dto.systemRegulationDTO;

import com.project.hrm.systemRegulation.enums.SystemRegulationKey;
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
