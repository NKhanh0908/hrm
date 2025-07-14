package com.project.hrm.auth.service.impl;

import com.project.hrm.auth.dto.*;
import com.project.hrm.auth.util.LoginAttemptService;
import com.project.hrm.auth.util.OtpService;
import com.project.hrm.common.service.MailService;
import com.project.hrm.department.entity.Role;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.auth.entity.Account;
import com.project.hrm.common.exceptions.CustomException;
import com.project.hrm.common.exceptions.Error;
import com.project.hrm.auth.mapper.AccountMapper;
import com.project.hrm.auth.repository.AccountRepository;
import com.project.hrm.auth.service.AccountService;
import com.project.hrm.employee.service.EmployeeService;
import com.project.hrm.common.utils.IdGenerator;
import com.project.hrm.auth.util.JwtTokenUtil;
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
    private final LoginAttemptService loginAttemptService;
    private final OtpService otpService;
    private final MailService mailService;

    public AccountServiceImpl(PasswordEncoder passwordEncoder,
                              JwtTokenUtil jwtTokenUtil,
                              AccountRepository accountRepository,
                              AccountMapper accountMapper,
                              @Lazy EmployeeService employeeService,
                              LoginAttemptService loginAttemptService,
                              OtpService otpService,
                              MailService mailService) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.employeeService = employeeService;
        this.loginAttemptService = loginAttemptService;
        this.otpService = otpService;
        this.mailService = mailService;
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

//            String ip = RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes
//                    ? ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRemoteAddr()
//                    : "unknown";
//            String key = name + ":" + ip;

            if (loginAttemptService.isBlocked(name)) {
                throw new CustomException(Error.ACCOUNT_LOCKED_TEMPORARILY);
            }

            log.info("Account: {}", account);

            if (!passwordEncoder.matches(formLoginDTO.getPassword(), account.getPassword())) {
                loginAttemptService.loginFailed(name);

                int remaining = loginAttemptService.getRemainingAttempts(name);
                log.warn("Login attempt failed for user: {}, remaining attempts: {}", name, remaining);
                if (remaining <= 0) {
                    throw new CustomException(Error.ACCOUNT_LOCKED_TEMPORARILY);
                } else {
                    throw new CustomException(Error.ACCOUNT_INVALID_PASSWORD);
                }
            }

            loginAttemptService.loginSucceeded(name);

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
     * @return
     */
    @Override
    public Account getAccountAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new CustomException(Error.UNAUTHORIZED);
        }

        Account account = (Account) authentication.getPrincipal();

        log.info("User principal: {}", account);

        return account;
    }

    /**
     * Retrieves the {@link AccountDTO} associated with a given employee ID.
     * @param employeeId the ID of the employee whose account is to be retrieved
     * @return the {@link AccountDTO} of the employee
     */
    @Override
    public AccountDTO getAccountByEmployeeId(Integer employeeId) {
        log.info("Retrieving account for employee ID: {}", employeeId);
        return accountMapper.toDTO(
                accountRepository.getAccountByEmployeeId(employeeId)
                        .orElseThrow(() -> new CustomException(Error.ACCOUNT_NOT_FOUND)
        ));
    }

    /**
     * Initiates the password recovery process for a user identified by their email address.
     *
     * @param email the email address of the user requesting password recovery
     * @return an {@link AccountDTO} containing the user's account information
     */
    @Override
    public ForgotPasswordResponseDTO forgotPassword(String email) {
        log.info("Initiating password reset for email: {}", email);

        // Validate email exists
        Account account = accountRepository.getAccountByEmail(email)
                .orElseThrow(() -> new CustomException(Error.ACCOUNT_NOT_FOUND));

        // Check if OTP already exists (rate limiting)
        if (otpService.otpExists(email)) {
            throw new CustomException(Error.OTP_ALREADY_SENT);
        }
        try {
            // Generate OTP
            String otp = otpService.generateOtp(email);

            // Send OTP email
            mailService.sendOtpEmail(email, otp, otpService.getOtpExpiryMinutes());

            log.info("OTP sent successfully to email: {}", email);

            return ForgotPasswordResponseDTO.builder()
                    .message("OTP has been sent to your email")
                    .email(email)
                    .expiryMinutes(otpService.getOtpExpiryMinutes())
                    .maxAttempts(otpService.getMaxOtpAttempts())
                    .build();

        } catch (Exception e) {
            log.error("Failed to send OTP to email: {}", email, e);
            throw new CustomException(Error.OTP_SEND_FAILED);
        }
    }

    /**
     * Verifies the OTP provided by the user for password recovery.
     * @param otpVerificationDTO the DTO containing the email and OTP to verify
     * @return an {@link OtpVerificationResponseDTO} indicating whether the OTP is valid
     */
    @Override
    public OtpVerificationResponseDTO verifyOtp(OtpVerificationDTO otpVerificationDTO) {
        log.info("Verifying OTP for email: {}", otpVerificationDTO.getEmail());

        if(!otpVerificationDTO.getIsVerify()){
                throw new CustomException(Error.OTP_REQUIRED);
        }

        // Validate email exists
        accountRepository.getAccountByEmail(otpVerificationDTO.getEmail())
                .orElseThrow(() -> new CustomException(Error.ACCOUNT_NOT_FOUND));

        // Validate OTP
        boolean isValid = otpService.validateOtp(otpVerificationDTO.getEmail(), otpVerificationDTO.getOtp());

        return OtpVerificationResponseDTO.builder()
                .message(isValid ? "OTP verified successfully" : "Invalid OTP")
                .email(otpVerificationDTO.getEmail())
                .isValid(isValid)
                .build();
    }

    /**
     *  Resets the password for a user identified by their email address.
     *
     * @param resetPasswordDTO the DTO containing the email, OTP, and new password
     * @return a success message indicating the password has been reset
     */
    @Override
    public String resetPassword(ResetPasswordDTO resetPasswordDTO) {
        log.info("Resetting password for email: {}", resetPasswordDTO.getEmail());

        // Validate email exists
        Account account = accountRepository.getAccountByEmail(resetPasswordDTO.getEmail())
                .orElseThrow(() -> new CustomException(Error.ACCOUNT_NOT_FOUND));

        // Validate password length
        if (resetPasswordDTO.getNewPassword().length() < 5) {
            throw new CustomException(Error.ACCOUNT_PASSWORD_TO_SHORT);
        }

        try {
            // Validate OTP
            // otpService.validateOtp(resetPasswordDTO.getEmail(), resetPasswordDTO.getOtp());

            // Update password
            account.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
            accountRepository.save(account);

            log.info("Password reset successfully for email: {}", resetPasswordDTO.getEmail());

            return "Password has been reset successfully";

        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to reset password for email: {}", resetPasswordDTO.getEmail(), e);
            throw new CustomException(Error.PASSWORD_RESET_FAILED);
        }
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
        return getAccountAuth().getEmployees();
    }

    /**
     * Retrieves the username associated with a given employee ID.
     * @param employeeId the ID of the employee whose username is to be retrieved
     * @return the username of the employee, or null if no account is found
     */
    @Override
    public String getUsernameByEmployeeId(Integer employeeId) {

        return accountRepository.getUserNameByEmployeeId(employeeId);
    }


    private boolean usernameExists(String username) {
        return accountRepository.findByUsername(username).isPresent();
    }

}
