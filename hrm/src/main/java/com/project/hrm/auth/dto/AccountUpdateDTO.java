package com.project.hrm.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountUpdateDTO {
    private Integer id;
    private String username;
    private LocalDateTime createAt;
    private Boolean status;
    private Integer employeeId;
    private Integer roleId;
}
