package com.project.hrm.service;

import com.project.hrm.dto.accountDTO.AccountCreateDTO;
import com.project.hrm.dto.accountDTO.AccountDTO;
import com.project.hrm.dto.accountDTO.AuthenticationDTO;
import com.project.hrm.entities.Account;
import com.project.hrm.entities.Employees;
import com.project.hrm.entities.Role;
import com.project.hrm.exceptions.CustomException;
import com.project.hrm.exceptions.Error;
import com.project.hrm.repositories.AccountRepository;
import com.project.hrm.services.AccountService;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.services.RoleService;
import com.project.hrm.services.impl.OurUserDetailsService;
import com.project.hrm.utils.IdGenerator;
import com.project.hrm.utils.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private OurUserDetailsService ourUserDetailsService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private AccountService accountService;
    @Mock
    private EmployeeService employeeService;
    @Mock
    private RoleService roleService;

    private Account account;

    private AccountCreateDTO accountCreateDTO;

    @BeforeEach
    void setUp(){
        account = new Account();
        account.setId(IdGenerator.getGenerationId());
        account.setUsername("k123");
        account.setPassword("k123");

        accountCreateDTO = new AccountCreateDTO();
        accountCreateDTO.setUsername("k123");
        accountCreateDTO.setPassword("k123");
        accountCreateDTO.setEmployeeId(21);
    }

    @Test
    void create_WithExistingUsername_ShouldThrowCustomException() {
        when(accountRepository.findByUsername(accountCreateDTO.getUsername()))
                .thenReturn(Optional.of(new Account()));

        when(employeeService.getEntityById(any())).thenReturn(new Employees());
        when(roleService.getEntityById(any())).thenReturn(new Role());
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        CustomException exception = assertThrows(CustomException.class, () -> {
            accountService.create(accountCreateDTO);
        });

        assertEquals(
                Error.ACCOUNT_ALREADY_EXISTS,
                exception.getErrors().get(0)
        );

        // VERIFY
        verify(accountRepository, times(1)).findByUsername(accountCreateDTO.getUsername());
        verifyNoInteractions(employeeService, roleService, passwordEncoder);
    }

}
