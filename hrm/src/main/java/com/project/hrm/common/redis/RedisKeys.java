package com.project.hrm.common.redis;

public class RedisKeys {
    private RedisKeys() {}

    // Training Program Keys
    public static final String TRAINING_PROGRAM_PREFIX = "training:program:";
    public static final String TRAINING_PROGRAMS_LIST = "training:programs:list";

    // Training Session Keys
    public static final String TRAINING_SESSIONS_LIST = "training:sessions:list";

    // Recruitment Keys
    public static final String RECRUITMENT_PREFIX = "recruitment:";
    public static final String RECRUITMENT_LIST = "recruitment:list:";

    // Employee Keys
    public static final String EMPLOYEE_PREFIX = "employee:";
    public static final String EMPLOYEES_LIST = "employees:list";

    // OTP Keys
    public static final String OTP_PREFIX = "otp:";
    public static final String OTP_ATTEMPT_PREFIX = "otp_attempt:";

    public static String trainingProgramKey(Integer programId) {
        return TRAINING_PROGRAM_PREFIX + programId;
    }

    public static String recruitmentKey(Integer recruitmentId) {
        return RECRUITMENT_PREFIX + recruitmentId;
    }

    public static String employeeKey(Integer employeeId) {
        return EMPLOYEE_PREFIX + employeeId;
    }

    public static String otpKey(String email) {
        return OTP_PREFIX + email;
    }

    public static String otpAttemptKey(String email) {
        return OTP_ATTEMPT_PREFIX + email;
    }

}
