package com.project.hrm.dto.accountDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private Integer id;
    private String username;
    private LocalDateTime createAt;
    private Boolean status;
    private Integer employeeId;
    private String employeeName;
    private Integer roleId;
    private String roleName;
}
