package com.project.hrm.services;

import com.project.hrm.dto.payrollsDTO.PayrollsCreateDTO;
import com.project.hrm.dto.payrollsDTO.PayrollsDTO;
import com.project.hrm.dto.payrollsDTO.PayrollsFilter;
import com.project.hrm.dto.payrollsDTO.PayrollsUpdateDTO;
import com.project.hrm.entities.Payrolls;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface PayrollsService {
    PayrollsDTO create(PayrollsCreateDTO payrollsCreateDTO);

    PayrollsDTO update(PayrollsUpdateDTO payrollsUpdateDTO);

    void delete(Integer Id);

    Boolean checkExistence(Integer Id);

    PayrollsDTO getById(Integer id);

    Payrolls getEntityById(Integer id);

    List<PayrollsDTO> filter(PayrollsFilter payrollFilter, int page, int size);

    List<PayrollsDTO> filterWithRange(BigDecimal minIncome, BigDecimal maxIncome, BigDecimal minDeduction, BigDecimal maxDeduction, BigDecimal minNetSalary, BigDecimal maxNetSalary, int page, int size);
}
