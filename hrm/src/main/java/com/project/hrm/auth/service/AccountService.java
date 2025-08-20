package com.project.hrm.auth.service;

import com.project.hrm.auth.dto.*;
import com.project.hrm.auth.entity.Account;
import com.project.hrm.employee.entity.Employees;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    AuthenticationDTO signIn(FormLoginDTO formLoginDTO);

    AccountDTO create(AccountCreateDTO accountCreateDTO);

    Account getAccountAuth();

    AccountDTO getAccountByEmployeeId(Integer employeeId);

    AuthenticationDTO refreshToken(RefreshTokenDTO refreshTokenDTO);

    Employees getPrincipal();

    String getUsernameByEmployeeId(Integer employeeId);

    ForgotPasswordResponseDTO forgotPassword(String email);

    OtpVerificationResponseDTO verifyOtp(OtpVerificationDTO otpVerificationDTO);

    String resetPassword(ResetPasswordDTO resetPasswordDTO);
}
