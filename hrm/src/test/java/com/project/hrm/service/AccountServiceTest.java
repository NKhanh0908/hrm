package com.project.hrm.service;

import com.project.hrm.auth.dto.AccountCreateDTO;
import com.project.hrm.auth.entity.Account;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.department.entity.Role;
import com.project.hrm.common.exceptions.CustomException;
import com.project.hrm.common.exceptions.Error;
import com.project.hrm.auth.repository.AccountRepository;
import com.project.hrm.auth.service.AccountService;
import com.project.hrm.employee.service.EmployeeService;
import com.project.hrm.department.service.RoleService;
import com.project.hrm.auth.service.impl.OurUserDetailsService;
import com.project.hrm.common.utils.IdGenerator;
import com.project.hrm.auth.util.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

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
