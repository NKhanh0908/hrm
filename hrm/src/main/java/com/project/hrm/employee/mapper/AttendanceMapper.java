package com.project.hrm.employee.mapper;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.employee.dto.attendanceDTO.AttendanceCreateDTO;
import com.project.hrm.employee.dto.attendanceDTO.AttendanceDTO;
import com.project.hrm.employee.entity.Attendance;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AttendanceMapper {

    public Attendance toEntity(AttendanceDTO attendanceDTO) {
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
                .attendanceDate(attendanceCreateDTO.getAttendanceDate())
                .checkIn(attendanceCreateDTO.getCheckIn())
                .checkOut(attendanceCreateDTO.getCheckOut())
                .regularTime(attendanceCreateDTO.getRegularTime())
                .otherTime(attendanceCreateDTO.getOtherTime())
                .shiftType(attendanceCreateDTO.getShiftType())
                .build();
    }

    public PageDTO<AttendanceDTO> toAttendancePageDTO(Page<Attendance> page) {
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
