package com.project.hrm.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.hrm.dto.salaryDTO.SalaryCreateDTO;
import com.project.hrm.dto.salaryDTO.SalaryDTO;
import com.project.hrm.dto.salaryDTO.SalaryUpdateDTO;
import com.project.hrm.entities.Employees;
import com.project.hrm.entities.Salary;
import com.project.hrm.mapper.SalaryMapper;
import com.project.hrm.repositories.SalaryRepository;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.services.SalaryService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@AllArgsConstructor
public class SalaryServiceImpl implements SalaryService{

    private final EmployeeService employeeService;
    private final SalaryRepository salaryRepository;
    private final SalaryMapper salaryMapper;


    @Override
    public List<SalaryDTO> getAll() {
        // TODO Auto-generated method stub
        List<Salary> list = salaryRepository.findAll();
        return list.stream()
                .map(salaryMapper::toSalaryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SalaryDTO getById(Integer id) {
        return salaryMapper.toSalaryDTO(salaryRepository.findById(id)
                .orElseThrow());
    }

    @Override
    public Boolean checkExists(Integer salaryId) {
        // TODO Auto-generated method stub
       return salaryRepository.existsById(salaryId);
    }

    @Override
    public SalaryDTO create(SalaryCreateDTO salaryCreateDTO) {
        // TODO Auto-generated method stub
        log.info("Create salary");

        Employees employee = employeeService.getEntityById(salaryCreateDTO.getEmployeeId());
        Salary salary = salaryMapper.toSalaryFromCreateDTO(salaryCreateDTO, employee);

        return salaryMapper.toSalaryDTO(salaryRepository.save(salary));
    }

    @Override
    public SalaryDTO update(SalaryUpdateDTO salaryUpdateDTO) {
        // TODO Auto-generated method stub
        log.info("Update Salary");

        Salary salary = salaryMapper.toSalary(getById(salaryUpdateDTO.getId()));
        if(salaryUpdateDTO.getTime() != null){
            salary.setTime(salaryUpdateDTO.getTime());
        }
        if(salaryUpdateDTO.getEmployeeId() != null){
            salary.setEmployee(employeeService.getEntityById(salaryUpdateDTO.getEmployeeId()));
        }
        return salaryMapper.toSalaryDTO(salaryRepository.save(salary));
    }

    @Override
    public void delete(Integer salaryId) {
        // TODO Auto-generated method stub
        log.info("Delete Salary by id: {}", salaryId);

        if(checkExists(salaryId)){
            salaryRepository.deleteById(salaryId);
        }else{
            throw new RuntimeException("Salary not found");
        }
    }

    @Override
    public Salary getEntityById(Integer id) {
        // TODO Auto-generated method stub
        return salaryRepository.findById(id).orElseThrow();
    }
}
