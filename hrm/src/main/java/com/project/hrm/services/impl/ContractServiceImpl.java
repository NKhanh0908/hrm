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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class ContractServiceImpl implements ContractService {
    private final ContractRepository contractRepository;

    private final ContractMapper contractMapper;

    private final EmployeeService employeeService;
    private final RoleService roleService;

    private static final String REPORT_PATH = "/reports/";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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

        LocalDateTime twoYearsAgo = LocalDateTime.now().minusYears(2);
        if (startDate.isBefore(twoYearsAgo)) {
            throw new CustomException(Error.START_DATE_TOO_OLD);
        }

        if (startDate.plusDays(30).isAfter(endDate)) {
            throw new CustomException(Error.CONTRACT_PERIOD_TOO_SHORT);
        }
    }

    private void validateContractCreation(ContractCreateDTO contractCreateDTO) {
        if (contractRepository.existsOverlappingActiveContract(
                contractCreateDTO.getEmployeeId(),
                contractCreateDTO.getRoleId(),
                contractCreateDTO.getStartDate(),
                contractCreateDTO.getEndDate())) {
            log.error("Overlap detected for employee ID: {} and role ID: {}",
                    contractCreateDTO.getEmployeeId(), contractCreateDTO.getRoleId());
            throw new CustomException(Error.CONTRACT_ALREADY_EXISTS);
        }

        Optional<Contracts> currentContract = contractRepository.findCurrentActiveContract(
                contractCreateDTO.getEmployeeId());

        if (currentContract.isPresent()) {
            Contracts existing = currentContract.get();
            if (!existing.getRole().getId().equals(contractCreateDTO.getRoleId())) {
                log.warn("Employee {} has active contract with different role. Consider terminating existing contract first.",
                        contractCreateDTO.getEmployeeId());
                throw new CustomException(Error.EMPLOYEE_HAS_ACTIVE_CONTRACT_DIFFERENT_ROLE);
            }
        }
    }

    private void validateContractUpdate(ContractUpdateDTO contractUpdateDTO, Contracts existingContract) {
        if (!existingContract.getContractStatus().equals(ContractStatus.SIGNED)) {
            throw new CustomException(Error.CONTRACT_UNABLE_TO_UPDATE);
        }

        if (contractUpdateDTO.getStartDate() != null || contractUpdateDTO.getEndDate() != null) {
            LocalDateTime newStartDate = contractUpdateDTO.getStartDate() != null ?
                    contractUpdateDTO.getStartDate() : existingContract.getStartDate();
            LocalDateTime newEndDate = contractUpdateDTO.getEndDate() != null ?
                    contractUpdateDTO.getEndDate() : existingContract.getEndDate();

            if (contractRepository.existsOverlappingActiveContract(
                    existingContract.getEmployee().getId(),
                    existingContract.getRole().getId(),
                    newStartDate,
                    newEndDate)) {
                throw new CustomException(Error.CONTRACT_PERIOD_CONFLICT);
            }
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
        log.info("Flow Update contract status");
        LocalDateTime now = LocalDateTime.now();

        int activated = contractRepository.activateSignedContracts(now);
        int expired = contractRepository.expireActiveContracts(now);

        log.info("Activated {} contracts, expired {} contracts", activated, expired);

//        LocalDateTime futureDate = now.plusDays(30);
//        int suspended = contractRepository.suspendExpiringContracts(now, futureDate);
//        log.info("Suspended {} contracts nearing expiration", suspended);
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
        log.info("Create contract for employee: {}", contractCreateDTO.getEmployeeId());

        validateContractDates(contractCreateDTO.getStartDate(),
                contractCreateDTO.getEndDate(),
                contractCreateDTO.getContractSigningDate());

        validateContractCreation(contractCreateDTO);

        Employees employees = employeeService.getEntityById(contractCreateDTO.getEmployeeId());
        Role role = roleService.getEntityById(contractCreateDTO.getRoleId());

        Contracts contracts = contractMapper.convertCreateDTOToEntity(contractCreateDTO, employees, role);
        contracts.setId(IdGenerator.getGenerationId());

        contracts.setContractStatus(ContractStatus.SIGNED);

        Contracts saved = contractRepository.save(contracts);
        log.info("Created contract with ID: {}", saved.getId());

        return contractMapper.toDTO(saved);
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
        log.info("Update contract ID: {}", contractUpdateDTO.getId());

        Contracts contracts = getEntityById(contractUpdateDTO.getId());
        validateContractUpdate(contractUpdateDTO, contracts);

        // Update fields
        updateContractFields(contractUpdateDTO, contracts);

        Contracts saved = contractRepository.save(contracts);
        log.info("Updated contract ID: {}", saved.getId());

        return contractMapper.toDTO(saved);
    }

    private void updateContractFields(ContractUpdateDTO dto, Contracts contract) {
        if (dto.getEmployeeId() != null) {
            contract.setEmployee(employeeService.getEntityById(dto.getEmployeeId()));
        }
        if (dto.getRoleId() != null) {
            contract.setRole(roleService.getEntityById(dto.getRoleId()));
        }
        if (dto.getTitle() != null && !dto.getTitle().trim().isEmpty()) {
            contract.setTitle(dto.getTitle());
        }
        if (dto.getContractSigningDate() != null) {
            validateContractDates(contract.getStartDate(), contract.getEndDate(), dto.getContractSigningDate());
            contract.setContractSigningDate(dto.getContractSigningDate());
        }
        if (dto.getStartDate() != null) {
            validateContractDates(dto.getStartDate(), contract.getEndDate(), contract.getContractSigningDate());
            contract.setStartDate(dto.getStartDate());
        }
        if (dto.getEndDate() != null) {
            validateContractDates(contract.getStartDate(), dto.getEndDate(), contract.getContractSigningDate());
            contract.setEndDate(dto.getEndDate());
        }
        if (dto.getBaseSalary() != null) {
            contract.setBaseSalary(dto.getBaseSalary());
        }
        if (dto.getDescription() != null && !dto.getDescription().trim().isEmpty()) {
            contract.setDescription(dto.getDescription());
        }
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
        log.info("Delete Contract ID: {}", contractId);

        Contracts contract = getEntityById(contractId);

        if (!EnumSet.of(ContractStatus.SIGNED, ContractStatus.CANCELLED).contains(contract.getContractStatus())) {
            throw new CustomException(Error.CONTRACT_UNABLE_TO_DELETE);
        }

        contractRepository.delete(contract);
        log.info("Deleted contract ID: {}", contractId);
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

    /**
     * @param employeeId
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public ContractDTO getCurrentActiveContract(Integer employeeId) {
        log.info("Get current active contract for employee: {}", employeeId);

        return contractRepository.findCurrentActiveContract(employeeId)
                .map(contractMapper::toDTO)
                .orElse(null);
    }

    /**
     * @param id
     * @return
     */
    @Transactional
    @Override
    public byte[] generateContractReport(Integer id)  throws Exception {
        ContractDTO contract = getById(id);
        Map<String, Object> reportData = createReportData(contract);

        log.debug("report data: {}", reportData);

        JasperReport page1Report = compileReport("ContractPage1.jrxml");
        JasperPrint page1Print = JasperFillManager.fillReport(page1Report, reportData,
                new JRBeanCollectionDataSource(List.of(reportData)));

//        JasperReport page2Report = compileReport("ContractPage2.jrxml");
//        JasperPrint page2Print = JasperFillManager.fillReport(page2Report, reportData,
//                new JRBeanCollectionDataSource(List.of(reportData)));
//
//        List<JasperPrint> jasperPrintList = List.of(page1Print, page2Print);
//        JasperPrint mergedReport = mergeReports(jasperPrintList);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(page1Print, outputStream);

//        Map<String, Object> reportData = createReportData();
//
//        JasperReport page1Report = compileReport("test.jrxml");
//        JasperPrint page1Print = JasperFillManager.fillReport(page1Report, reportData,
//                new JRBeanCollectionDataSource(List.of(reportData)));
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        JasperExportManager.exportReportToPdfStream(page1Print, outputStream);
        return outputStream.toByteArray();
    }

    private Map<String, Object> createReportData() {
        Map<String, Object> data = new HashMap<>();
        data.put("reportTitle", "Bao cao 1");
        return data;
    }

    private JasperReport compileReport(String reportFileName) throws Exception {
        try (InputStream reportStream = new ClassPathResource(REPORT_PATH + reportFileName).getInputStream()) {
            return JasperCompileManager.compileReport(reportStream);
        }
    }

    private JasperPrint mergeReports(List<JasperPrint> jasperPrintList) throws JRException {
        JasperPrint mergedReport = new JasperPrint();

        for (JasperPrint jasperPrint : jasperPrintList) {
            List<JRPrintPage> pages = jasperPrint.getPages();
            for (JRPrintPage page : pages) {
                mergedReport.addPage(page);
            }
        }

        return mergedReport;
    }

    private Map<String, Object> createReportData(ContractDTO contract) {
        Map<String, Object> data = new HashMap<>();

        if (contract.getEmployee() != null) {
            data.put("fullName", contract.getEmployee().getFirstName() + " " +
                    contract.getEmployee().getLastName());
            data.put("dob", contract.getEmployee().getDateOfBirth() != null ?
                    contract.getEmployee().getDateOfBirth() : "");
            data.put("citizenIdentificationCard", contract.getEmployee().getCitizenIdentificationCard());
            data.put("gender", contract.getEmployee().getGender());
            data.put("address", contract.getEmployee().getAddress());
            data.put("phone", contract.getEmployee().getPhone());
            data.put("email", contract.getEmployee().getEmail());
        }

        data.put("contractTitle", contract.getTitle());
        data.put("baseSalary", formatCurrency(contract.getBaseSalary()));
        data.put("typeContract", contract.getTitle()); // hoặc có thể là contract type riêng
        data.put("fromDate", contract.getStartDate() != null ?
                contract.getStartDate().format(DATE_FORMATTER) : "");
        data.put("toDate", contract.getEndDate() != null ?
                contract.getEndDate().format(DATE_FORMATTER) : "");
        data.put("description", contract.getDescription());
        data.put("status", contract.getStatus());

        data.put("departmentName", contract.getDepartmentName());
        data.put("roleName", contract.getRoleName());

        Map<String, Object> reportData = new HashMap<>();
        reportData.put("row", data);

        return data;
    }

    private String formatCurrency(Double amount) {
        if (amount == null) return "0 VND";
        return String.format("%,.0f VND", amount);
    }

    /**
     * @param contracts 
     * @return
     */
    public byte[] generateContractListReport(List<ContractDTO> contracts) throws Exception {
        // Tạo dữ liệu cho báo cáo danh sách
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("reportTitle", "DANH SÁCH HỢP ĐỒNG LAO ĐỘNG");
        parameters.put("generatedDate", java.time.LocalDate.now().format(DATE_FORMATTER));

        // Chuyển đổi dữ liệu
        List<Map<String, Object>> reportData = contracts.stream()
                .map(this::convertToReportMap)
                .toList();

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportData);

        // Compile và fill report (cần tạo file ContractList.jrxml)
        JasperReport jasperReport = compileReport("ContractList.jrxml");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Export PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        return outputStream.toByteArray();
    }

    private Map<String, Object> convertToReportMap(ContractDTO contract) {
        Map<String, Object> map = new HashMap<>();

        map.put("id", contract.getId());
        map.put("title", contract.getTitle());
        map.put("employeeName", contract.getEmployee() != null ?
                (contract.getEmployee().getFirstName() + " " + contract.getEmployee().getLastName()) : "");
        map.put("departmentName", contract.getDepartmentName());
        map.put("roleName", contract.getRoleName());
        map.put("startDate", contract.getStartDate() != null ?
                contract.getStartDate().format(DATE_FORMATTER) : "");
        map.put("endDate", contract.getEndDate() != null ?
                contract.getEndDate().format(DATE_FORMATTER) : "");
        map.put("baseSalary", formatCurrency(contract.getBaseSalary()));
        map.put("status", contract.getStatus());

        return map;
    }
}
