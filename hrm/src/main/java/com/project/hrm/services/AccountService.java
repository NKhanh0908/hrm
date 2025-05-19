package com.project.hrm.services;

import com.project.hrm.dto.accountDTO.AccountCreateDTO;
import com.project.hrm.dto.accountDTO.AccountDTO;
import com.project.hrm.dto.accountDTO.AuthenticationDTO;
import com.project.hrm.dto.accountDTO.FormLoginDTO;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {


    AuthenticationDTO signIn(FormLoginDTO formLoginDTO);

    AccountDTO create(AccountCreateDTO accountCreateDTO);


}
