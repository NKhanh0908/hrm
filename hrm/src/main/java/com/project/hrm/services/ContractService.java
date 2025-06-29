package com.project.hrm.services;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.contractDTO.ContractCreateDTO;
import com.project.hrm.dto.contractDTO.ContractDTO;
import com.project.hrm.dto.contractDTO.ContractFilter;
import com.project.hrm.dto.contractDTO.ContractUpdateDTO;
import com.project.hrm.entities.Contracts;
import com.project.hrm.enums.ContractStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContractService {
    ContractDTO create(ContractCreateDTO contractCreateDTO);

    ContractDTO update(ContractUpdateDTO contractUpdateDTO);

    void updateStatus(Integer id, ContractStatus status);

    void delete(Integer contractId);

    ContractDTO getById(Integer id);

    Contracts getEntityById(Integer id);

    PageDTO<ContractDTO> filter(ContractFilter contractFilter, int page, int size);

    ContractDTO getCurrentActiveContract(Integer employeeId);

    byte[] generateContractReport(Integer id) throws Exception ;

    byte[] generateContractListReport(List<ContractDTO> contracts) throws Exception;

}