package com.project.hrm.services.impl;

import com.project.hrm.dto.payrollsDTO.PayrollsCreateDTO;
import com.project.hrm.dto.payrollsDTO.PayrollsDTO;
import com.project.hrm.dto.payrollsDTO.PayrollsFilter;
import com.project.hrm.dto.payrollsDTO.PayrollsUpdateDTO;
import com.project.hrm.entities.Payrolls;
import com.project.hrm.mapper.PayPeriodMapper;
import com.project.hrm.mapper.PayrollsMapper;
import com.project.hrm.repositories.PayrollsRepository;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.services.PayPeriodsService;
import com.project.hrm.services.PayrollsService;
import com.project.hrm.specifications.PayPeriodsSpecifications;
import com.project.hrm.specifications.PayrollsSpecifications;
import com.project.hrm.utils.IdGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PayrollsServiceImpl implements PayrollsService {
    private final PayrollsRepository payrollsRepository;
    private final PayrollsMapper payrollsMapper;
    private final EmployeeService employeeService;
    private final PayPeriodsService payPeriodsService;
    private final PayPeriodMapper payPeriodMapper;

    @Transactional
    @Override
    public PayrollsDTO create(PayrollsCreateDTO payrollsCreateDTO) {
        log.info("Create PayrollsDTO");
        Payrolls payrolls = payrollsMapper.toPayrrollsFromCreateDTO(payrollsCreateDTO);
        payrolls.setId(IdGenerator.getGenerationId());
        return payrollsMapper.toPayrollsDTO(payrollsRepository.save(payrolls));
    }

    @Transactional
    @Override
    public PayrollsDTO update(PayrollsUpdateDTO payrollsUpdateDTO) {
        log.info("Update PayrollsDTO");

        Payrolls payrolls = getEntityById(payrollsUpdateDTO.getId());

        if (payrollsUpdateDTO.getEmployeeId() != null && employeeService.checkExists(payrollsUpdateDTO.getEmployeeId())) {
            payrolls.setEmployee(employeeService.getEntityById(payrollsUpdateDTO.getEmployeeId()));
        }

        if (payrollsUpdateDTO.getPayPeriodId() != null && payPeriodsService.checkExistence(payrollsUpdateDTO.getPayPeriodId())) {
            payrolls.setPayPeriod(payPeriodsService.getEntityById(payrollsUpdateDTO.getPayPeriodId()));
        }

        if (payrollsUpdateDTO.getTotalIncome() != null && payrollsUpdateDTO.getTotalIncome().compareTo(BigDecimal.ZERO) > 0) {
            payrolls.setTotal_income(payrollsUpdateDTO.getTotalIncome());
        }

        if (payrollsUpdateDTO.getTotalDeduction() != null && payrollsUpdateDTO.getTotalDeduction().compareTo(BigDecimal.ZERO) > 0) {
            payrolls.setTotal_income(payrollsUpdateDTO.getTotalIncome());
        }

        if (payrollsUpdateDTO.getStatus() != null){
            payrolls.setStatus(payrollsUpdateDTO.getStatus());
        }

        if (payrollsUpdateDTO.getNetSalary() != null && payrollsUpdateDTO.getNetSalary().compareTo(BigDecimal.ZERO) > 0) {
            payrolls.setNet_salary(payrollsUpdateDTO.getNetSalary());
        } else {
            BigDecimal income = payrollsUpdateDTO.getTotalIncome();
            BigDecimal deduction = payrollsUpdateDTO.getTotalDeduction();

            if (income != null && deduction != null) {
                payrolls.setNet_salary(income.subtract(deduction));
            } else {
                log.warn("Không thể tính net_salary vì thiếu totalIncome hoặc totalDeduction");
                payrolls.setNet_salary(BigDecimal.ZERO); // hoặc giữ nguyên giá trị cũ nếu muốn
            }
        }
        return payrollsMapper.toPayrollsDTO(payrollsRepository.save(payrolls));
    }

    @Transactional
    @Override
    public void delete(Integer Id) {
        log.info("Delete PayrollsDTO");
        if (checkExistence(Id)) {
            payrollsRepository.deleteById(Id);
        } else {
            throw new EntityNotFoundException("Payrolls not found with id: " + Id);
        }

    }

    @Transactional(readOnly = true)
    @Override
    public Boolean checkExistence(Integer Id) {
        log.info("Check existence of Payrolls with Id: {}", Id);
        return payrollsRepository.existsById(Id);
    }

    @Transactional(readOnly = true)
    @Override
    public PayrollsDTO getById(Integer id) {
        log.info("Get ParollsDTO By Id: {}", id);
        return payrollsMapper.toPayrollsDTO(getEntityById(id));
    }

    @Transactional(readOnly = true)
    @Override
    public Payrolls getEntityById(Integer id) {
        log.info("Get Parolls By Id: {}", id);
        return payrollsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payrolls Not Found By Id: " + id));
    }

    @Override
    public List<PayrollsDTO> filter(PayrollsFilter payrollFilter, int page, int size) {
        log.info("Filter PayrollsDTO");

        Specification<Payrolls> payrollsSpecification = PayrollsSpecifications.filter(payrollFilter);

        Pageable pageable = PageRequest.of(page, size);

        return payrollsMapper.convertToPageEntityToPageDTO(payrollsRepository.findAll(payrollsSpecification, pageable));
    }

    @Override
    public List<PayrollsDTO> filterWithRange(BigDecimal minIncome, BigDecimal maxIncome, BigDecimal minDeduction, BigDecimal maxDeduction, BigDecimal minNetSalary, BigDecimal maxNetSalary, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<Payrolls> payrollsSpecification = PayrollsSpecifications.filterWithRange(minIncome,maxIncome, minDeduction, maxDeduction, minNetSalary, maxNetSalary);

        return payrollsMapper.convertToPageEntityToPageDTO(payrollsRepository.findAll(payrollsSpecification, pageable));
    }
}
