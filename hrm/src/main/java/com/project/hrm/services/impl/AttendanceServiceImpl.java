package com.project.hrm.services.impl;

import com.project.hrm.dto.attendanceDTO.*;
import com.project.hrm.entities.Attendance;
import com.project.hrm.mapper.AttendanceMapper;
import com.project.hrm.repositories.AttendanceRepository;
import com.project.hrm.services.AttendanceService;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.specifications.AttendanceSpecifications;
import com.project.hrm.utils.IdGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class AttendanceServiceImpl implements AttendanceService{
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    private final EmployeeService employeeService;


    /**
     * Creates a new {@link Attendance} record using the given {@link AttendanceCreateDTO}.
     *
     * @param attendanceCreateDTO the DTO containing attendance creation data
     * @return the created {@link AttendanceDTO} after being saved
     */
    @Transactional
    @Override
    public AttendanceDTO create(AttendanceCreateDTO attendanceCreateDTO) {
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
        log.info("Updating attendance: {}", attendanceUpdateDTO);

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
     * @param Id the ID of the attendance to delete
     * @throws EntityNotFoundException if no attendance is found with the given ID
     */
    @Transactional
    @Override
    public void delete(Integer Id) {
        log.info("Delete attendance with Id: {}", Id);
        if(checkExistence(Id)){
            attendanceRepository.deleteById(Id);
        }else {
            throw new EntityNotFoundException("Attendance with Id: " + Id + " not found");
        }
    }

    /**
     * Checks if an {@link Attendance} record exists by its ID.
     *
     * @param Id the ID of the attendance record
     * @return true if the attendance exists, false otherwise
     */
    @Transactional(readOnly = true)
    @Override
    public Boolean checkExistence(Integer Id) {
        log.info("Check existence with Id: {}",Id);
        return attendanceRepository.existsById(Id);
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
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));
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
    public List<AttendanceDTO> filter(AttendanceFilter attendanceFilter, int page, int size) {
        log.info("Filter attendance filter: {}", attendanceFilter);
        Pageable pageable = PageRequest.of(page, size);

        Specification<Attendance> specification = AttendanceSpecifications.filter(attendanceFilter);

        return attendanceRepository.findAll(specification, pageable)
                .getContent()
                .stream()
                .map(attendanceMapper::toDTO)
                .collect(Collectors.toList());
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
    public List<AttendanceDTO> filterWithRange(AttendanceFilterWithRange attendanceFilterWithRange, int page, int size) {
        log.info("Filter attendance filter with range: {}", attendanceFilterWithRange);
        Pageable pageable = PageRequest.of(page, size);

        Specification<Attendance> specification = AttendanceSpecifications.filterWithRange(attendanceFilterWithRange);
        return attendanceRepository.findAll(specification, pageable)
                .getContent()
                .stream()
                .map(attendanceMapper::toDTO)
                .collect(Collectors.toList());
    }

}
