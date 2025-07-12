package com.project.hrm.employee.service;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.employee.dto.attendanceDTO.*;
import com.project.hrm.employee.entity.Attendance;
import com.project.hrm.payroll.entities.PayPeriods;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public interface AttendanceService {
    AttendanceDTO create(AttendanceCreateDTO attendanceCreateDTO);

    AttendanceDTO update(AttendanceUpdateDTO attendanceUpdateDTO);

    void delete(Integer Id);

    Boolean checkExistence(Integer Id);

    AttendanceDTO getById(Integer id);

    Attendance getEntityById(Integer id);

    PageDTO<AttendanceDTO> filter(AttendanceFilter attendanceFilter, int page, int size);

    PageDTO<AttendanceDTO> filterWithRange(AttendanceFilterWithRange attendanceFilterWithRange, int page, int size);

    AttendanceDTO createWhenClickCheckIn(Integer employeesId);

    AttendanceDTO setAttendanceWhenClickCheckOut(Integer employeesId);

    boolean hasUncheckedOutAttendanceOnDate(Integer employeeId, LocalDateTime checkInDate);

    float getTotalRegularTimeAttendanceByPayPeriodsForEmployee(Integer employeesId, PayPeriods payPeriods);

    float getTotalOverTimeAttendanceByPayPeriodsForEmployee(Integer employeesId, PayPeriods payPeriods);

    Map<Integer, Float> getBatchTotalRegularTime(List<Integer> employeeIds, PayPeriods payPeriods);

    Map<Integer, Float> getBatchTotalOverTime(List<Integer> employeeIds, PayPeriods payPeriods);


}
