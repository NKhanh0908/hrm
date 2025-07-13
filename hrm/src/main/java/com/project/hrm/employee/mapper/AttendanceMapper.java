package com.project.hrm.employee.mapper;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.employee.dto.attendanceDTO.AttendanceCreateDTO;
import com.project.hrm.employee.dto.attendanceDTO.AttendanceDTO;
import com.project.hrm.employee.dto.attendanceDTO.AttendanceUpdateDTO;
import com.project.hrm.employee.entity.Attendance;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AttendanceMapper {

    private final Validator validator;

    /**
     * Converts AttendanceDTO to Attendance entity.
     * Note: Employee entity must be set separately as it requires a database lookup.
     */
    public Attendance toEntity(AttendanceDTO attendanceDTO) {
        Set<ConstraintViolation<AttendanceDTO>> violations = validator.validate(attendanceDTO);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Invalid AttendanceDTO: " + violations.stream()
                    .map(v -> v.getPropertyPath() + " " + v.getMessage())
                    .collect(Collectors.joining(", ")));
        }

        return Attendance.builder()
                .id(attendanceDTO.getId())
                .attendanceDate(attendanceDTO.getAttendanceDate())
                .checkIn(attendanceDTO.getCheckIn())
                .checkOut(attendanceDTO.getCheckOut())
                .regularTime(attendanceDTO.getRegularTime())
                .otherTime(attendanceDTO.getOtherTime())
                .shiftType(attendanceDTO.getShiftType())
                .build();
    }

    /**
     * Converts AttendanceUpdateDTO to Attendance entity.
     * Note: Employee entity must be set separately.
     */
    public Attendance toEntityFromUpdateDTO(AttendanceUpdateDTO attendanceUpdateDTO) {
        Set<ConstraintViolation<AttendanceUpdateDTO>> violations = validator.validate(attendanceUpdateDTO);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Invalid AttendanceUpdateDTO: " + violations.stream()
                    .map(v -> v.getPropertyPath() + " " + v.getMessage())
                    .collect(Collectors.joining(", ")));
        }

        return Attendance.builder()
                .id(attendanceUpdateDTO.getId())
                .attendanceDate(attendanceUpdateDTO.getAttendanceDate())
                .checkIn(attendanceUpdateDTO.getCheckIn())
                .checkOut(attendanceUpdateDTO.getCheckOut())
                .regularTime(attendanceUpdateDTO.getRegularTime())
                .otherTime(attendanceUpdateDTO.getOtherTime())
                .shiftType(attendanceUpdateDTO.getShiftType())
                .build();
    }

    /**
     * Converts AttendanceCreateDTO to Attendance entity.
     * Note: Employee entity must be set separately.
     */
    public Attendance toEntityFromCreateDTO(AttendanceCreateDTO attendanceCreateDTO) {
        Set<ConstraintViolation<AttendanceCreateDTO>> violations = validator.validate(attendanceCreateDTO);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Invalid AttendanceCreateDTO: " + violations.stream()
                    .map(v -> v.getPropertyPath() + " " + v.getMessage())
                    .collect(Collectors.joining(", ")));
        }

        return Attendance.builder()
                .attendanceDate(attendanceCreateDTO.getAttendanceDate())
                .checkIn(attendanceCreateDTO.getCheckIn())
                .shiftType(attendanceCreateDTO.getShiftType())
                .build();
    }

    /**
     * Converts Attendance entity to AttendanceDTO.
     */
    public AttendanceDTO toDTO(Attendance attendance) {
        if (attendance == null) {
            throw new IllegalArgumentException("Attendance entity cannot be null");
        }

        return AttendanceDTO.builder()
                .id(attendance.getId())
                .employeeId(attendance.getEmployee() != null ? attendance.getEmployee().getId() : null)
                .attendanceDate(attendance.getAttendanceDate())
                .checkIn(attendance.getCheckIn())
                .checkOut(attendance.getCheckOut())
                .regularTime(attendance.getRegularTime())
                .otherTime(attendance.getOtherTime())
                .shiftType(attendance.getShiftType())
                .build();
    }

    /**
     * Converts Page<Attendance> to PageDTO<AttendanceDTO>.
     */
    public PageDTO<AttendanceDTO> toAttendancePageDTO(Page<Attendance> page) {
        if (page == null) {
            throw new IllegalArgumentException("Page cannot be null");
        }

        return PageDTO.<AttendanceDTO>builder()
                .content(page.getContent()
                        .stream()
                        .map(this::toDTO)
                        .toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }


}