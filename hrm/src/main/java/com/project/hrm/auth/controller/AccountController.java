package com.project.hrm.auth.controller;

import com.project.hrm.auth.dto.*;
import com.project.hrm.common.response.APIResponse;
import com.project.hrm.auth.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@Tag(name = "Account Controller", description = "Manage user accounts and authentication")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/sign-in")
    @Operation(
            summary = "Sign In",
            description = "Authenticate user and return access information",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User login credentials",
                    required = true,
                    content = @Content(schema = @Schema(implementation = FormLoginDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Authentication successful",
                            content = @Content(schema = @Schema(implementation = AuthenticationDTO.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials")
            }
    )
    public ResponseEntity<APIResponse<AuthenticationDTO>> signIn(@RequestBody FormLoginDTO formLoginDTO, HttpServletRequest request) {
        AuthenticationDTO authDTO = accountService.signIn(formLoginDTO);
        return ResponseEntity.ok(new APIResponse<>(
                true,
                "Authentication successful",
                authDTO,
                null,
                request.getRequestURI()));
    }

    @PostMapping
    @Operation(
            summary = "Create Account",
            description = "Creates a new user account in the system",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Account creation information",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AccountCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Account created successfully",
                            content = @Content(schema = @Schema(implementation = AccountDTO.class))
                    )
            }
    )
    public ResponseEntity<APIResponse<AccountDTO>> create(@RequestBody AccountCreateDTO accountCreateDTO, HttpServletRequest request) {
        AccountDTO accountDTO = accountService.create(accountCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(
                        true,
                        "Account created successfully",
                        accountDTO,
                        null,
                        request.getRequestURI()));
    }

    @PostMapping("/forgot-password")
    @Operation(
            summary = "Forgot Password",
            description = "Initiates password reset process by sending OTP to user's email",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User's email for password reset",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ForgotPasswordRequestDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "OTP sent successfully",
                            content = @Content(schema = @Schema(implementation = ForgotPasswordResponseDTO.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Account not found"),
                    @ApiResponse(responseCode = "400", description = "OTP already sent recently")
            }
    )
    public ResponseEntity<APIResponse<ForgotPasswordResponseDTO>> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequestDTO requestDTO,
            HttpServletRequest request) {

        ForgotPasswordResponseDTO response = accountService.forgotPassword(requestDTO.getEmail());

        return ResponseEntity.ok(new APIResponse<>(
                true,
                "OTP sent successfully to your email",
                response,
                null,
                request.getRequestURI()
        ));
    }

    @PostMapping("/verify-otp")
    @Operation(
            summary = "Verify OTP",
            description = "Verifies OTP for password reset",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "OTP verification data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = OtpVerificationDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "OTP verification result",
                            content = @Content(schema = @Schema(implementation = OtpVerificationResponseDTO.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid OTP or expired"),
                    @ApiResponse(responseCode = "404", description = "Account not found")
            }
    )
    public ResponseEntity<APIResponse<OtpVerificationResponseDTO>> verifyOtp(
            @Valid @RequestBody OtpVerificationDTO otpVerificationDTO,
            HttpServletRequest request) {

        OtpVerificationResponseDTO response = accountService.verifyOtp(otpVerificationDTO);

        return ResponseEntity.ok(new APIResponse<>(
                true,
                response.getMessage(),
                response,
                null,
                request.getRequestURI()
        ));
    }

    @PostMapping("/reset-password")
    @Operation(
            summary = "Reset Password",
            description = "Resets user password using OTP verification",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Password reset data with OTP",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ResetPasswordDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Password reset successfully",
                            content = @Content(schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid OTP or password"),
                    @ApiResponse(responseCode = "404", description = "Account not found")
            }
    )
    public ResponseEntity<APIResponse<String>> resetPassword(
            @Valid @RequestBody ResetPasswordDTO resetPasswordDTO,
            HttpServletRequest request) {

        String message = accountService.resetPassword(resetPasswordDTO);

        return ResponseEntity.ok(new APIResponse<>(
                true,
                message,
                message,
                null,
                request.getRequestURI()
        ));
    }

    @PostMapping("/refresh-token")
    @Operation(
            summary = "Refresh Token",
            description = "Refreshes the authentication token",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Refresh token data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RefreshTokenDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Token refreshed successfully",
                            content = @Content(schema = @Schema(implementation = AuthenticationDTO.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Invalid refresh token")
            }
    )
    public ResponseEntity<APIResponse<AuthenticationDTO>> refreshToken(
            @Valid @RequestBody RefreshTokenDTO refreshTokenDTO,
            HttpServletRequest request) {

        AuthenticationDTO authDTO = accountService.refreshToken(refreshTokenDTO);

        return ResponseEntity.ok(new APIResponse<>(
                true,
                "Token refreshed successfully",
                authDTO,
                null,
                request.getRequestURI()
        ));
    }

}
