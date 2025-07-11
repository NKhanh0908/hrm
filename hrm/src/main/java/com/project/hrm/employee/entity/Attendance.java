package com.project.hrm.employee.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.hrm.employee.enums.AttendanceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attendance", indexes = {
    @Index(name = "idx_attendance_employee", columnList = "employee_id"),
    @Index(name = "idx_attendance_date", columnList = "attendance_date"),
    @Index(name = "idx_attendance_checkin", columnList = "check_in"),
    @Index(name = "idx_attendance_employee_date", columnList = "employee_id, attendance_date"),
    @Index(name = "idx_attendance_employee_checkout", columnList = "employee_id, check_out")
})
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Employees employee;

    private LocalDateTime attendanceDate;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Float regularTime;
    private Float otherTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceType shiftType;
}
