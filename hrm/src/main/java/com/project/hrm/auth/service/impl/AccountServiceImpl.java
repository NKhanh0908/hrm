package com.project.hrm.auth.service.impl;

import com.project.hrm.auth.dto.AccountCreateDTO;
import com.project.hrm.auth.dto.AccountDTO;
import com.project.hrm.auth.dto.AuthenticationDTO;
import com.project.hrm.auth.dto.FormLoginDTO;
import com.project.hrm.department.entity.Role;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.auth.entity.Account;
import com.project.hrm.common.exceptions.CustomException;
import com.project.hrm.common.exceptions.Error;
import com.project.hrm.auth.mapper.AccountMapper;
import com.project.hrm.auth.repository.AccountRepository;
import com.project.hrm.auth.service.AccountService;
import com.project.hrm.employee.service.EmployeeService;
import com.project.hrm.department.service.RoleService;
import com.project.hrm.common.utils.IdGenerator;
import com.project.hrm.auth.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    private final EmployeeService employeeService;

    public AccountServiceImpl(PasswordEncoder passwordEncoder,
                              JwtTokenUtil jwtTokenUtil,
                              AccountRepository accountRepository,
                              AccountMapper accountMapper,
                              @Lazy EmployeeService employeeService) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.employeeService = employeeService;

    }

    /**
     * Authenticates a user based on the provided credentials and generates JWT tokens.
     *
     * <p>Process:
     * <ol>
     *   <li>Normalize the username (trim and lowercase).</li>
     *   <li>Retrieve the {@link Account} by username.</li>
     *   <li>Validate the provided password against the stored hashed password.</li>
     *   <li>If valid, generate an access token and a refresh token via {@link JwtTokenUtil}.</li>
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
                        .role(account.getRole().name())
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
     *   <li>Retrieve associated {@link Employees} by ID.</li>
     *   <li>Retrieve associated {@link Role} by ID.</li>
     *   <li>Encode the password and populate account entity fields.</li>
     *   <li>Persist the new account via {@link AccountRepository}.</li>
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

        Account account = accountMapper.convertCreateDTOToEntity(accountCreateDTO, employees);
        account.setId(IdGenerator.getGenerationId());
        account.setPassword(passwordEncoder.encode(accountCreateDTO.getPassword()));

        return accountMapper.toDTO(accountRepository.save(account));
    }

    /**
     * Retrieves the {@link Employees} entity associated with the currently
     * authenticated user (principal) from the Spring Security context.
     *
     * <p>
     * The method performs the following steps:
     * <ol>
     *   <li>Obtains the {@link Authentication} object from {@link SecurityContextHolder}.</li>
     *   <li>Verifies that the authentication is not {@code null} and is authenticated;
     *       otherwise, throws {@link CustomException} with {@link Error#UNAUTHORIZED}.</li>
     *   <li>Extracts the {@link Account} object from {@link Authentication#getPrincipal()}.</li>
     *   <li>Logs the employee information for auditing purposes.</li>
     *   <li>Returns the associated {@link Employees} entity.</li>
     * </ol>
     * </p>
     *
     * @return the {@link Employees} entity of the currently authenticated user
     * @throws CustomException if no authenticated user is present in the security context
     */
    @Override
    public Employees getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new CustomException(Error.UNAUTHORIZED);
        }

        Account account = (Account) authentication.getPrincipal();

        log.info("User principal: {}", account);
        return account.getEmployees();
    }

    /**
     * @param employeeId
     * @return
     */
    @Override
    public String getUsernameByEmployeeId(Integer employeeId) {

        return accountRepository.getUserNameByEmployeeId(employeeId);
    }


    private boolean usernameExists(String username) {
        return accountRepository.findByUsername(username).isPresent();
    }

}
