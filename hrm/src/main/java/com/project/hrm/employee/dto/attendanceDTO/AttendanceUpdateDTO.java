package com.project.hrm.employee.dto.attendanceDTO;

import com.project.hrm.employee.enums.AttendanceType;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import jakarta.validation.constraints.*;
import java.lang.annotation.*;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidAttendance
public class AttendanceUpdateDTO {
    @NotNull(message = "Attendance ID cannot be null")
    @Positive(message = "Attendance ID must be a positive number")
    private Integer id;

    @NotNull(message = "Employee ID cannot be null")
    @Positive(message = "Employee ID must be a positive number")
    private Integer employeeId;

    @NotNull(message = "Attendance date cannot be null")
    @PastOrPresent(message = "Attendance date must be today or in the past")
    private LocalDateTime attendanceDate;

    @NotNull(message = "Check-in time cannot be null")
    @PastOrPresent(message = "Check-in time must be today or in the past")
    private LocalDateTime checkIn;

    @PastOrPresent(message = "Check-out time must be today or in the past")
    private LocalDateTime checkOut;

    @Min(value = 0, message = "Regular time cannot be negative")
    private Float regularTime;

    @Min(value = 0, message = "Other time cannot be negative")
    private Float otherTime;

    @NotNull(message = "Shift type cannot be null")
    private AttendanceType shiftType;
}

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AttendanceValidator.class)
@interface ValidAttendance {
    String message() default "Check-out time must be after check-in time";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class AttendanceValidator implements ConstraintValidator<ValidAttendance, AttendanceUpdateDTO> {
    @Override
    public boolean isValid(AttendanceUpdateDTO dto, ConstraintValidatorContext context) {
        if (dto.getCheckIn() == null || dto.getCheckOut() == null) {
            return true; // Let @NotNull handle null checks
        }
        return dto.getCheckOut().isAfter(dto.getCheckIn());
    }
}