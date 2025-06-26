package com.project.hrm.dto.systemRegulationDTO;

import com.project.hrm.enums.SystemRegulationKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemRegulationCreateDTO {
    private SystemRegulationKey key;
    private String value;
    private String description;
}
