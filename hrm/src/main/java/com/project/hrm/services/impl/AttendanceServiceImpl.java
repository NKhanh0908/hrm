package com.project.hrm.services.impl;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.attendanceDTO.*;
import com.project.hrm.dto.disciplinaryActionDTO.DisciplinaryActionCreateDTO;
import com.project.hrm.entities.Attendance;
import com.project.hrm.entities.Employees;
import com.project.hrm.entities.PayPeriods;
import com.project.hrm.enums.AttendanceType;
import com.project.hrm.enums.SystemRegulationKey;
import com.project.hrm.enums.ViolationSeverity;
import com.project.hrm.exceptions.CustomException;
import com.project.hrm.exceptions.Error;
import com.project.hrm.mapper.AttendanceMapper;
import com.project.hrm.repositories.AttendanceRepository;
import com.project.hrm.services.*;
import com.project.hrm.specifications.AttendanceSpecifications;
import com.project.hrm.utils.IdGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@AllArgsConstructor
@Slf4j
public class AttendanceServiceImpl implements AttendanceService{
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    private final EmployeeService employeeService;
    private final SystemRegulationService systemRegulationService;
    private final RegulationsService regulationsService;
    private final DisciplinaryActionService disciplinaryActionService;


    /**
     * Creates a new {@link Attendance} record using the given {@link AttendanceCreateDTO}.
     *
     * @param attendanceCreateDTO the DTO containing attendance creation data
     * @return the created {@link AttendanceDTO} after being saved
     */
    @Transactional
    @Override
    public AttendanceDTO create(AttendanceCreateDTO attendanceCreateDTO) {
        log.info("Create Attendance");
        Attendance attendance = attendanceMapper.toEntityFromCreateDTO(attendanceCreateDTO);
        attendance.setId(IdGenerator.getGenerationId());
        attendance.setEmployee(employeeService.getEntityById(attendance.getEmployee().getId()));

        return attendanceMapper.toDTO(attendanceRepository.save(attendance));
    }

    /**
     * Updates an existing {@link Attendance} record using the provided {@link AttendanceUpdateDTO}.
     *
     * @param attendanceUpdateDTO the DTO containing updated attendance information
     * @return the updated {@link AttendanceDTO}
     * @throws EntityNotFoundException if the attendance record or employee is not found
     */
    @Transactional
    @Override
    public AttendanceDTO update(AttendanceUpdateDTO attendanceUpdateDTO) {
        log.info("Updating attendance: {}", attendanceUpdateDTO.getId());

        Attendance attendance = getEntityById(attendanceUpdateDTO.getId());

        if (attendanceUpdateDTO.getAttendanceDate() != null) {
            attendance.setAttendanceDate(attendanceUpdateDTO.getAttendanceDate());
        }

        if (attendanceUpdateDTO.getEmployeeId() != null && employeeService.checkExists(attendanceUpdateDTO.getEmployeeId())) {
            attendance.setEmployee(employeeService.getEntityById(attendanceUpdateDTO.getEmployeeId()));
        }

        if (attendanceUpdateDTO.getCheckIn() != null) {
            attendance.setCheckIn(attendanceUpdateDTO.getCheckIn());
        }

        if (attendanceUpdateDTO.getCheckOut() != null) {
            attendance.setCheckOut(attendanceUpdateDTO.getCheckOut());
        }

        if (attendanceUpdateDTO.getRegularTime() != null) {
            attendance.setRegularTime(attendanceUpdateDTO.getRegularTime());
        }

        if (attendanceUpdateDTO.getOtherTime() != null) {
            attendance.setOtherTime(attendanceUpdateDTO.getOtherTime());
        }

        if (attendanceUpdateDTO.getShiftType() != null) {
            attendance.setShiftType(attendanceUpdateDTO.getShiftType());
        }

        return attendanceMapper.toDTO(attendanceRepository.save(attendance));
    }

    /**
     * Deletes an existing {@link Attendance} record by its ID.
     *
     * @param id the ID of the attendance to delete
     * @throws EntityNotFoundException if no attendance is found with the given ID
     */
    @Transactional
    @Override
    public void delete(Integer id) {
        log.info("Delete attendance with Id: {}", id);
        if(checkExistence(id)){
            attendanceRepository.deleteById(id);
        }else {
            throw new CustomException(Error.ATTENDANCE_NOT_FOUND);
        }
    }

