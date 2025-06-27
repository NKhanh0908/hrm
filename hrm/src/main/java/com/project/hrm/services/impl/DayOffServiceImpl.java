package com.project.hrm.services.impl;

import com.project.hrm.dto.dayOffDTO.*;
import com.project.hrm.entities.DayOff;
import com.project.hrm.enums.DayOffStatus;
import com.project.hrm.mapper.DayOffMapper;
import com.project.hrm.repositories.DayOffRepository;
import com.project.hrm.services.DayOffService;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.specifications.DayOffSpecifications;
import com.project.hrm.utils.IdGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class DayOffServiceImpl implements DayOffService {
    private final DayOffRepository dayOffRepository;
    private final DayOffMapper dayOffMapper;
    private final EmployeeService employeeService;


    /**
     * Creates a new {@link DayOff} entity from the provided {@link DayOffCreateDTO}.
     *
     * @param dayOffCreateDTO the DTO containing day-off request information
     * @return the created {@link DayOffDTO} after being persisted
     */
    @Transactional
    @Override
    public DayOffDTO create(DayOffCreateDTO dayOffCreateDTO) {
        log.info("Inside create dayOff");

        DayOff dayOff = dayOffMapper.toEntityFromDTO(dayOffCreateDTO);

        dayOff.setId(IdGenerator.getGenerationId());

        dayOff.setEmployee(employeeService.getEntityById(dayOffCreateDTO.getEmployeeId()));

        return dayOffMapper.toDTO(dayOffRepository.save(dayOff));
    }

    /**
     * Updates an existing {@link DayOff} entity with the given {@link DayOffUpdateDTO}.
     *
     * @param dayOffUpdateDTO the DTO containing updated day-off request information
     * @return the updated {@link DayOffDTO} after persistence
     * @throws EntityNotFoundException if the specified day-off or employee does not exist
     */

    @Transactional
    @Override
    public DayOffDTO update(DayOffUpdateDTO dayOffUpdateDTO) {
        log.info("Inside update dayOff with id {}", dayOffUpdateDTO.getId());

        DayOff dayOff = getEntityById(dayOffUpdateDTO.getId());

        if (dayOffUpdateDTO.getRequestDay() != null) {
            dayOff.setRequestDay(dayOffUpdateDTO.getRequestDay());
        }

        if (dayOffUpdateDTO.getStartDate() != null) {
            dayOff.setStartDate(dayOffUpdateDTO.getStartDate());
        }

        if (dayOffUpdateDTO.getEndDate() != null) {
            dayOff.setEndDate(dayOffUpdateDTO.getEndDate());
        }

        if (dayOffUpdateDTO.getReason() != null && !dayOffUpdateDTO.getReason().isEmpty()) {
            dayOff.setReason(dayOffUpdateDTO.getReason());
        }

        if (dayOffUpdateDTO.getStatus() != null && !dayOffUpdateDTO.getStatus().isEmpty()) {
            dayOff.setStatus(DayOffStatus.valueOf(dayOffUpdateDTO.getStatus()));
        }

        if (dayOffUpdateDTO.getEmployeeId() != null && employeeService.checkExists(dayOffUpdateDTO.getEmployeeId())) {
            dayOff.setEmployee(employeeService.getEntityById(dayOffUpdateDTO.getEmployeeId()));
        }

        DayOff updated = dayOffRepository.save(dayOff);
        return dayOffMapper.toDTO(updated);
    }

    /**
     * Deletes a {@link DayOff} entity by its ID.
     *
     * @param id the ID of the day-off record to delete
     * @throws EntityNotFoundException if no day-off exists with the given ID
     */
    @Transactional
    @Override
    public void delete(Integer id) {
        log.info("Delete day off with id {}", id);
        if(checkExists(id)){
            dayOffRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("No such day off with id " + id);
        }
    }

    /**
     * Checks if a {@link DayOff} entity exists by its ID.
     *
     * @param id the ID to check
     * @return true if a day-off with the given ID exists, false otherwise
     */
    @Transactional(readOnly = true)
    @Override
    public Boolean checkExists(Integer id) {
        log.info("Check exists if DayOff with id: {}" , id);
        return dayOffRepository.existsById(id);
    }

    /**
     * Retrieves a {@link DayOffDTO} by its ID.
     *
     * @param id the ID of the day-off record
     * @return the corresponding {@link DayOffDTO}
     * @throws EntityNotFoundException if the day-off does not exist
     */
    @Transactional(readOnly = true)
    @Override
    public DayOffDTO getById(Integer id) {
        log.info("Get Day Off DTO by id: {}", id);
        return dayOffMapper.toDTO(getEntityById(id));
    }

    /**
     * Retrieves a {@link DayOff} entity by its ID.
     *
     * @param id the ID of the day-off record
     * @return the corresponding {@link DayOff} entity
     * @throws EntityNotFoundException if no entity exists with the given ID
     */
    @Transactional(readOnly = true)
    @Override
    public DayOff getEntityById(Integer id) {
        log.info("Get Day Off by id: {}", id);
        return dayOffRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Day Off found with id: " + id));
    }

    /**
     * Retrieves all {@link DayOffDTO} records for a specific employee.
     *
     * @param employeeId the ID of the employee
     * @return a list of {@link DayOffDTO} associated with the employee
     */
    @Transactional(readOnly = true)
    @Override
    public List<DayOffDTO> getDayOffsByEmployeeId(Integer employeeId) {
        log.info("Get Day Offs by Employee Id: {}", employeeId);
        List<DayOff> dayOffsList = dayOffRepository.findDayOffsByEmployeeIdNative(employeeId);

        return dayOffsList.stream()
                .map(dayOffMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Filters {@link DayOff} entities using the provided {@link DayOffFilter},
     * and returns a paginated list of {@link DayOffDTO}.
     *
     * @param dayOffFilter the filtering criteria
     * @param page         the page number (zero-based)
     * @param size         the number of records per page
     * @return a list of filtered {@link DayOffDTO}
     */
    @Transactional(readOnly = true)
    @Override
    public List<DayOffDTO> filter(DayOffFilter dayOffFilter, int page, int size) {
        log.info("Get Day Offs by Filter: {}", dayOffFilter);

        Specification<DayOff> dayOffSpecification = DayOffSpecifications.filter(dayOffFilter);

        Pageable pageable = PageRequest.of(page, size);

        return dayOffRepository.findAll(dayOffSpecification, pageable)
                .getContent()
                .stream().map(dayOffMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Dynamically filters {@link DayOff} entities based on complex criteria
     * in {@link DayOffFilterDynamic}, and returns a paginated list of {@link DayOffDTO}.
     *
     * @param dayOffFilterDynamic the dynamic filtering criteria
     * @param page                the page number (zero-based)
     * @param size                the number of records per page
     * @return a list of dynamically filtered {@link DayOffDTO}
     */
    @Transactional(readOnly = true)
    @Override
    public List<DayOffDTO> filterDynamic(DayOffFilterDynamic dayOffFilterDynamic, int page, int size) {
        log.info("Get Day Offs by Filter Dynamic");

        Pageable pageable = PageRequest.of(page, size);

        Specification<DayOff> dayOffSpecification = DayOffSpecifications.filterDynamic(dayOffFilterDynamic);
        return dayOffRepository.findAll(dayOffSpecification, pageable)
                .getContent()
                .stream().map(dayOffMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public int countDayOffByEmployeeId(Integer employeeId, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Count Day Offs by Employee Id: {}", employeeId);

        DayOffFilterDynamic dayOffFilterDynamic = new DayOffFilterDynamic();
        dayOffFilterDynamic.setStartDateFrom(startDate);
        dayOffFilterDynamic.setEndDateTo(endDate);
        dayOffFilterDynamic.setUseStartEndOverlapFilter(Boolean.TRUE);

        Specification<DayOff> spec = DayOffSpecifications.filterDynamic(dayOffFilterDynamic);

        List<DayOff> dayOffs = dayOffRepository.findAll(spec);

        Set<LocalDate> uniqueDaysOff = getUniqueDaysOff(startDate, endDate, dayOffs);

        return uniqueDaysOff.size();
    }

    @Override
    public int countDayOffByEmployeeIdStatus(Integer employeeId, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Count Day Offs status by Employee Id: {}", employeeId);

        DayOffFilterDynamic dayOffFilterDynamic = new DayOffFilterDynamic();
        dayOffFilterDynamic.setStartDateFrom(startDate);
        dayOffFilterDynamic.setEndDateTo(endDate);
        dayOffFilterDynamic.setUseStartEndOverlapFilter(Boolean.TRUE);
        dayOffFilterDynamic.setStatus(String.valueOf(DayOffStatus.PENDING));

        Specification<DayOff> spec = DayOffSpecifications.filterDynamic(dayOffFilterDynamic);

        List<DayOff> dayOffs = dayOffRepository.findAll(spec);

        Set<LocalDate> uniqueDaysOff = getUniqueDaysOff(startDate, endDate, dayOffs);

        return uniqueDaysOff.size();
    }

    private static Set<LocalDate> getUniqueDaysOff(LocalDateTime startDate, LocalDateTime endDate, List<DayOff> dayOffs) {
        Set<LocalDate> uniqueDaysOff = new HashSet<>();

        for (DayOff dayOff : dayOffs) {
            LocalDate dayOffStart = dayOff.getStartDate().toLocalDate();
            LocalDate dayOffEnd = dayOff.getEndDate().toLocalDate();
            LocalDate filterStart = startDate.toLocalDate();
            LocalDate filterEnd = endDate.toLocalDate();

            // Lấy khoảng giao nhau giữa DayOff và khoảng truyền vào
            LocalDate effectiveStart = dayOffStart.isBefore(filterStart) ? filterStart : dayOffStart;
            LocalDate effectiveEnd = dayOffEnd.isAfter(filterEnd) ? filterEnd : dayOffEnd;

            // Thêm từng ngày vào set (loại trùng)
            while (!effectiveStart.isAfter(effectiveEnd)) {
                uniqueDaysOff.add(effectiveStart);
                effectiveStart = effectiveStart.plusDays(1);
            }
        }
        return uniqueDaysOff;
    }


}
