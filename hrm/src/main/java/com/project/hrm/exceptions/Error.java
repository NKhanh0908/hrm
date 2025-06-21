package com.project.hrm.exceptions;

import org.springframework.http.HttpStatusCode;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Error {
    // Client Error
    NOT_FOUND(404, "Resource not found", HttpStatus.NOT_FOUND),
    BAD_REQUEST(400, "Bad request", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(401, "Unauthorized", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(403, "Forbidden", HttpStatus.FORBIDDEN),
    CONFLICT(409, "Conflict", HttpStatus.CONFLICT),

    // Server Error
    UNCATEGORIZED_EXCEPTION(9999, "Unclassified error", HttpStatus.INTERNAL_SERVER_ERROR),

    // Database Error
    DATABASE_ACCESS_ERROR(9998, "Database access error", HttpStatus.INTERNAL_SERVER_ERROR),
    DUPLICATE_KEY(9996, "Duplicate key found", HttpStatus.CONFLICT),
    EMPTY_RESULT(9995, "No result found", HttpStatus.NOT_FOUND),
    NON_UNIQUE_RESULT(9994, "Non-unique result found", HttpStatus.CONFLICT),

    // Account-related errors
    ACCOUNT_NOT_FOUND(1001, "Account not found", HttpStatus.NOT_FOUND),
    ACCOUNT_ALREADY_EXISTS(1002, "Account already exists", HttpStatus.CONFLICT),
    ACCOUNT_UNABLE_TO_SAVE(1003, "Unable to save account", HttpStatus.INTERNAL_SERVER_ERROR),
    ACCOUNT_UNABLE_TO_UPDATE(1004, "Unable to update account", HttpStatus.INTERNAL_SERVER_ERROR),
    ACCOUNT_UNABLE_TO_DELETE(1005, "Unable to delete account", HttpStatus.INTERNAL_SERVER_ERROR),
    ACCOUNT_INVALID_USERNAME(1006, "Invalid username", HttpStatus.BAD_REQUEST),
    ACCOUNT_INVALID_PASSWORD(1007, "Invalid password", HttpStatus.BAD_REQUEST),
    ACCOUNT_LOCKED(1008, "Account is locked", HttpStatus.FORBIDDEN),
    ACCOUNT_USERNAME_TO_SHORT(1008, "Username account to short", HttpStatus.BAD_REQUEST),
    ACCOUNT_USERNAME_TO_LONG(1008, "Username account to long", HttpStatus.BAD_REQUEST),
    ACCOUNT_PASSWORD_TO_SHORT(1008, "Password account to short", HttpStatus.BAD_REQUEST),

    // Employee-related errors
    EMPLOYEE_NOT_FOUND(2001, "Employee not found", HttpStatus.NOT_FOUND),
    EMPLOYEE_ALREADY_EXISTS(2002, "Employee already exists", HttpStatus.CONFLICT),
    EMPLOYEE_UNABLE_TO_SAVE(2003, "Unable to save employee", HttpStatus.INTERNAL_SERVER_ERROR),
    EMPLOYEE_UNABLE_TO_UPDATE(2004, "Unable to update employee", HttpStatus.INTERNAL_SERVER_ERROR),
    EMPLOYEE_UNABLE_TO_DELETE(2005, "Unable to delete employee", HttpStatus.INTERNAL_SERVER_ERROR),
    EMPLOYEE_INVALID_EMAIL(2006, "Invalid email format", HttpStatus.BAD_REQUEST),
    EMPLOYEE_INVALID_PHONE(2007, "Invalid phone number", HttpStatus.BAD_REQUEST),
    EMPLOYEE_INVALID_CITIZEN_ID(2008, "Invalid citizen identification card", HttpStatus.BAD_REQUEST),
    EMPLOYEE_INACTIVE(2009, "Employee is inactive", HttpStatus.BAD_REQUEST),

    // Department-related errors
    DEPARTMENT_NOT_FOUND(3001, "Department not found", HttpStatus.NOT_FOUND),
    DEPARTMENT_ALREADY_EXISTS(3002, "Department already exists", HttpStatus.CONFLICT),
    DEPARTMENT_UNABLE_TO_SAVE(3003, "Unable to save department", HttpStatus.INTERNAL_SERVER_ERROR),
    DEPARTMENT_UNABLE_TO_UPDATE(3004, "Unable to update department", HttpStatus.INTERNAL_SERVER_ERROR),
    DEPARTMENT_UNABLE_TO_DELETE(3005, "Unable to delete department", HttpStatus.INTERNAL_SERVER_ERROR),
    DEPARTMENT_INVALID_NAME(3006, "Invalid department name", HttpStatus.BAD_REQUEST),
    DEPARTMENT_INVALID_EMAIL(3007, "Invalid department email", HttpStatus.BAD_REQUEST),
    DEPARTMENT_INVALID_PHONE(3008, "Invalid department phone", HttpStatus.BAD_REQUEST),

    // Role-related errors
    ROLE_NOT_FOUND(4001, "Role not found", HttpStatus.NOT_FOUND),
    ROLE_ALREADY_EXISTS(4002, "Role already exists", HttpStatus.CONFLICT),
    ROLE_UNABLE_TO_SAVE(4003, "Unable to save role", HttpStatus.INTERNAL_SERVER_ERROR),
    ROLE_UNABLE_TO_UPDATE(4004, "Unable to update role", HttpStatus.INTERNAL_SERVER_ERROR),
    ROLE_UNABLE_TO_DELETE(4005, "Unable to delete role", HttpStatus.INTERNAL_SERVER_ERROR),
    ROLE_INVALID_NAME(4006, "Invalid role name", HttpStatus.BAD_REQUEST),

    // Contract-related errors
    CONTRACT_NOT_FOUND(5001, "Contract not found", HttpStatus.NOT_FOUND),
    CONTRACT_ALREADY_EXISTS(5002, "An active contract of this role already exists for employee", HttpStatus.CONFLICT),
    CONTRACT_UNABLE_TO_SAVE(5003, "Unable to save contract", HttpStatus.INTERNAL_SERVER_ERROR),
    CONTRACT_UNABLE_TO_UPDATE(5004, "Unable to update contract", HttpStatus.INTERNAL_SERVER_ERROR),
    CONTRACT_UNABLE_TO_DELETE(5005, "Unable to delete contract", HttpStatus.INTERNAL_SERVER_ERROR),
    CONTRACT_INVALID_DATE(5006, "Invalid contract date", HttpStatus.BAD_REQUEST),
    CONTRACT_INVALID_SALARY(5007, "Invalid base salary", HttpStatus.BAD_REQUEST),
    CONTRACT_EXPIRED(5008, "Contract has expired", HttpStatus.BAD_REQUEST),
    CONTRACT_INVALID_STATUS(5009, "Invalid contract status", HttpStatus.BAD_REQUEST),

    // Recruitment-related errors
    RECRUITMENT_NOT_FOUND(6001, "Recruitment not found", HttpStatus.NOT_FOUND),
    RECRUITMENT_ALREADY_EXISTS(6002, "Recruitment already exists", HttpStatus.CONFLICT),
    RECRUITMENT_UNABLE_TO_SAVE(6003, "Unable to save recruitment", HttpStatus.INTERNAL_SERVER_ERROR),
    RECRUITMENT_UNABLE_TO_UPDATE(6004, "Unable to update recruitment", HttpStatus.INTERNAL_SERVER_ERROR),
    RECRUITMENT_UNABLE_TO_DELETE(6005, "Unable to delete recruitment", HttpStatus.INTERNAL_SERVER_ERROR),
    RECRUITMENT_INVALID_DEADLINE(6006, "Invalid recruitment deadline", HttpStatus.BAD_REQUEST),
    RECRUITMENT_INVALID_EMAIL(6007, "Invalid recruitment email", HttpStatus.BAD_REQUEST),
    RECRUITMENT_INVALID_PHONE(6008, "Invalid recruitment phone", HttpStatus.BAD_REQUEST),
    RECRUITMENT_DEADLINE_PASSED(6009, "Recruitment deadline has passed", HttpStatus.BAD_REQUEST),

    // Recruitment Requirements-related errors
    RECRUITMENT_REQUIREMENTS_NOT_FOUND(7001, "Recruitment requirements not found", HttpStatus.NOT_FOUND),
    RECRUITMENT_REQUIREMENTS_ALREADY_EXISTS(7002, "Recruitment requirements already exists", HttpStatus.CONFLICT),
    RECRUITMENT_REQUIREMENTS_UNABLE_TO_SAVE(7003, "Unable to save recruitment requirements", HttpStatus.INTERNAL_SERVER_ERROR),
    RECRUITMENT_REQUIREMENTS_UNABLE_TO_UPDATE(7004, "Unable to update recruitment requirements", HttpStatus.INTERNAL_SERVER_ERROR),
    RECRUITMENT_REQUIREMENTS_UNABLE_TO_DELETE(7005, "Unable to delete recruitment requirements", HttpStatus.INTERNAL_SERVER_ERROR),
    RECRUITMENT_REQUIREMENTS_INVALID_QUANTITY(7006, "Invalid recruitment quantity", HttpStatus.BAD_REQUEST),
    RECRUITMENT_REQUIREMENTS_INVALID_SALARY(7007, "Invalid expected salary", HttpStatus.BAD_REQUEST),
    RECRUITMENT_REQUIREMENTS_UNABLE_TO_UPDATE_STATUS(7008, "Unable to update recruitment requirements status", HttpStatus.INTERNAL_SERVER_ERROR),

    // Candidate Profile-related errors
    CANDIDATE_PROFILE_NOT_FOUND(8001, "Candidate profile not found", HttpStatus.NOT_FOUND),
    CANDIDATE_PROFILE_ALREADY_EXISTS(8002, "Candidate profile already exists", HttpStatus.CONFLICT),
    CANDIDATE_PROFILE_UNABLE_TO_SAVE(8003, "Unable to save candidate profile", HttpStatus.INTERNAL_SERVER_ERROR),
    CANDIDATE_PROFILE_UNABLE_TO_UPDATE(8004, "Unable to update candidate profile", HttpStatus.INTERNAL_SERVER_ERROR),
    CANDIDATE_PROFILE_UNABLE_TO_DELETE(8005, "Unable to delete candidate profile", HttpStatus.INTERNAL_SERVER_ERROR),
    CANDIDATE_PROFILE_INVALID_EMAIL(8006, "Invalid candidate email", HttpStatus.BAD_REQUEST),
    CANDIDATE_PROFILE_INVALID_PHONE(8007, "Invalid candidate phone", HttpStatus.BAD_REQUEST),
    CANDIDATE_PROFILE_INVALID_CV(8008, "Invalid CV link", HttpStatus.BAD_REQUEST),

    // Apply-related errors
    APPLY_NOT_FOUND(9001, "Application not found", HttpStatus.NOT_FOUND),
    APPLY_ALREADY_EXISTS(9002, "Application already exists", HttpStatus.CONFLICT),
    APPLY_UNABLE_TO_SAVE(9003, "Unable to save application", HttpStatus.INTERNAL_SERVER_ERROR),
    APPLY_UNABLE_TO_UPDATE(9004, "Unable to update application", HttpStatus.INTERNAL_SERVER_ERROR),
    APPLY_UNABLE_TO_DELETE(9005, "Unable to delete application", HttpStatus.INTERNAL_SERVER_ERROR),
    APPLY_INVALID_STATUS(9006, "Invalid application status", HttpStatus.BAD_REQUEST),
    APPLY_ALREADY_SUBMITTED(9007, "Application already submitted for this position", HttpStatus.CONFLICT),
    APPLY_NOT_OPEN(9008, "Cannot apply recruitment is not open", HttpStatus.BAD_REQUEST),

    // Evaluate-related errors
    EVALUATE_NOT_FOUND(10001, "Evaluation not found", HttpStatus.NOT_FOUND),
    EVALUATE_ALREADY_EXISTS(10002, "Evaluation already exists", HttpStatus.CONFLICT),
    EVALUATE_UNABLE_TO_SAVE(10003, "Unable to save evaluation", HttpStatus.INTERNAL_SERVER_ERROR),
    EVALUATE_UNABLE_TO_UPDATE(10004, "Unable to update evaluation", HttpStatus.INTERNAL_SERVER_ERROR),
    EVALUATE_UNABLE_TO_DELETE(10005, "Unable to delete evaluation", HttpStatus.INTERNAL_SERVER_ERROR),
    EVALUATE_INVALID_FEEDBACK(10006, "Invalid evaluation feedback", HttpStatus.BAD_REQUEST),

    // Salary-related errors
    SALARY_NOT_FOUND(11001, "Salary record not found", HttpStatus.NOT_FOUND),
    SALARY_ALREADY_EXISTS(11002, "Salary record already exists", HttpStatus.CONFLICT),
    SALARY_UNABLE_TO_SAVE(11003, "Unable to save salary record", HttpStatus.INTERNAL_SERVER_ERROR),
    SALARY_UNABLE_TO_UPDATE(11004, "Unable to update salary record", HttpStatus.INTERNAL_SERVER_ERROR),
    SALARY_UNABLE_TO_DELETE(11005, "Unable to delete salary record", HttpStatus.INTERNAL_SERVER_ERROR),
    SALARY_INVALID_AMOUNT(11006, "Invalid salary amount", HttpStatus.BAD_REQUEST),

    // Deduction-related errors
    DEDUCTION_NOT_FOUND(12001, "Deduction not found", HttpStatus.NOT_FOUND),
    DEDUCTION_ALREADY_EXISTS(12002, "Deduction already exists", HttpStatus.CONFLICT),
    DEDUCTION_UNABLE_TO_SAVE(12003, "Unable to save deduction", HttpStatus.INTERNAL_SERVER_ERROR),
    DEDUCTION_UNABLE_TO_UPDATE(12004, "Unable to update deduction", HttpStatus.INTERNAL_SERVER_ERROR),
    DEDUCTION_UNABLE_TO_DELETE(12005, "Unable to delete deduction", HttpStatus.INTERNAL_SERVER_ERROR),
    DEDUCTION_INVALID_AMOUNT(12006, "Invalid deduction amount", HttpStatus.BAD_REQUEST),
    DEDUCTION_INVALID_TYPE(12007, "Invalid deduction type", HttpStatus.BAD_REQUEST),

    // Subsidy-related errors
    SUBSIDY_NOT_FOUND(13001, "Subsidy not found", HttpStatus.NOT_FOUND),
    SUBSIDY_ALREADY_EXISTS(13002, "Subsidy already exists", HttpStatus.CONFLICT),
    SUBSIDY_UNABLE_TO_SAVE(13003, "Unable to save subsidy", HttpStatus.INTERNAL_SERVER_ERROR),
    SUBSIDY_UNABLE_TO_UPDATE(13004, "Unable to update subsidy", HttpStatus.INTERNAL_SERVER_ERROR),
    SUBSIDY_UNABLE_TO_DELETE(13005, "Unable to delete subsidy", HttpStatus.INTERNAL_SERVER_ERROR),
    SUBSIDY_INVALID_AMOUNT(13006, "Invalid subsidy amount", HttpStatus.BAD_REQUEST),
    SUBSIDY_INVALID_TYPE(13007, "Invalid subsidy type", HttpStatus.BAD_REQUEST),

    // JWT token-related errors
    JWT_INVALID(14001, "Invalid JWT token", HttpStatus.UNAUTHORIZED),
    JWT_EXPIRED(14002, "JWT token expired", HttpStatus.UNAUTHORIZED),
    JWT_MALFORMED(14003, "Malformed JWT token", HttpStatus.UNAUTHORIZED),

    // File upload-related errors
    FILE_UPLOAD_FAILED(15001, "File upload failed", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_INVALID_FORMAT(15002, "Invalid file format", HttpStatus.BAD_REQUEST),
    FILE_TOO_LARGE(15003, "File size too large", HttpStatus.BAD_REQUEST),
    FILE_NOT_FOUND(15004, "File not found", HttpStatus.NOT_FOUND),
    FILE_SIZE_EXCEEDED(15005, "File size to less than 5MB", HttpStatus.BAD_REQUEST),
    INVALID_FILE_TYPE(15006, "File type invalid", HttpStatus.BAD_REQUEST),

    // Training Program
    TRAINING_PROGRAM_NOT_FOUND(16001, "Training program not found", HttpStatus.NOT_FOUND),
    TRAINING_PROGRAM_ALREADY_EXISTS(16002, "Training program already exists", HttpStatus.CONFLICT),
    TRAINING_PROGRAM_UNABLE_TO_SAVE(16003, "Unable to save Training program", HttpStatus.INTERNAL_SERVER_ERROR),
    TRAINING_PROGRAM_UNABLE_TO_UPDATE(16004, "Unable to update Training program", HttpStatus.INTERNAL_SERVER_ERROR),
    TRAINING_PROGRAM_UNABLE_TO_DELETE(16005, "Unable to delete Training program", HttpStatus.INTERNAL_SERVER_ERROR),

    // Training Program
    TRAINING_SESSION_NOT_FOUND(17001, "Training session not found", HttpStatus.NOT_FOUND),
    TRAINING_SESSION_ALREADY_EXISTS(17002, "Training session already exists", HttpStatus.CONFLICT),
    TRAINING_SESSION_UNABLE_TO_SAVE(17003, "Unable to save Training session", HttpStatus.INTERNAL_SERVER_ERROR),
    TRAINING_SESSION_UNABLE_TO_UPDATE(17004, "Unable to update Training session", HttpStatus.INTERNAL_SERVER_ERROR),
    TRAINING_SESSION_UNABLE_TO_DELETE(17005, "Unable to delete Training session", HttpStatus.INTERNAL_SERVER_ERROR),

    //Training enrollment
    TRAINING_ENROLLMENT_NOT_FOUND(18001, "Training enrollment not found", HttpStatus.NOT_FOUND),
    TRAINING_ENROLLMENT_ALREADY_EXISTS(18002, "Training enrollment already exists", HttpStatus.CONFLICT),
    TRAINING_ENROLLMENT_UNABLE_TO_SAVE(18003, "Unable to save Training enrollment", HttpStatus.INTERNAL_SERVER_ERROR),
    TRAINING_ENROLLMENT_UNABLE_TO_UPDATE(18004, "Unable to update Training enrollment", HttpStatus.INTERNAL_SERVER_ERROR),
    TRAINING_ENROLLMENT_UNABLE_TO_DELETE(18005, "Unable to delete Training enrollment", HttpStatus.INTERNAL_SERVER_ERROR),

    // Training Request
    TRAINING_REQUEST_NOT_FOUND(19001, "Training Request not found", HttpStatus.NOT_FOUND),
    TRAINING_REQUEST_ALREADY_EXISTS(19002, "Training Request already exists", HttpStatus.CONFLICT),
    TRAINING_REQUEST_UNABLE_TO_SAVE(19003, "Unable to save Training Request", HttpStatus.INTERNAL_SERVER_ERROR),
    TRAINING_REQUEST_UNABLE_TO_UPDATE(19004, "Unable to update Training Request", HttpStatus.INTERNAL_SERVER_ERROR),
    TRAINING_REQUEST_UNABLE_TO_DELETE(19005, "Unable to delete Training Request", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    /**
     * Constructor for ErrorCode.
     *
     * @param code       the error code
     * @param message    the error message
     * @param statusCode the corresponding HTTP status code
     */
    Error(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}