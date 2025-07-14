package com.project.hrm.auth.mapper;

import com.project.hrm.auth.dto.AccountCreateDTO;
import com.project.hrm.auth.dto.AccountDTO;
import com.project.hrm.auth.entity.Account;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.auth.enums.AccountRole;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AccountMapper {
    public AccountDTO toDTO(Account account){
        return AccountDTO.builder()
                .id(account.getId())
                .username(account.getUsername())
                .createAt(account.getCreateAt())
                .status(account.getStatus())
                .employeeId(account.getEmployees().getId())
                .role(account.getRole().name())
                .build();
    }

    public Account convertCreateDTOToEntity(AccountCreateDTO accountCreateDTO, Employees employees){
        return Account.builder()
                .username(accountCreateDTO.getUsername())
                .createAt(LocalDateTime.now())
                .status(true)
                .employees(employees)
                .role(AccountRole.valueOf(accountCreateDTO.getRole()))
                .build();
    }

}
