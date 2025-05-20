package com.project.hrm.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.project.hrm.controllers.AccountController;
import com.project.hrm.dto.salaryDTO.DeductionCreateDTO;
import com.project.hrm.dto.salaryDTO.DeductionDTO;
import com.project.hrm.dto.salaryDTO.DeductionUpdateDTO;
import com.project.hrm.entities.Deduction;
import com.project.hrm.entities.Salary;
import com.project.hrm.mapper.DeductionMapper;
import com.project.hrm.mapper.SalaryMapper;
import com.project.hrm.repositories.DeductionRepository;
import com.project.hrm.services.DeductionService;
import com.project.hrm.services.SalaryService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class DeductionServiceImpl implements DeductionService{

    private final DeductionRepository deductionRepository;
    private final SalaryMapper salaryMapper;
    private final DeductionMapper deductionMapper;
    private final SalaryService salaryService;

    @Override
    public List<DeductionDTO> getAll() {
        // TODO Auto-generated method stub
        List<Deduction> list = deductionRepository.findAll();
        return list.stream()
                .map(deductionMapper::toDeductionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DeductionDTO getById(Integer id) {
        // TODO Auto-generated method stub
        return deductionMapper.toDeductionDTO(deductionRepository.findById(id)
                .orElseThrow());  
    }

    @Override
    public Boolean checkExists(Integer deductionId) {
        // TODO Auto-generated method stub
        return deductionRepository.existsById(deductionId);
    }

    @Override
    public DeductionDTO create(DeductionCreateDTO deductionCreateDTO) {
        // TODO Auto-generated method stub
        log.info("Create Duduction");

        Salary salary = salaryService.getEntityById(deductionCreateDTO.getSalaryId());
        Deduction deduction = deductionMapper.toDeductionFromCreateDTO(deductionCreateDTO, salary);

        return deductionMapper.toDeductionDTO(deductionRepository.save(deduction));
    }

    @Override
    public DeductionDTO update(DeductionUpdateDTO deductionUpdateDTO) {
        // TODO Auto-generated method stub
        log.info("Update deduction");

        Deduction deduction = deductionMapper.toDeduction(getById(deductionUpdateDTO.getId()));

        if(deductionUpdateDTO.getTypeDeduction() != null){
            deduction.setTypeDeduction(deductionUpdateDTO.getTypeDeduction());
        }
        if(deductionUpdateDTO.getAmount() != null){
            deduction.setAmount(deductionUpdateDTO.getAmount());
        }
        if(deductionUpdateDTO.getSalaryId() != null){
            deduction.setSalary(salaryService.getEntityById(deductionUpdateDTO.getSalaryId()));
        }
        
        return deductionMapper.toDeductionDTO(deductionRepository.save(deduction));
    }

    @Override
    public void delete(Integer deductionId) {
        // TODO Auto-generated method stub
        log.info("Delete deduction by id: {}", deductionId);

        if(checkExists(deductionId)){
            deductionRepository.deleteById(deductionId);
        }else{
            throw new RuntimeException("Deduction not found");
        }
    }
    
}
