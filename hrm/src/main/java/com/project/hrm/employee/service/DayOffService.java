package com.project.hrm.employee.service;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.employee.dto.dayOffDTO.*;
import com.project.hrm.employee.entity.DayOff;
import com.project.hrm.payroll.entities.PayPeriods;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    Map<Integer, Integer> getBatchDayOffCount(List<Integer> employeeIds, PayPeriods payPeriods);

    Map<Integer, Integer> getBatchDayOffNotAcceptCount(List<Integer> employeeIds, PayPeriods payPeriods);


}
