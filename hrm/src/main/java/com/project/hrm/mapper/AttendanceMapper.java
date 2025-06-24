package com.project.hrm.mapper;

import com.project.hrm.dto.attendanceDTO.AttendanceCreateDTO;
import com.project.hrm.dto.attendanceDTO.AttendanceDTO;
import com.project.hrm.entities.Attendance;
import com.project.hrm.repositories.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AttendanceMapper {
    private final EmployeeRepository employeeRepository;

    public Attendance toEntity(AttendanceDTO attendanceDTO) {
        return Attendance.builder()
                .id(attendanceDTO.getId())
                .employee(employeeRepository.findById(attendanceDTO.getEmployeeId())
                        .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + attendanceDTO.getEmployeeId())))
                .attendanceDate(attendanceDTO.getAttendanceDate())
                .checkIn(attendanceDTO.getCheckIn())
                .checkOut(attendanceDTO.getCheckOut())
                .regularTime(attendanceDTO.getRegularTime())
                .otherTime(attendanceDTO.getOtherTime())
                .shiftType(attendanceDTO.getShiftType())
                .build();
    }

    public AttendanceDTO toDTO(Attendance attendance) {
        return AttendanceDTO.builder()
                .id(attendance.getId())
                .employeeId(attendance.getEmployee().getId())
                .attendanceDate(attendance.getAttendanceDate())
                .checkIn(attendance.getCheckIn())
                .checkOut(attendance.getCheckOut())
                .regularTime(attendance.getRegularTime())
                .otherTime(attendance.getOtherTime())
                .shiftType(attendance.getShiftType())
                .build();
    }

    public Attendance toEntityFromCreateDTO(AttendanceCreateDTO attendanceCreateDTO) {
        return Attendance.builder()
                .employee(employeeRepository.findById(attendanceCreateDTO.getEmployeeId())
                        .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + attendanceCreateDTO.getEmployeeId())))
                .attendanceDate(attendanceCreateDTO.getAttendanceDate())
                .checkIn(attendanceCreateDTO.getCheckIn())
                .checkOut(attendanceCreateDTO.getCheckOut())
                .regularTime(attendanceCreateDTO.getRegularTime())
                .otherTime(attendanceCreateDTO.getOtherTime())
                .shiftType(attendanceCreateDTO.getShiftType())
                .build();
    }
}
