package com.project.hrm.services;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.dayOffDTO.*;
import com.project.hrm.entities.DayOff;
import com.project.hrm.entities.Employees;
import com.project.hrm.entities.PayPeriods;
import com.project.hrm.enums.EmployeeStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface DayOffService {
    DayOffDTO create(DayOffCreateDTO dayOffCreateDTO);

    DayOffDTO update(DayOffUpdateDTO dayOffUpdateDTO);

    void delete(Integer id);

    Boolean checkExists(Integer id);

    DayOffDTO getById(Integer id);

    DayOff getEntityById(Integer id);

    List<DayOffDTO> getDayOffsByEmployeeId(Integer employeeId);

    PageDTO<DayOffDTO> filter(DayOffFilter dayOffFilter, int page, int size);

    public List<DayOffDTO> filterDynamic(DayOffFilterDynamic dayOffFilterDynamic, int page, int size);

    int countDayOffByEmployeeId(Integer employeeId, LocalDateTime startDate, LocalDateTime endDate);

    int countDayOffByEmployeeIdStatus(Integer employeeId, LocalDateTime startDate, LocalDateTime endDate);

}
