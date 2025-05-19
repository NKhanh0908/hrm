package com.project.hrm.services.impl;

import com.project.hrm.dto.accountDTO.AccountCreateDTO;
import com.project.hrm.dto.accountDTO.AccountDTO;
import com.project.hrm.dto.accountDTO.AuthenticationDTO;
import com.project.hrm.dto.accountDTO.FormLoginDTO;
import com.project.hrm.entities.Account;
import com.project.hrm.entities.Employees;
import com.project.hrm.entities.Role;
import com.project.hrm.mapper.AccountMapper;
import com.project.hrm.repositories.AccountRepository;
import com.project.hrm.services.AccountService;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.services.RoleService;
import com.project.hrm.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    private AccountRepository accountRepository;

    private AccountMapper accountMapper;

    private EmployeeService employeeService;
    private RoleService roleService;


    @Override
    public AuthenticationDTO signIn(FormLoginDTO formLoginDTO) {
        try {
            String name = formLoginDTO.getUsername().trim().toLowerCase();

            Account account = accountRepository.findByUsername(formLoginDTO.getUsername())
                    .orElseThrow(
                            () -> new RuntimeException("Username is exists")
                    );

            if (!passwordEncoder.matches(account.getPassword(), formLoginDTO.getPassword())) {
                throw new RuntimeException("TAIKHOAN INVALID PASSWORD");
            }

            UserDetails userDetails = (UserDetails) account;

            try {
                String jwtToken = jwtTokenUtil.generateToken(userDetails);
                String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);
                return AuthenticationDTO.builder()
                        .token(jwtToken)
                        .refreshToken(refreshToken)
                        .role(account.getRole().getName())
                        .build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AccountDTO create(AccountCreateDTO accountCreateDTO) {

        if (usernameExists(accountCreateDTO.getUsername())) {
            throw new RuntimeException("Account ALREADY_EXISTS");
        }

        Employees employees = employeeService.getEntityById(accountCreateDTO.getEmployeeId());

        Role role = roleService.getEntityById(accountCreateDTO.getRoleId());

        Account account = new Account();
        account.setUsername(account.getUsername());
        account.setPassword(accountCreateDTO.getPassword());
        account.setEmployees(employees);
        account.setRole(role);

        return accountMapper.toDTO(account);
    }

    private boolean usernameExists(String username) {
        return accountRepository.findByUsername(username).isPresent();
    }

}
