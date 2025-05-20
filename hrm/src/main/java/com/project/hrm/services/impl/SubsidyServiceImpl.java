package com.project.hrm.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.hrm.dto.salaryDTO.SubsidyDTO;
import com.project.hrm.dto.salaryDTO.SubsidyCreateDTO;
import com.project.hrm.dto.salaryDTO.SubsidyUpdateDTO;
import com.project.hrm.entities.Salary;
import com.project.hrm.entities.Subsidy;
import com.project.hrm.mapper.SubsidyMapper;
import com.project.hrm.repositories.SubsidyRepository;
import com.project.hrm.services.SalaryService;
import com.project.hrm.services.SubsidyService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class SubsidyServiceImpl implements SubsidyService{

    private final SubsidyRepository subsidyRepository;
    private final SubsidyMapper subsidyMapper;
    private final SalaryService salaryService;


    @Override
    public List<SubsidyDTO> getAll() {
        // TODO Auto-generated method stub
        List<Subsidy> list = subsidyRepository.findAll();
        return list.stream()
                .map(subsidyMapper::toSubsidyDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SubsidyDTO getById(Integer id) {
        // TODO Auto-generated method stub
        return subsidyMapper.toSubsidyDTO(subsidyRepository.findById(id)
                .orElseThrow());  
    }

    @Override
    public Boolean checkExists(Integer SubsidyId) {
        // TODO Auto-generated method stub
        return subsidyRepository.existsById(SubsidyId);
    }

    @Override
    public SubsidyDTO create(SubsidyCreateDTO SubsidyCreateDTO) {
        // TODO Auto-generated method stub
        log.info("Create Duduction");

        Salary salary = salaryService.getEntityById(SubsidyCreateDTO.getSalaryId());
        Subsidy subsidy = subsidyMapper.toSubsidyFromCreateDTO(SubsidyCreateDTO, salary);

        return subsidyMapper.toSubsidyDTO(subsidyRepository.save(subsidy));
    }

    @Override
    public SubsidyDTO update(SubsidyUpdateDTO subsidyUpdateDTO) {
        // TODO Auto-generated method stub
        log.info("Update Subsidy");

        Subsidy subsidy = subsidyMapper.toSubsidy(getById(subsidyUpdateDTO.getId()));

        if(subsidyUpdateDTO.getTypeSubsidy() != null){
            subsidy.setTypeSubsidy(subsidyUpdateDTO.getTypeSubsidy());
        }
        if(subsidyUpdateDTO.getAmount() != null){
            subsidy.setAmount(subsidyUpdateDTO.getAmount());
        }
        if(subsidyUpdateDTO.getSalaryId() != null){
            subsidy.setSalary(salaryService.getEntityById(subsidyUpdateDTO.getSalaryId()));
        }
        
        return subsidyMapper.toSubsidyDTO(subsidyRepository.save(subsidy));
    }

    @Override
    public void delete(Integer SubsidyId) {
        // TODO Auto-generated method stub
        log.info("Delete Subsidy by id: {}", SubsidyId);

        if(checkExists(SubsidyId)){
            subsidyRepository.deleteById(SubsidyId);
        }else{
            throw new RuntimeException("Subsidy not found");
        }
    }
    
}
