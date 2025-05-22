package com.project.hrm.mapper;

import com.project.hrm.dto.accountDTO.AccountCreateDTO;
import com.project.hrm.dto.accountDTO.AccountDTO;
import com.project.hrm.entities.Account;
import com.project.hrm.entities.Employees;
import com.project.hrm.entities.Role;
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
                .employeeName(account.getEmployees().fullName())
                .roleId(account.getRole().getId())
                .roleName(account.getRole().getName())
                .build();
    }

    public Account convertCreateDTOToEntity(AccountCreateDTO accountCreateDTO, Employees employees, Role role){
        return Account.builder()
                .username(accountCreateDTO.getUsername())
                .createAt(LocalDateTime.now())
                .status(true)
                .employees(employees)
                .role(role)
                .build();
    }

}
