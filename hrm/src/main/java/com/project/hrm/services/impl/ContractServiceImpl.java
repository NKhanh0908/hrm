package com.project.hrm.services.impl;

import com.project.hrm.dto.contractDTO.ContractCreateDTO;
import com.project.hrm.dto.contractDTO.ContractDTO;
import com.project.hrm.dto.contractDTO.ContractFilter;
import com.project.hrm.dto.contractDTO.ContractUpdateDTO;
import com.project.hrm.entities.Contracts;
import com.project.hrm.entities.Departments;
import com.project.hrm.entities.Employees;
import com.project.hrm.entities.Role;
import com.project.hrm.mapper.ContractMapper;
import com.project.hrm.repositories.ContractRepository;
import com.project.hrm.services.ContractService;
import com.project.hrm.services.DepartmentService;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.services.RoleService;
import com.project.hrm.specifications.ContractSpecification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ContractServiceImpl implements ContractService {
    private final ContractRepository contractRepository;

    private final ContractMapper contractMapper;

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final RoleService roleService;


    /**
     * Creates a new {@link Contracts} entity from the provided {@link ContractCreateDTO}.
     *
     * @param contractCreateDTO the DTO containing data required to create a contract
     * @return the created {@link ContractDTO} after being persisted
     * @throws RuntimeException if any referenced employee, department, or role is not found
     */
    @Transactional
    @Override
    public ContractDTO create(ContractCreateDTO contractCreateDTO) {
        log.info("Create contract");

        Employees employees = employeeService.getEntityById(contractCreateDTO.getEmployeeId());
        Departments departments = departmentService.getEntityById(contractCreateDTO.getDepartmentId());
        Role role = roleService.getEntityById(contractCreateDTO.getRoleId());

        Contracts contracts = new Contracts(contractMapper.convertCreateDTOToEntity(
                contractCreateDTO, employees, departments, role));

        return contractMapper.toDTO(contractRepository.save(contracts));
    }

    /**
     * Updates an existing {@link Contracts} entity based on non-null fields in the provided {@link ContractUpdateDTO}.
     *
     * @param contractUpdateDTO the DTO containing updated contract data; must include the contract ID
     * @return the updated {@link ContractDTO} after being persisted
     * @throws RuntimeException if the contract or any referenced entity is not found
     */
    @Transactional
    @Override
    public ContractDTO update(ContractUpdateDTO contractUpdateDTO) {
        log.info("Update contract");

        Contracts contracts = getEntityById(contractUpdateDTO.getId());

        if (contractUpdateDTO.getEmployeeId() != null) {
            contracts.setEmployee(employeeService.getEntityById(contractUpdateDTO.getEmployeeId()));
        }
        if (contractUpdateDTO.getDepartmentId() != null) {
            contracts.setDepartments(departmentService.getEntityById(contractUpdateDTO.getDepartmentId()));
        }
        if (contractUpdateDTO.getRoleId() != null) {
            contracts.setRole(roleService.getEntityById(contractUpdateDTO.getRoleId()));
        }
        if (contractUpdateDTO.getTitle() != null) {
            contracts.setTitle(contractUpdateDTO.getTitle());
        }
        if (contractUpdateDTO.getContractSigningDate() != null) {
            contracts.setContractSigningDate(contractUpdateDTO.getContractSigningDate());
        }
        if (contractUpdateDTO.getStartDate() != null) {
            contracts.setStartDate(contractUpdateDTO.getStartDate());
        }
        if (contractUpdateDTO.getEndDate() != null) {
            contracts.setEndDate(contractUpdateDTO.getEndDate());
        }
        if (contractUpdateDTO.getBaseSalary() != null) {
            contracts.setBaseSalary(contractUpdateDTO.getBaseSalary());
        }
        if (contractUpdateDTO.getDescription() != null) {
            contracts.setDescription(contractUpdateDTO.getDescription());
        }

        Contracts saved = contractRepository.save(contracts);
        return contractMapper.toDTO(saved);
    }

    /**
     * Deletes the {@link Contracts} entity with the specified ID.
     *
     * @param contractId the ID of the contract to delete
     * @throws RuntimeException if no contract is found with the given ID
     */
    @Transactional
    @Override
    public void delete(Integer contractId) {
        log.info("Delete Contract");

        Contracts contracts = getEntityById(contractId);
        contractRepository.delete(contracts);
    }

    /**
     * Retrieves a {@link ContractDTO} by its unique ID.
     *
     * @param id the ID of the contract to retrieve
     * @return the corresponding {@link ContractDTO}
     * @throws RuntimeException if no contract is found with the given ID
     */
    @Override
    public ContractDTO getById(Integer id) {
        log.info("Get contract by id: {}", id);
        return contractMapper.toDTO(getEntityById(id));
    }

    /**
     * Retrieves the raw {@link Contracts} entity by its ID.
     *
     * @param id the ID of the contract to retrieve
     * @return the corresponding {@link Contracts} entity
     * @throws RuntimeException if no contract is found with the given ID
     */
    @Override
    public Contracts getEntityById(Integer id) {
        log.info("Get contract entity by id: {}", id);
        return contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));
    }

    /**
     * Filters {@link Contracts} entities based on the provided {@link ContractFilter} criteria,
     * and returns a list of matching {@link ContractDTO}s.
     *
     * @param contractFilter the filter criteria encapsulating search parameters
     * @param page           the zero-based page index
     * @param size           the number of records per page
     * @return a list of {@link ContractDTO} matching the filter conditions
     */
    @Override
    public List<ContractDTO> filter(ContractFilter contractFilter, int page, int size) {
        log.info("Filter contract");

        Specification<Contracts> spec = ContractSpecification.filter(contractFilter);
        Pageable pageable = PageRequest.of(page, size);
        Page<Contracts> contractsPage = contractRepository.findAll(spec, pageable);

        return contractMapper.convertPageToList(contractsPage);
    }
}
