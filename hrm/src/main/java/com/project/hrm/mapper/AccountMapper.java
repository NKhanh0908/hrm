package com.project.hrm.mapper;

import com.project.hrm.dto.accountDTO.AccountDTO;
import com.project.hrm.entities.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public AccountDTO toDTO(Account account){
        return AccountDTO.builder()
                .id(account.getId())
                .username(account.getUsername())
                .createAt(account.getCreateAt())
                .status(account.getStatus())
                .employeeId(account.getEmployees().getId())
                .employeeName(account.getEmployees().fullName())
                .roleId(account.getRole().getId())
                .roleName(account.getRole().getName())
                .build();
    }

}
