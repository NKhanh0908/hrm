package com.project.hrm.common.exceptions;

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
    METHOD_NOT_ALLOWED(405, "Method not allowed", HttpStatus.METHOD_NOT_ALLOWED),
    TOO_MANY_REQUESTS(429, "Too many requests", HttpStatus.TOO_MANY_REQUESTS),

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
    ACCOUNT_LOCKED_TEMPORARILY(1009, "Account is temporarily locked due to too many failed login attempts", HttpStatus.FORBIDDEN),

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
    INVALID_CONTRACT_PERIOD(5010, "Invalid contract period", HttpStatus.BAD_REQUEST),
    SIGNING_DATE_AFTER_START_DATE(5011, "Invalid contract period", HttpStatus.BAD_REQUEST),

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
    RECRUITMENT_REQUIREMENTS_UNABLE_TO_UPDATE(7004, "Unable to update recruitment requirements by status not equals PENDING", HttpStatus.INTERNAL_SERVER_ERROR),
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
    EMPLOYEE_HAS_ACTIVE_CONTRACT_DIFFERENT_ROLE(8009,"Employee already has active contract with different role", HttpStatus.BAD_REQUEST),
    CONTRACT_PERIOD_CONFLICT(8010,"Contract period conflicts with existing contract", HttpStatus.BAD_REQUEST),
    INVALID_STATUS_TRANSITION(8011,"Invalid contract status transition", HttpStatus.BAD_REQUEST),
    START_DATE_TOO_OLD(8012,"Contract start date is too old", HttpStatus.BAD_REQUEST),
    CONTRACT_PERIOD_TOO_SHORT(8014,"Contract period is too short (minimum 30 days required", HttpStatus.BAD_REQUEST),

    // Apply-related errors
    APPLY_NOT_FOUND(9001, "Application not found", HttpStatus.NOT_FOUND),
    APPLY_ALREADY_EXISTS(9002, "Application already exists", HttpStatus.CONFLICT),
    APPLY_UNABLE_TO_SAVE(9003, "Unable to save application", HttpStatus.INTERNAL_SERVER_ERROR),
    APPLY_UNABLE_TO_UPDATE(9004, "Unable to update application", HttpStatus.INTERNAL_SERVER_ERROR),
    APPLY_UNABLE_TO_DELETE(9005, "Unable to delete application", HttpStatus.INTERNAL_SERVER_ERROR),
    APPLY_INVALID_STATUS(9006, "Invalid application status", HttpStatus.BAD_REQUEST),
    APPLY_ALREADY_SUBMITTED(9007, "Application already submitted for this position", HttpStatus.CONFLICT),
    APPLY_NOT_OPEN(9008, "Cannot apply recruitment is not open", HttpStatus.BAD_REQUEST),
    APPLY_EXPIRED(9008, "Cannot apply recruitment is EXPIRED", HttpStatus.BAD_REQUEST),

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
    TRAINING_REQUEST_UNABLE_TO_DELETE(19005, "Unable to delete Training Request", HttpStatus.INTERNAL_SERVER_ERROR),

    // Performance review
    PERFORMANCE_REVIEW_NOT_FOUND(20001, "Performance review not found", HttpStatus.NOT_FOUND),
    PERFORMANCE_REVIEW_UNABLE_TO_SAVE(20002, "Unable to save Performance review", HttpStatus.INTERNAL_SERVER_ERROR),
    PERFORMANCE_REVIEW_UNABLE_TO_UPDATE(20003, "Must update Performance review with status SCHEDULED", HttpStatus.INTERNAL_SERVER_ERROR),

    // Performance review detail
    PERFORMANCE_REVIEW_DETAIL_NOT_FOUND(21001, "Performance review detail not found", HttpStatus.NOT_FOUND),
    PERFORMANCE_REVIEW_DETAIL_UNABLE_TO_SAVE(21002, "Unable to save Performance review detail", HttpStatus.INTERNAL_SERVER_ERROR),
    PERFORMANCE_REVIEW_DETAIL_UNABLE_TO_UPDATE(21003, "Unable to update Performance review detail", HttpStatus.INTERNAL_SERVER_ERROR),

    // Feedback employee
    FEEDBACK_NOT_FOUND(22001, "Feedback employee not found", HttpStatus.NOT_FOUND),
    FEEDBACK_UNABLE_TO_SAVE(22002, "Unable to save Feedback employee", HttpStatus.INTERNAL_SERVER_ERROR),
    FEEDBACK_UNABLE_TO_UPDATE(22003, "Unable to update Feedback employee", HttpStatus.INTERNAL_SERVER_ERROR),

    // AssignedWorkPerson
    ASSIGNED_WORK_PERSON_NOT_FOUND(23001, "AssignedWorkPerson not found", HttpStatus.NOT_FOUND),
    ASSIGNED_WORK_PERSON_UNABLE_TO_SAVE(23002, "Unable to save AssignedWorkPerson", HttpStatus.INTERNAL_SERVER_ERROR),
    ASSIGNED_WORK_PERSON_UNABLE_TO_UPDATE(23003, "Unable to update AssignedWorkPerson", HttpStatus.INTERNAL_SERVER_ERROR),

    // Attendance-related errors
    ATTENDANCE_NOT_FOUND(24001, "Attendance record not found", HttpStatus.NOT_FOUND),
    ATTENDANCE_ALREADY_EXISTS(24002, "Attendance record already exists for this date", HttpStatus.CONFLICT),
    ATTENDANCE_UNABLE_TO_SAVE(24003, "Unable to save attendance record", HttpStatus.INTERNAL_SERVER_ERROR),
    ATTENDANCE_UNABLE_TO_UPDATE(24004, "Unable to update attendance record", HttpStatus.INTERNAL_SERVER_ERROR),
    ATTENDANCE_UNABLE_TO_DELETE(24005, "Unable to delete attendance record", HttpStatus.INTERNAL_SERVER_ERROR),
    ATTENDANCE_INVALID_TIME(24006, "Invalid check-in or check-out time", HttpStatus.BAD_REQUEST),
    ATTENDANCE_CHECKIN_AFTER_CHECKOUT(24007, "Check-in time cannot be after check-out time", HttpStatus.BAD_REQUEST),
    ATTENDANCE_ALREADY_CHECKED_IN(24008, "Employee already checked in for today", HttpStatus.CONFLICT),
    ATTENDANCE_NOT_CHECKED_IN(24009, "Employee has not checked in yet", HttpStatus.BAD_REQUEST),

    // DayOff-related errors
    DAY_OFF_NOT_FOUND(25001, "Day off request not found", HttpStatus.NOT_FOUND),
    DAY_OFF_ALREADY_EXISTS(25002, "Day off request already exists for this period", HttpStatus.CONFLICT),
    DAY_OFF_UNABLE_TO_SAVE(25003, "Unable to save day off request", HttpStatus.INTERNAL_SERVER_ERROR),
    DAY_OFF_UNABLE_TO_UPDATE(25004, "Unable to update day off request", HttpStatus.INTERNAL_SERVER_ERROR),
    DAY_OFF_UNABLE_TO_DELETE(25005, "Unable to delete day off request", HttpStatus.INTERNAL_SERVER_ERROR),
    DAY_OFF_INVALID_DATE_RANGE(25006, "Invalid day off date range", HttpStatus.BAD_REQUEST),
    DAY_OFF_START_DATE_AFTER_END_DATE(25007, "Start date cannot be after end date", HttpStatus.BAD_REQUEST),
    DAY_OFF_PAST_DATE(25008, "Cannot request day off for past dates", HttpStatus.BAD_REQUEST),
    DAY_OFF_INSUFFICIENT_BALANCE(25009, "Insufficient leave balance", HttpStatus.BAD_REQUEST),

    // Dependent-related errors
    DEPENDENT_NOT_FOUND(26001, "Dependent not found", HttpStatus.NOT_FOUND),
    DEPENDENT_ALREADY_EXISTS(26002, "Dependent already exists", HttpStatus.CONFLICT),
    DEPENDENT_UNABLE_TO_SAVE(26003, "Unable to save dependent", HttpStatus.INTERNAL_SERVER_ERROR),
    DEPENDENT_UNABLE_TO_UPDATE(26004, "Unable to update dependent", HttpStatus.INTERNAL_SERVER_ERROR),
    DEPENDENT_UNABLE_TO_DELETE(26005, "Unable to delete dependent", HttpStatus.INTERNAL_SERVER_ERROR),
    DEPENDENT_INVALID_RELATIONSHIP(26006, "Invalid dependent relationship", HttpStatus.BAD_REQUEST),
    DEPENDENT_INVALID_BIRTH_DATE(26007, "Invalid dependent birth date", HttpStatus.BAD_REQUEST),
    DEPENDENT_AGE_LIMIT_EXCEEDED(26008, "Dependent age limit exceeded", HttpStatus.BAD_REQUEST),

    // PayPeriods-related errors
    PAY_PERIOD_NOT_FOUND(27001, "Pay period not found", HttpStatus.NOT_FOUND),
    PAY_PERIOD_ALREADY_EXISTS(27002, "Pay period already exists for this period", HttpStatus.CONFLICT),
    PAY_PERIOD_UNABLE_TO_SAVE(27003, "Unable to save pay period", HttpStatus.INTERNAL_SERVER_ERROR),
    PAY_PERIOD_UNABLE_TO_UPDATE(27004, "Unable to update pay period", HttpStatus.INTERNAL_SERVER_ERROR),
    PAY_PERIOD_UNABLE_TO_DELETE(27005, "Unable to delete pay period", HttpStatus.INTERNAL_SERVER_ERROR),
    PAY_PERIOD_INVALID_DATE_RANGE(27006, "Invalid pay period date range", HttpStatus.BAD_REQUEST),
    PAY_PERIOD_OVERLAPPING(27007, "Pay period overlaps with existing period", HttpStatus.CONFLICT),
    PAY_PERIOD_ALREADY_CLOSED(27008, "Pay period is already closed", HttpStatus.BAD_REQUEST),
    PAY_PERIOD_CANNOT_DELETE_WITH_PAYROLLS(27009, "Cannot delete pay period with existing payrolls", HttpStatus.CONFLICT),

    // Payrolls-related errors
    PAYROLL_NOT_FOUND(28001, "Payroll not found", HttpStatus.NOT_FOUND),
    PAYROLL_ALREADY_EXISTS(28002, "Payroll already exists for this employee and period", HttpStatus.CONFLICT),
    PAYROLL_UNABLE_TO_SAVE(28003, "Unable to save payroll", HttpStatus.INTERNAL_SERVER_ERROR),
    PAYROLL_UNABLE_TO_UPDATE(28004, "Unable to update payroll", HttpStatus.INTERNAL_SERVER_ERROR),
    PAYROLL_UNABLE_TO_DELETE(28005, "Unable to delete payroll", HttpStatus.INTERNAL_SERVER_ERROR),
    PAYROLL_ALREADY_APPROVED(28006, "Payroll is already approved", HttpStatus.BAD_REQUEST),
    PAYROLL_ALREADY_PAID(28007, "Payroll is already paid", HttpStatus.BAD_REQUEST),
    PAYROLL_INVALID_STATUS(28008, "Invalid payroll status", HttpStatus.BAD_REQUEST),
    PAYROLL_PERIOD_NOT_ACTIVE(28009, "Pay period is not active", HttpStatus.BAD_REQUEST),

    // PayrollComponents-related errors
    PAYROLL_COMPONENT_NOT_FOUND(29001, "Payroll component not found", HttpStatus.NOT_FOUND),
    PAYROLL_COMPONENT_ALREADY_EXISTS(29002, "Payroll component already exists", HttpStatus.CONFLICT),
    PAYROLL_COMPONENT_UNABLE_TO_SAVE(29003, "Unable to save payroll component", HttpStatus.INTERNAL_SERVER_ERROR),
    PAYROLL_COMPONENT_UNABLE_TO_UPDATE(29004, "Unable to update payroll component", HttpStatus.INTERNAL_SERVER_ERROR),
    PAYROLL_COMPONENT_UNABLE_TO_DELETE(29005, "Unable to delete payroll component", HttpStatus.INTERNAL_SERVER_ERROR),
    PAYROLL_COMPONENT_INVALID_AMOUNT(29006, "Invalid payroll component amount", HttpStatus.BAD_REQUEST),
    PAYROLL_COMPONENT_INVALID_PERCENTAGE(29007, "Invalid payroll component percentage", HttpStatus.BAD_REQUEST),
    PAYROLL_COMPONENT_INVALID_TYPE(29008, "Invalid payroll component type", HttpStatus.BAD_REQUEST),

    // Regulations-related errors
    REGULATION_NOT_FOUND(30001, "Regulation not found", HttpStatus.NOT_FOUND),
    REGULATION_ALREADY_EXISTS(30002, "Regulation already exists", HttpStatus.CONFLICT),
    REGULATION_UNABLE_TO_SAVE(30003, "Unable to save regulation", HttpStatus.INTERNAL_SERVER_ERROR),
    REGULATION_UNABLE_TO_UPDATE(30004, "Unable to update regulation", HttpStatus.INTERNAL_SERVER_ERROR),
    REGULATION_UNABLE_TO_DELETE(30005, "Unable to delete regulation", HttpStatus.INTERNAL_SERVER_ERROR),
    REGULATION_INVALID_AMOUNT(30006, "Invalid regulation amount", HttpStatus.BAD_REQUEST),
    REGULATION_INVALID_PERCENTAGE(30007, "Invalid regulation percentage", HttpStatus.BAD_REQUEST),
    REGULATION_INVALID_EFFECTIVE_DATE(30008, "Invalid regulation effective date", HttpStatus.BAD_REQUEST),
    REGULATION_ALREADY_EFFECTIVE(30009, "Regulation is already effective", HttpStatus.BAD_REQUEST),

    // Approvals-related errors
    APPROVAL_NOT_FOUND(31001, "Approval not found", HttpStatus.NOT_FOUND),
    APPROVAL_ALREADY_EXISTS(31002, "Approval already exists for this payroll", HttpStatus.CONFLICT),
    APPROVAL_UNABLE_TO_SAVE(31003, "Unable to save approval", HttpStatus.INTERNAL_SERVER_ERROR),
    APPROVAL_UNABLE_TO_UPDATE(31004, "Unable to update approval", HttpStatus.INTERNAL_SERVER_ERROR),
    APPROVAL_UNABLE_TO_DELETE(31005, "Unable to delete approval", HttpStatus.INTERNAL_SERVER_ERROR),
    APPROVAL_INVALID_STATUS(31006, "Invalid approval status", HttpStatus.BAD_REQUEST),
    APPROVAL_ALREADY_PROCESSED(31007, "Approval already processed", HttpStatus.BAD_REQUEST),
    APPROVAL_INSUFFICIENT_PERMISSION(31008, "Insufficient permission to approve", HttpStatus.FORBIDDEN),

    // SystemRegulation-related errors
    SYSTEM_REGULATION_NOT_FOUND(32001, "System regulation not found", HttpStatus.NOT_FOUND),
    SYSTEM_REGULATION_UNABLE_TO_SAVE(32002, "Unable to save system regulation", HttpStatus.INTERNAL_SERVER_ERROR),
    SYSTEM_REGULATION_UNABLE_TO_UPDATE(32003, "Unable to update system regulation", HttpStatus.INTERNAL_SERVER_ERROR),
    SYSTEM_REGULATION_UNABLE_TO_DELETE(32004, "Unable to delete system regulation", HttpStatus.INTERNAL_SERVER_ERROR),
    SYSTEM_REGULATION_INVALID_KEY(32005, "Invalid system regulation key", HttpStatus.BAD_REQUEST),
    SYSTEM_REGULATION_INVALID_VALUE(32006, "Invalid system regulation value", HttpStatus.BAD_REQUEST),
    SYSTEM_REGULATION_READONLY(32007, "System regulation is read-only", HttpStatus.BAD_REQUEST),

    // General validation errors
    INVALID_DATE_FORMAT(33001, "Invalid date format", HttpStatus.BAD_REQUEST),
    INVALID_TIME_FORMAT(33002, "Invalid time format", HttpStatus.BAD_REQUEST),
    INVALID_NUMERIC_VALUE(33003, "Invalid numeric value", HttpStatus.BAD_REQUEST),
    INVALID_ENUM_VALUE(33004, "Invalid enum value", HttpStatus.BAD_REQUEST),
    REQUIRED_FIELD_MISSING(33005, "Required field is missing", HttpStatus.BAD_REQUEST),
    FIELD_TOO_LONG(33006, "Field value exceeds maximum length", HttpStatus.BAD_REQUEST),
    FIELD_TOO_SHORT(33007, "Field value is below minimum length", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL_FORMAT(33008, "Invalid email format", HttpStatus.BAD_REQUEST),
    INVALID_PHONE_FORMAT(33009, "Invalid phone number format", HttpStatus.BAD_REQUEST),

    // Business logic errors
    INSUFFICIENT_PRIVILEGES(34001, "Insufficient privileges to perform this action", HttpStatus.FORBIDDEN),
    OPERATION_NOT_PERMITTED(34002, "Operation not permitted in current state", HttpStatus.BAD_REQUEST),
    RESOURCE_IN_USE(34003, "Resource is currently in use and cannot be modified", HttpStatus.CONFLICT),
    DEADLINE_EXCEEDED(34004, "Deadline has been exceeded", HttpStatus.BAD_REQUEST),
    QUOTA_EXCEEDED(34005, "Quota limit exceeded", HttpStatus.BAD_REQUEST),
    WORKFLOW_VIOLATION(34006, "Action violates workflow rules", HttpStatus.BAD_REQUEST),
    DATA_INTEGRITY_VIOLATION(34007, "Data integrity constraint violation", HttpStatus.CONFLICT),

    // Document-related errors
    DOCUMENT_NOT_FOUND(35001, "Document not found", HttpStatus.NOT_FOUND),
    DOCUMENT_ALREADY_EXISTS(35002, "Document already exists", HttpStatus.CONFLICT),
    DOCUMENT_UNABLE_TO_SAVE(35003, "Unable to save document", HttpStatus.INTERNAL_SERVER_ERROR),
    DOCUMENT_UNABLE_TO_UPDATE(35004, "Unable to update document", HttpStatus.INTERNAL_SERVER_ERROR),
    DOCUMENT_UNABLE_TO_DELETE(35005, "Unable to delete document", HttpStatus.INTERNAL_SERVER_ERROR),
    DOCUMENT_INVALID_TITLE(35006, "Invalid document title", HttpStatus.BAD_REQUEST),
    DOCUMENT_INVALID_DESCRIPTION(35007, "Invalid document description", HttpStatus.BAD_REQUEST),
    DOCUMENT_INVALID_FILE_PATH(35008, "Invalid document file path", HttpStatus.BAD_REQUEST),
    DOCUMENT_INVALID_STATUS(35009, "Invalid document status", HttpStatus.BAD_REQUEST),
    DOCUMENT_ACCESS_DENIED(35010, "Access denied to document", HttpStatus.FORBIDDEN),
    DOCUMENT_EXPIRED(35011, "Document has expired", HttpStatus.BAD_REQUEST),
    DOCUMENT_NOT_APPROVED(35012, "Document is not approved", HttpStatus.BAD_REQUEST),
    DOCUMENT_ALREADY_APPROVED(35013, "Document is already approved", HttpStatus.CONFLICT),
    DOCUMENT_ALREADY_REJECTED(35014, "Document is already rejected", HttpStatus.CONFLICT),

    // DocumentTypes-related errors
    DOCUMENT_TYPE_NOT_FOUND(36001, "Document type not found", HttpStatus.NOT_FOUND),
    DOCUMENT_TYPE_ALREADY_EXISTS(36002, "Document type already exists", HttpStatus.CONFLICT),
    DOCUMENT_TYPE_UNABLE_TO_SAVE(36003, "Unable to save document type", HttpStatus.INTERNAL_SERVER_ERROR),
    DOCUMENT_TYPE_UNABLE_TO_UPDATE(36004, "Unable to update document type", HttpStatus.INTERNAL_SERVER_ERROR),
    DOCUMENT_TYPE_UNABLE_TO_DELETE(36005, "Unable to delete document type", HttpStatus.INTERNAL_SERVER_ERROR),
    DOCUMENT_TYPE_INVALID_NAME(36006, "Invalid document type name", HttpStatus.BAD_REQUEST),
    DOCUMENT_TYPE_INVALID_DESCRIPTION(36007, "Invalid document type description", HttpStatus.BAD_REQUEST),
    DOCUMENT_TYPE_IN_USE(36008, "Document type is currently in use and cannot be deleted", HttpStatus.CONFLICT),

    // DocumentAccesses-related errors
    DOCUMENT_ACCESS_NOT_FOUND(37001, "Document access record not found", HttpStatus.NOT_FOUND),
    DOCUMENT_ACCESS_ALREADY_EXISTS(37002, "Document access already exists", HttpStatus.CONFLICT),
    DOCUMENT_ACCESS_UNABLE_TO_SAVE(37003, "Unable to save document access", HttpStatus.INTERNAL_SERVER_ERROR),
    DOCUMENT_ACCESS_UNABLE_TO_UPDATE(37004, "Unable to update document access", HttpStatus.INTERNAL_SERVER_ERROR),
    DOCUMENT_ACCESS_UNABLE_TO_DELETE(37005, "Unable to delete document access", HttpStatus.INTERNAL_SERVER_ERROR),
    DOCUMENT_ACCESS_INVALID_LEVEL(37006, "Invalid document access level", HttpStatus.BAD_REQUEST),
    DOCUMENT_ACCESS_INSUFFICIENT_PERMISSION(37007, "Insufficient permission to access document", HttpStatus.FORBIDDEN),
    DOCUMENT_ACCESS_EXPIRED(37008, "Document access has expired", HttpStatus.BAD_REQUEST),

    // DocumentApprovals-related errors
    DOCUMENT_APPROVAL_NOT_FOUND(38001, "Document approval not found", HttpStatus.NOT_FOUND),
    DOCUMENT_APPROVAL_ALREADY_EXISTS(38002, "Document approval already exists", HttpStatus.CONFLICT),
    DOCUMENT_APPROVAL_UNABLE_TO_SAVE(38003, "Unable to save document approval", HttpStatus.INTERNAL_SERVER_ERROR),
    DOCUMENT_APPROVAL_UNABLE_TO_UPDATE(38004, "Unable to update document approval", HttpStatus.INTERNAL_SERVER_ERROR),
    DOCUMENT_APPROVAL_UNABLE_TO_DELETE(38005, "Unable to delete document approval", HttpStatus.INTERNAL_SERVER_ERROR),
    DOCUMENT_APPROVAL_INVALID_STATUS(38006, "Invalid document approval status", HttpStatus.BAD_REQUEST),
    DOCUMENT_APPROVAL_INVALID_REASON(38007, "Invalid document approval reason", HttpStatus.BAD_REQUEST),
    DOCUMENT_APPROVAL_ALREADY_PROCESSED(38008, "Document approval already processed", HttpStatus.CONFLICT),
    DOCUMENT_APPROVAL_INSUFFICIENT_PERMISSION(38009, "Insufficient permission to approve document", HttpStatus.FORBIDDEN),
    DOCUMENT_APPROVAL_SELF_APPROVAL_NOT_ALLOWED(38010, "Self approval is not allowed", HttpStatus.BAD_REQUEST),
    DOCUMENT_APPROVAL_DEADLINE_EXCEEDED(38011, "Document approval deadline exceeded", HttpStatus.BAD_REQUEST),
    DOCUMENT_APPROVAL_INVALID_DATE(38012, "Invalid document approval date", HttpStatus.BAD_REQUEST),
    ;

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