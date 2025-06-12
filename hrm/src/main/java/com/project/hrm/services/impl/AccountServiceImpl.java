package com.project.hrm.services.impl;

import com.project.hrm.dto.accountDTO.AccountCreateDTO;
import com.project.hrm.dto.accountDTO.AccountDTO;
import com.project.hrm.dto.accountDTO.AuthenticationDTO;
import com.project.hrm.dto.accountDTO.FormLoginDTO;
import com.project.hrm.entities.Account;
import com.project.hrm.entities.Employees;
import com.project.hrm.entities.Role;
import com.project.hrm.exceptions.CustomException;
import com.project.hrm.exceptions.Error;
import com.project.hrm.mapper.AccountMapper;
import com.project.hrm.repositories.AccountRepository;
import com.project.hrm.services.AccountService;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.services.RoleService;
import com.project.hrm.utils.IdGenerator;
import com.project.hrm.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    private final EmployeeService employeeService;
    private final RoleService roleService;

    /**
     * Authenticates a user based on the provided credentials and generates JWT tokens.
     *
     * <p>Process:
     * <ol>
     *   <li>Normalize the username (trim and lowercase).</li>
     *   <li>Retrieve the {@link com.project.hrm.entities.Account} by username.</li>
     *   <li>Validate the provided password against the stored hashed password.</li>
     *   <li>If valid, generate an access token and a refresh token via {@link com.project.hrm.utils.JwtTokenUtil}.</li>
     * </ol>
     *
     * @param formLoginDTO the login request containing username and password
     * @return an {@link AuthenticationDTO} containing the JWT access token, refresh token, and role name
     * @throws RuntimeException if the username does not exist, the password is invalid,
     *                          or token generation fails
     */
    @Transactional(readOnly = true)
    @Override
    public AuthenticationDTO signIn(FormLoginDTO formLoginDTO) {
        try {
            String name = formLoginDTO.getUsername().trim().toLowerCase();

            Account account = accountRepository.findByUsername(name)
                    .orElseThrow(() -> new CustomException(Error.ACCOUNT_NOT_FOUND));

            log.info("Account: {}", account);

            if (!passwordEncoder.matches(formLoginDTO.getPassword(), account.getPassword())) {
                throw new CustomException(Error.ACCOUNT_INVALID_PASSWORD);
            }

            try {
                String jwtToken = jwtTokenUtil.generateToken((UserDetails) account);
                String refreshToken = jwtTokenUtil.generateRefreshToken((UserDetails) account);
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

    /**
     * Creates a new user account in the system.
     *
     * <p>Process:
     * <ol>
     *   <li>Check if the requested username already exists.</li>
     *   <li>Retrieve associated {@link com.project.hrm.entities.Employees} by ID.</li>
     *   <li>Retrieve associated {@link com.project.hrm.entities.Role} by ID.</li>
     *   <li>Encode the password and populate account entity fields.</li>
     *   <li>Persist the new account via {@link com.project.hrm.repositories.AccountRepository}.</li>
     * </ol>
     *
     * @param accountCreateDTO the DTO containing the new account details, including username,
     *                         plain-text password, employeeId, and roleId
     * @return an {@link AccountDTO} representing the newly created account
     * @throws RuntimeException if the username already exists or referenced employee/role not found
     */
    @Transactional
    @Override
    public AccountDTO create(AccountCreateDTO accountCreateDTO) {
        String usernameRegister = accountCreateDTO.getUsername();

        if(usernameRegister.length() < 5){throw new CustomException(Error.ACCOUNT_USERNAME_TO_SHORT);}
        if(usernameRegister.length()> 25){throw new CustomException(Error.ACCOUNT_USERNAME_TO_LONG);}
        if(accountCreateDTO.getPassword().length()<5){throw new CustomException(Error.ACCOUNT_PASSWORD_TO_SHORT);}


        if (usernameExists(accountCreateDTO.getUsername())) {
            throw new CustomException(Error.ACCOUNT_ALREADY_EXISTS);
        }

        Employees employees = employeeService.getEntityById(accountCreateDTO.getEmployeeId());

        Role role = roleService.getEntityById(accountCreateDTO.getRoleId());

        Account account = accountMapper.convertCreateDTOToEntity(accountCreateDTO, employees, role);
        account.setId(IdGenerator.getGenerationId());
        account.setPassword(passwordEncoder.encode(accountCreateDTO.getUsername()));

        return accountMapper.toDTO(accountRepository.save(account));
    }

    private boolean usernameExists(String username) {
        return accountRepository.findByUsername(username).isPresent();
    }

}
