package com.project.hrm.employee.service;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.employee.dto.contractDTO.ContractCreateDTO;
import com.project.hrm.employee.dto.contractDTO.ContractDTO;
import com.project.hrm.employee.dto.contractDTO.ContractFilter;
import com.project.hrm.employee.dto.contractDTO.ContractUpdateDTO;
import com.project.hrm.employee.entity.Contracts;
import com.project.hrm.employee.enums.ContractStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    Map<Integer, Double> getBaseSalariesForEmployees(List<Integer> employeeIds);

}