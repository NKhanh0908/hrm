package com.project.hrm.auth.util;

import com.project.hrm.common.exceptions.CustomException;
import com.project.hrm.common.redis.RedisKeys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;

import com.project.hrm.common.exceptions.Error;

@Service
@Slf4j
@RequiredArgsConstructor
public class OtpService {
    private final RedisTemplate<String, String> redisTemplate;

    private static final int OTP_LENGTH = 6;
    private static final int OTP_EXPIRY_MINUTES = 5;
    private static final int MAX_OTP_ATTEMPTS = 5;
    private static final int OTP_ATTEMPT_EXPIRY_MINUTES = 15;

    public String generateOtp(String email) {
        String otp = generateRandomOtp();
        String otpKey = RedisKeys.OTP_PREFIX + email;
        String attemptKey = RedisKeys.OTP_ATTEMPT_PREFIX + email;

        // Store OTP with expiry
        redisTemplate.opsForValue().set(otpKey, otp, Duration.ofMinutes(OTP_EXPIRY_MINUTES));

        // Reset attempt counter
        redisTemplate.delete(attemptKey);

        log.info("OTP generated for email: {}", email);
        return otp;
    }

    public boolean validateOtp(String email, String inputOtp) {
        String otpKey = RedisKeys.OTP_PREFIX + email;
        String attemptKey = RedisKeys.OTP_ATTEMPT_PREFIX + email;

        // Check if OTP exists and not expired
        String storedOtp = redisTemplate.opsForValue().get(otpKey);
        if (storedOtp == null) {
            throw new CustomException(Error.OTP_EXPIRED_OR_INVALID);
        }

        // Check attempt count
        String attemptCountStr = redisTemplate.opsForValue().get(attemptKey);
        int attemptCount = attemptCountStr != null ? Integer.parseInt(attemptCountStr) : 0;

        if (attemptCount >= MAX_OTP_ATTEMPTS) {
            // Remove OTP to prevent further attempts
            redisTemplate.delete(otpKey);
            throw new CustomException(Error.OTP_MAX_ATTEMPTS_EXCEEDED);
        }

        // Increment attempt count
        redisTemplate.opsForValue().set(attemptKey, String.valueOf(attemptCount + 1),
                Duration.ofMinutes(OTP_ATTEMPT_EXPIRY_MINUTES));

        // Validate OTP
        if (!storedOtp.equals(inputOtp)) {
            int remainingAttempts = MAX_OTP_ATTEMPTS - attemptCount - 1;
            log.warn("Invalid OTP for email: {}, remaining attempts: {}", email, remainingAttempts);
            throw new CustomException(Error.OTP_INVALID);
        }

        // OTP is valid, clean up
        redisTemplate.delete(otpKey);
        redisTemplate.delete(attemptKey);

        log.info("OTP validated successfully for email: {}", email);
        return true;
    }

    public boolean otpExists(String email) {
        String otpKey = RedisKeys.OTP_PREFIX + email;
        return Boolean.TRUE.equals(redisTemplate.hasKey(otpKey));
    }

    public int getRemainingAttempts(String email) {
        String attemptKey = RedisKeys.OTP_ATTEMPT_PREFIX + email;
        String attemptCountStr = redisTemplate.opsForValue().get(attemptKey);
        int attemptCount = attemptCountStr != null ? Integer.parseInt(attemptCountStr) : 0;
        return Math.max(0, MAX_OTP_ATTEMPTS - attemptCount);
    }

    public void removeOtp(String email) {
        String otpKey = RedisKeys.OTP_PREFIX + email;
        String attemptKey = RedisKeys.OTP_ATTEMPT_PREFIX + email;
        redisTemplate.delete(otpKey);
        redisTemplate.delete(attemptKey);
        log.info("OTP removed for email: {}", email);
    }


    private String generateRandomOtp() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }

    public int getOtpExpiryMinutes() {
        return OTP_EXPIRY_MINUTES;
    }

    public int getMaxOtpAttempts() {
        return MAX_OTP_ATTEMPTS;
    }
}
