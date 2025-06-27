package com.project.hrm.services.impl;

import com.project.hrm.dto.contractDTO.ContractCreateDTO;
import com.project.hrm.dto.contractDTO.ContractDTO;
import com.project.hrm.dto.contractDTO.ContractFilter;
import com.project.hrm.dto.contractDTO.ContractUpdateDTO;
import com.project.hrm.entities.Contracts;
import com.project.hrm.entities.Employees;
import com.project.hrm.entities.Role;
import com.project.hrm.enums.ContractStatus;
import com.project.hrm.exceptions.CustomException;
import com.project.hrm.exceptions.Error;
import com.project.hrm.mapper.ContractMapper;
import com.project.hrm.repositories.ContractRepository;
import com.project.hrm.services.ContractService;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.services.RoleService;
import com.project.hrm.specifications.ContractSpecification;
import com.project.hrm.utils.IdGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ContractServiceImpl implements ContractService {
    private final ContractRepository contractRepository;

    private final ContractMapper contractMapper;

    private final EmployeeService employeeService;
    private final RoleService roleService;

    /**
     * Validates the start date, end date, and signing date of a contract.
     * <p>
     * - Throws {@link CustomException} if the start date is after the end date.
     * - Throws {@link CustomException} if the signing date is after the start date.
     *
     * @param startDate   the start date of the contract
     * @param endDate     the end date of the contract
     * @param signingDate the date the contract was signed
     * @throws CustomException if the date logic is invalid
     */
    private void validateContractDates(LocalDateTime startDate, LocalDateTime endDate, LocalDateTime signingDate) {
        if (startDate.isAfter(endDate)) {
            throw new CustomException(Error.INVALID_CONTRACT_PERIOD);
        }
        if (signingDate.isAfter(startDate)) {
            throw new CustomException(Error.SIGNING_DATE_AFTER_START_DATE);
        }
    }

    /**
     * Scheduled job that runs daily to update the statuses of contracts based on the current date.
     * <p>
     * - Activates contracts that are signed and have reached their start date.
     * - Expires contracts that have passed their end date.
     * <p>
     * The execution schedule is defined in application properties using `jobs.daily-report.cron`
     * and the time zone using `jobs.time-zone`.
     */
    @Scheduled(cron = "${jobs.daily-report.cron}", zone = "${jobs.time-zone}")
    @Transactional
    public void updateContractStatuses() {
        log.info("Flow Update contract status ");

        LocalDateTime now = LocalDateTime.now();

        contractRepository.activateSignedContracts(now);

        contractRepository.expireActiveContracts(now);
    }

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

        if(contractRepository.existsOverlappingContract(contractCreateDTO.getEmployeeId(),
                contractCreateDTO.getRoleId(),
                contractCreateDTO.getStartDate(),
                contractCreateDTO.getEndDate())){
            log.error("409: An active contract of this role already exists for employee ID:{}", contractCreateDTO.getEmployeeId());
            throw new CustomException(Error.CONTRACT_ALREADY_EXISTS);
        }

        validateContractDates(contractCreateDTO.getStartDate(), contractCreateDTO.getEndDate(), contractCreateDTO.getContractSigningDate());

        Employees employees = employeeService.getEntityById(contractCreateDTO.getEmployeeId());

        Role role = roleService.getEntityById(contractCreateDTO.getRoleId());

        Contracts contracts = contractMapper.convertCreateDTOToEntity(
                contractCreateDTO, employees, role);
        contracts.setId(IdGenerator.getGenerationId());

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

        if(!contracts.getContractStatus().equals(ContractStatus.SIGNED)){
            throw new CustomException(Error.CONTRACT_UNABLE_TO_UPDATE);
        }

        if (contractUpdateDTO.getEmployeeId() != null) {
            contracts.setEmployee(employeeService.getEntityById(contractUpdateDTO.getEmployeeId()));
        }
        if (contractUpdateDTO.getRoleId() != null) {
            contracts.setRole(roleService.getEntityById(contractUpdateDTO.getRoleId()));
        }
        if (contractUpdateDTO.getTitle() != null && !contractUpdateDTO.getTitle().trim().isEmpty()) {
            contracts.setTitle(contractUpdateDTO.getTitle());
        }
        if (contractUpdateDTO.getContractSigningDate() != null) {
            validateContractDates(contracts.getStartDate(), contracts.getEndDate(), contractUpdateDTO.getContractSigningDate());
            contracts.setContractSigningDate(contractUpdateDTO.getContractSigningDate());
        }
        if (contractUpdateDTO.getStartDate() != null) {
            validateContractDates(contractUpdateDTO.getStartDate(), contracts.getEndDate(), contracts.getContractSigningDate());
            contracts.setStartDate(contractUpdateDTO.getStartDate());
        }
        if (contractUpdateDTO.getEndDate() != null) {
            validateContractDates(contracts.getStartDate(), contractUpdateDTO.getEndDate(), contracts.getContractSigningDate());
            contracts.setEndDate(contractUpdateDTO.getEndDate());
        }
        if (contractUpdateDTO.getBaseSalary() != null) {
            contracts.setBaseSalary(contractUpdateDTO.getBaseSalary());
        }
        if (contractUpdateDTO.getDescription() != null && !contractUpdateDTO.getDescription().trim().isEmpty()) {
            contracts.setDescription(contractUpdateDTO.getDescription());
        }

        Contracts saved = contractRepository.save(contracts);
        return contractMapper.toDTO(saved);
    }

    /**
     * Change the status of an existing contract.
     *
     * @param id     the ID of the contract
     * @param status the new {@link ContractStatus} to set
     * @throws EntityNotFoundException if no contract exists with the given ID
     */
    @Transactional
    @Override
    public void updateStatus(Integer id, ContractStatus status) {
        log.info("Update status contract id: {}", id);

        if (!contractRepository.existsById(id)) {
            throw new CustomException(Error.CONTRACT_NOT_FOUND);
        }

        int rows = contractRepository.updateStatus(id, status.name());

        if (rows != 1) {
            throw new CustomException(Error.CONTRACT_UNABLE_TO_UPDATE);
        }
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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    @Override
    public Contracts getEntityById(Integer id) {
        log.info("Get contract entity by id: {}", id);
        return contractRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.CONTRACT_NOT_FOUND));
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
    @Transactional(readOnly = true)
    @Override
    public List<ContractDTO> filter(ContractFilter contractFilter, int page, int size) {
        log.info("Filter contract");

        Specification<Contracts> spec = ContractSpecification.filter(contractFilter);
        Pageable pageable = PageRequest.of(page, size);
        Page<Contracts> contractsPage = contractRepository.findAll(spec, pageable);

        return contractMapper.convertPageToList(contractsPage);
    }
}
