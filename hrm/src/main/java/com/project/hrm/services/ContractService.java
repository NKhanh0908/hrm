package com.project.hrm.services;

import com.project.hrm.dto.employeeDTO.ContractCreateDTO;
import com.project.hrm.dto.employeeDTO.ContractDTO;
import com.project.hrm.dto.employeeDTO.ContractUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContractService {
    List<ContractDTO> getAll();

    ContractDTO getById(Integer id);

    Boolean checkExists(Integer contractId);

    ContractDTO create(ContractCreateDTO contractCreateDTO);

    ContractDTO update(ContractUpdateDTO contractUpdateDTO);

    void delete(Integer contractId);
}