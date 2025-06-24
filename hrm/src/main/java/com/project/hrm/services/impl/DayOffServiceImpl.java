package com.project.hrm.services.impl;

import com.project.hrm.dto.dayOffDTO.*;
import com.project.hrm.entities.DayOff;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class DayOffServiceImpl implements DayOffService {
    private final DayOffRepository dayOffRepository;
    private final DayOffMapper dayOffMapper;
    private final EmployeeService employeeService;

    @Transactional
    @Override
    public DayOffDTO create(DayOffCreateDTO dayOffCreateDTO) {
        log.info("Inside create dayOff");
        DayOff dayOff = dayOffMapper.toEntityfromDTO(dayOffCreateDTO);
        dayOff.setId(IdGenerator.getGenerationId());
        return dayOffMapper.toDTO(dayOffRepository.save(dayOff));
    }

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
            dayOff.setStatus(dayOffUpdateDTO.getStatus());
        }

        if (dayOffUpdateDTO.getEmployeeId() != null && employeeService.checkExists(dayOffUpdateDTO.getEmployeeId())) {
            dayOff.setEmployee(employeeService.getEntityById(dayOffUpdateDTO.getEmployeeId()));
        }

        DayOff updated = dayOffRepository.save(dayOff);
        return dayOffMapper.toDTO(updated);
    }

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

    @Transactional(readOnly = true)
    @Override
    public Boolean checkExists(Integer id) {
        log.info("Check exists if DayOff with id: {}" , id);
        return dayOffRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public DayOffDTO getById(Integer id) {
        log.info("Get Day Off DTO by id: {}", id);
        return dayOffMapper.toDTO(getEntityById(id));
    }

    @Transactional(readOnly = true)
    @Override
    public DayOff getEntityById(Integer id) {
        log.info("Get Day Off by id: {}", id);
        return dayOffRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Day Off found with id: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<DayOffDTO> getDayOffsByEmployeeId(Integer employeeId) {
        log.info("Get Day Offs by Employee Id: {}", employeeId);
        List<DayOff> dayOffsList = dayOffRepository.findDayOffsByEmployeeIdNative(employeeId);

        return dayOffsList.stream()
                .map(dayOffMapper::toDTO)
                .collect(Collectors.toList());
    }

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


}