    /**
     * Checks if an {@link Attendance} record exists by its ID.
     *
     * @param id the ID of the attendance record
     * @return true if the attendance exists, false otherwise
     */
    @Transactional(readOnly = true)
    @Override
    public Boolean checkExistence(Integer id) {
        log.info("Check existence with Id: {}",id);
        return attendanceRepository.existsById(id);
    }

    /**
     * Retrieves an {@link AttendanceDTO} by its ID.
     *
     * @param id the ID of the attendance to retrieve
     * @return the corresponding {@link AttendanceDTO}
     * @throws EntityNotFoundException if no attendance is found with the given ID
     */
    @Transactional(readOnly = true)
    @Override
    public AttendanceDTO getById(Integer id) {
        log.info("Get attendanceDTO by id: {}", id);
        return attendanceMapper.toDTO(getEntityById(id));
    }


    /**
     * Retrieves the raw {@link Attendance} entity by its ID.
     *
     * @param id the ID of the attendance to retrieve
     * @return the {@link Attendance} entity
     * @throws EntityNotFoundException if no attendance is found with the given ID
     */
    @Transactional(readOnly = true)
    @Override
    public Attendance getEntityById(Integer id) {
        log.info("Get attendance entity by id: {}", id);
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.ATTENDANCE_NOT_FOUND));
    }

    /**
     * Filters attendance records based on exact values (e.g., employeeId, checkIn time, etc.).
     * This method is used when filtering by specific matches, not by range.
     *
     * @param attendanceFilter the filter object containing exact match fields
     * @param page the page number (zero-based)
     * @param size the number of records per page
     * @return a list of {@link AttendanceDTO} that match the filter criteria
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<AttendanceDTO> filter(AttendanceFilter attendanceFilter, int page, int size) {
        log.info("Filter attendance filter with employeeId and Date: {}, {}", attendanceFilter.getEmployeeId(), attendanceFilter.getCheckIn());
        Pageable pageable = PageRequest.of(page, size);

        Specification<Attendance> specification = AttendanceSpecifications.filter(attendanceFilter);

        Page<Attendance> attendancePage = attendanceRepository.findAll(specification, pageable);

        return attendanceMapper.toAttendancePageDTO(attendancePage);
    }

    /**
     * Filters attendance records based on range queries (e.g., checkIn from - to, attendanceDate range).
     * This method allows querying with flexible date/time or float intervals.
     *
     * @param attendanceFilterWithRange the filter object containing from-to range values
     * @param page the page number (zero-based)
     * @param size the number of records per page
     * @return a list of {@link AttendanceDTO} that match the range filter criteria
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<AttendanceDTO> filterWithRange(AttendanceFilterWithRange attendanceFilterWithRange, int page, int size) {
        log.info("Filter attendance filter with range: {}", attendanceFilterWithRange.getEmployeeId());
        Pageable pageable = PageRequest.of(page, size);

        Specification<Attendance> specification = AttendanceSpecifications.filterWithRange(attendanceFilterWithRange);

        Page<Attendance> attendancePage = attendanceRepository.findAll(specification, pageable);

        return attendanceMapper.toAttendancePageDTO(attendancePage);
    }

    @Transactional
    @Override
    public AttendanceDTO createWhenClickCheckIn(Integer employeeId) {
        log.info("Create attendance when employeeId: {} click check in: {}", employeeId, LocalDateTime.now());

        LocalDateTime now = LocalDateTime.now();

        Employees employee = employeeService.getEmployeeIsActive(employeeId);
        if (employee == null) {
            throw new CustomException(Error.EMPLOYEE_NOT_FOUND);
        }

        if (hasUncheckedOutAttendanceOnDate(employeeId, now)) {
            log.warn("Employee ID {} has unclosed attendance on {}", employeeId, now.toLocalDate());
            throw new CustomException(Error.ATTENDANCE_ALREADY_CHECKED_IN);
        }

        // Check attendance late
        handleLateCheckIn(employeeId, now);

        // Create attendance
        Attendance attendance = new Attendance();
        attendance.setId(IdGenerator.getGenerationId());
        attendance.setEmployee(employee);
        attendance.setCheckIn(now);
        attendance.setShiftType(AttendanceType.REGULAR);

        Attendance saved = attendanceRepository.save(attendance);
        return attendanceMapper.toDTO(saved);
    }

    private void handleLateCheckIn(Integer employeeId, LocalDateTime checkInDateTime) {
        LocalTime checkInTime = checkInDateTime.toLocalTime();
        LocalTime startOfShift = LocalTime.parse(systemRegulationService.getValue(SystemRegulationKey.CHECKIN_START_TIME));

        if (checkInTime.isAfter(startOfShift)) {
            log.warn("Check-in late — Employee ID: {}, Check-in Time: {}", employeeId, checkInTime);

            DisciplinaryActionCreateDTO disciplinaryAction = new DisciplinaryActionCreateDTO();
            disciplinaryAction.setDescription("Check-in time is after start of shift");
            disciplinaryAction.setDate(checkInDateTime);
            disciplinaryAction.setEmployeeId(employeeId);
            disciplinaryAction.setRegulationId(regulationsService.getRegulationsByKey("ATTENDANCE_LATE").getId());
            disciplinaryAction.setSeverity(ViolationSeverity.LOW);
            disciplinaryAction.setPenaltyAmount(null);

            disciplinaryActionService.createDisciplinaryAction(disciplinaryAction);
        }
    }

    @Transactional
    @Override
    public AttendanceDTO setAttendanceWhenClickCheckOut(Integer employeesId) {
        log.info("Set attendance when employeeId: {} click check out: {}", employeesId, LocalDateTime.now());
        LocalDateTime now = LocalDateTime.now();
        Employees employee = employeeService.getEmployeeIsActive(employeesId);
        if (employee == null) {
            throw new CustomException(Error.EMPLOYEE_NOT_FOUND);
        }

        Attendance attendance = attendanceRepository.findFirstByEmployeeIdAndCheckOutIsNull(employeesId)
                .orElseThrow(() -> new CustomException(Error.ATTENDANCE_NOT_FOUND));

        attendance.setCheckOut(now);
        LocalTime endOfShift = LocalTime.parse(systemRegulationService.getValue(SystemRegulationKey.CHECKOUT_END_TIME));
        LocalDateTime endOfRegularShift = attendance.getCheckIn().toLocalDate().atTime(endOfShift);

        calculateAttendanceTimes(attendance, now, endOfRegularShift);

        Attendance saved = attendanceRepository.save(attendance);
        return attendanceMapper.toDTO(saved);
    }

    private void calculateAttendanceTimes(Attendance attendance, LocalDateTime now, LocalDateTime endOfRegularShift) {
        if (now.isBefore(endOfRegularShift)) {
            float regularTime = (float) Duration.between(attendance.getCheckIn(), now).toMinutes() / 60;
            attendance.setRegularTime(regularTime);
            attendance.setOtherTime(0f);
        } else {
            float regularTime = (float) Duration.between(attendance.getCheckIn(), endOfRegularShift).toMinutes() / 60;
            float otherTime = (float) Duration.between(endOfRegularShift, now).toMinutes() / 60;
            attendance.setRegularTime(regularTime);
            attendance.setOtherTime(otherTime);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public boolean hasUncheckedOutAttendanceOnDate(Integer employeeId, LocalDateTime checkInDate) {
        LocalDateTime startOfDay = checkInDate.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = checkInDate.toLocalDate().atTime(23, 59, 59);
        return attendanceRepository.existsCheckInOnDateWithoutCheckOut(employeeId, startOfDay, endOfDay);
    }

    @Transactional(readOnly = true)
    @Override
    public float getTotalRegularTimeAttendanceByPayPeriodsForEmployee(Integer employeesId, PayPeriods payPeriods) {
        log.info("Get total regular time attendance for employeeId: {} payPeriods: {}", employeesId, payPeriods);
        Employees employees = employeeService.getEmployeeIsActive(employeesId);
        return attendanceRepository.getTotalRegularTime(employees, payPeriods.getStartDate(), payPeriods.getEndDate());
    }

    @Transactional(readOnly = true)
    @Override
    public float getTotalOverTimeAttendanceByPayPeriodsForEmployee(Integer employeesId, PayPeriods payPeriods) {
        log.info("Get total over time attendance for employeeId: {} payPeriods: {}", employeesId, payPeriods);
        Employees employees = employeeService.getEmployeeIsActive(employeesId);
        return attendanceRepository.getTotalOtherTime(employees, payPeriods.getStartDate(), payPeriods.getEndDate());
    }
}
