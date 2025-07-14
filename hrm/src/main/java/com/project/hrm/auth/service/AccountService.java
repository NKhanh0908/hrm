package com.project.hrm.auth.service;

import com.project.hrm.auth.dto.AccountCreateDTO;
import com.project.hrm.auth.dto.AccountDTO;
import com.project.hrm.auth.dto.AuthenticationDTO;
import com.project.hrm.auth.dto.FormLoginDTO;
import com.project.hrm.auth.entity.Account;
import com.project.hrm.employee.entity.Employees;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    AuthenticationDTO signIn(FormLoginDTO formLoginDTO);

    AccountDTO create(AccountCreateDTO accountCreateDTO);

    Account getAccountAuth();

    AccountDTO getAccountByEmployeeId(Integer employeeId);

    Employees getPrincipal();

    String getUsernameByEmployeeId(Integer employeeId);

}
