package com.project.hrm.services.impl;

import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.approvalsDTO.ApprovalsCreateDTO;
import com.project.hrm.dto.approvalsDTO.ApprovalsDTO;
import com.project.hrm.dto.approvalsDTO.ApprovalsFilter;
import com.project.hrm.dto.approvalsDTO.ApprovalsUpdateDTO;
import com.project.hrm.entities.Approvals;
import com.project.hrm.mapper.ApprovalsMapper;
import com.project.hrm.mapper.PayrollsMapper;
import com.project.hrm.repositories.ApprovalsRepository;
import com.project.hrm.repositories.EmployeeRepository;
import com.project.hrm.services.ApprovalsService;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.services.PayrollsService;
import com.project.hrm.specifications.ApprovalsSpecifications;
import com.project.hrm.utils.IdGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ApprovalsServiceImpl implements ApprovalsService {
    private final ApprovalsRepository approvalsRepository;
    private final ApprovalsMapper approvalsMapper;
    private final EmployeeService employeeService;
    private final PayrollsService payrollsService;


    /**
     * Creates a new {@link Approvals} entity based on the provided {@link ApprovalsCreateDTO}.
     *
     * @param approvalsCreateDTO the DTO containing information to create an approval
     * @return the created {@link ApprovalsDTO} after being persisted
     */
    @Transactional
    @Override
    public ApprovalsDTO create(ApprovalsCreateDTO approvalsCreateDTO) {
        log.info("ApprovalsCreateDTO: {}", approvalsCreateDTO);
        Approvals approvals = approvalsMapper.toEntityFromCreateDTO(approvalsCreateDTO);

        approvals.setId(IdGenerator.getGenerationId());

        approvals.setEmployeeReview(employeeService.getEntityById(approvalsCreateDTO.getEmployeeReviewId()));

        approvals.setPayroll(payrollsService.getEntityById(approvalsCreateDTO.getPayrollId()));
        return approvalsMapper.toDTO(approvalsRepository.save(approvals));
    }


    /**
     * Updates an existing {@link Approvals} entity using the fields from the provided {@link ApprovalsUpdateDTO}.
     *
     * @param approvalsUpdateDTO the DTO containing update information, must include the approval ID
     * @return the updated {@link ApprovalsDTO} after being saved
     * @throws EntityNotFoundException if the approval or related entities do not exist
     */
    @Transactional
    @Override
    public ApprovalsDTO update(ApprovalsUpdateDTO approvalsUpdateDTO) {
        log.info("ApprovalsUpdateDTO: {}", approvalsUpdateDTO);

        Approvals approvals = getEntityById(approvalsUpdateDTO.getId());

        if(approvalsUpdateDTO.getEmployeeReviewId() != null && employeeService.checkExists(approvalsUpdateDTO.getEmployeeReviewId())){
            approvals.setEmployeeReview(employeeService.getEntityById(approvalsUpdateDTO.getEmployeeReviewId()));
        }

        if (approvalsUpdateDTO.getApprovalDate() != null) {
            approvals.setApprovalDate(approvalsUpdateDTO.getApprovalDate());
        }

        if (approvalsUpdateDTO.getComment() != null && !approvalsUpdateDTO.getComment().isEmpty()) {
            approvals.setComment(approvalsUpdateDTO.getComment());
        }

        if (approvalsUpdateDTO.getStatus() != null) {
            approvals.setStatus(approvalsUpdateDTO.getStatus());
        }

        return approvalsMapper.toDTO(approvalsRepository.save(approvals));
    }


    /**
     * Deletes an existing {@link Approvals} entity by its ID.
     *
     * @param Id the ID of the approval to delete
     * @throws EntityNotFoundException if no approval is found with the given ID
     */
    @Transactional
    @Override
    public void delete(Integer Id) {
        log.info("Delete Approvals: {}", Id);
        if(checkExistence(Id)){
            approvalsRepository.deleteById(Id);
        }else {
            throw new EntityNotFoundException("Approvals not found with id: " + Id);
        }
    }


    /**
     * Checks whether an {@link Approvals} entity exists with the specified ID.
     *
     * @param Id the ID of the approval to check
     * @return true if the approval exists, false otherwise
     */
    @Transactional(readOnly = true)
    @Override
    public Boolean checkExistence(Integer Id) {
        log.info("checkExistence with Id: {}", Id);
        return approvalsRepository.existsById(Id);
    }


    /**
     * Retrieves an {@link ApprovalsDTO} by its unique ID.
     *
     * @param id the ID of the approval to retrieve
     * @return the corresponding {@link ApprovalsDTO}
     * @throws EntityNotFoundException if no approval is found with the given ID
     */
    @Transactional(readOnly = true)
    @Override
    public ApprovalsDTO getById(Integer id) {
        log.info("ApprovalsDTO: {}", id);
        return approvalsMapper.toDTO(getEntityById(id));
    }


    /**
     * Retrieves an {@link Approvals} entity by its unique ID.
     *
     * @param id the ID of the approval to retrieve
     * @return the corresponding {@link Approvals} entity
     * @throws EntityNotFoundException if no approval is found with the given ID
     */
    @Transactional(readOnly = true)
    @Override
    public Approvals getEntityById(Integer id) {
        log.info("getEntityById: {}", id);
        return approvalsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No approvals found with id: " + id));
    }

    /**
     * Filters the list of {@link Approvals} entities using the given {@link ApprovalsFilter},
     * returning paginated {@link ApprovalsDTO} results.
     *
     * @param approvalsFilter the filter criteria
     * @param page            the zero-based page index
     * @param size            the number of records per page
     * @return a list of {@link ApprovalsDTO} matching the filter
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<ApprovalsDTO> filter(ApprovalsFilter approvalsFilter, int page, int size) {
        log.info("filter: {}", approvalsFilter);

        Pageable pageable = PageRequest.of(page, size);

        Specification<Approvals> approvalsSpecification = ApprovalsSpecifications.filter(approvalsFilter);

        Page<Approvals> approvals = approvalsRepository.findAll(approvalsSpecification, pageable);

        return approvalsMapper.toApprovalsPageDTO(approvals);
    }

    /**
     * Filters {@link Approvals} entities by approval date range.
     *
     * @param fromDate the start of the approval date range (inclusive)
     * @param toDate   the end of the approval date range (inclusive)
     * @param page     the zero-based page index
     * @param size     the number of records per page
     * @return a list of {@link ApprovalsDTO} within the specified date range
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<ApprovalsDTO> filterByApprovalDateRange(LocalDateTime fromDate, LocalDateTime toDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<Approvals> spec = ApprovalsSpecifications.filterByApprovalDateRange(fromDate, toDate);

        Page<Approvals> approvalsPageDTO = approvalsRepository.findAll(spec, pageable);

        return approvalsMapper.toApprovalsPageDTO(approvalsPageDTO);
    }
}
