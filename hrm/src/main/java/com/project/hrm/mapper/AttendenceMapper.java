package com.project.hrm.mapper;

import com.project.hrm.dto.attendenceDTO.AttendenceCreateDTO;
import com.project.hrm.dto.attendenceDTO.AttendenceDTO;
import com.project.hrm.entities.Attendence;
import com.project.hrm.repositories.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AttendenceMapper {
    private final EmployeeRepository employeeRepository;

    public Attendence toEntity(AttendenceDTO attendenceDTO) {
        return Attendence.builder()
                .id(attendenceDTO.getId())
                .employee(employeeRepository.findById(attendenceDTO.getEmployeeId())
                        .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + attendenceDTO.getEmployeeId())))
                .attendenceDate(attendenceDTO.getAttendenceDate())
                .checkIn(attendenceDTO.getCheckIn())
                .checkOut(attendenceDTO.getCheckOut())
                .regularTime(attendenceDTO.getRegularTime())
                .otherTime(attendenceDTO.getOtherTime())
                .shiftType(attendenceDTO.getShiftType())
                .build();
    }

    public AttendenceDTO toDTO(Attendence attendence) {
        return AttendenceDTO.builder()
                .id(attendence.getId())
                .employeeId(attendence.getEmployee().getId())
                .attendenceDate(attendence.getAttendenceDate())
                .checkIn(attendence.getCheckIn())
                .checkOut(attendence.getCheckOut())
                .regularTime(attendence.getRegularTime())
                .otherTime(attendence.getOtherTime())
                .shiftType(attendence.getShiftType())
                .build();
    }

    public Attendence toEntityFromCreateDTO(AttendenceCreateDTO attendenceCreateDTO) {
        return Attendence.builder()
                .employee(employeeRepository.findById(attendenceCreateDTO.getEmployeeId())
                        .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + attendenceCreateDTO.getEmployeeId())))
                .attendenceDate(attendenceCreateDTO.getAttendenceDate())
                .checkIn(attendenceCreateDTO.getCheckIn())
                .checkOut(attendenceCreateDTO.getCheckOut())
                .regularTime(attendenceCreateDTO.getRegularTime())
                .otherTime(attendenceCreateDTO.getOtherTime())
                .shiftType(attendenceCreateDTO.getShiftType())
                .build();
    }
}
