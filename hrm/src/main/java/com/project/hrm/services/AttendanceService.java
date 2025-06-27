package com.project.hrm.services;

import com.project.hrm.dto.attendanceDTO.*;
import com.project.hrm.entities.Attendance;
import com.project.hrm.entities.Employees;
import com.project.hrm.entities.PayPeriods;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface AttendanceService {
    AttendanceDTO create(AttendanceCreateDTO attendanceCreateDTO);

    AttendanceDTO update(AttendanceUpdateDTO attendanceUpdateDTO);

    void delete(Integer Id);

    Boolean checkExistence(Integer Id);

    AttendanceDTO getById(Integer id);

    Attendance getEntityById(Integer id);

    List<AttendanceDTO> filter(AttendanceFilter attendanceFilter, int page, int size);

    List<AttendanceDTO> filterWithRange(AttendanceFilterWithRange attendanceFilterWithRange, int page, int size);

    AttendanceDTO createWhenClickCheckIn(Integer employeesId);

    AttendanceDTO setAttendanceWhenClickCheckOut(Integer employeesId);

    boolean hasUncheckedOutAttendanceOnDate(LocalDateTime checkInDate);

    float getTotalRegularTimeAttendanceByPayPeriodsForEmployee(Integer employeesId, PayPeriods payPeriods);

    float getTotalOverTimeAttendanceByPayPeriodsForEmployee(Integer employeesId, PayPeriods payPeriods);


}
