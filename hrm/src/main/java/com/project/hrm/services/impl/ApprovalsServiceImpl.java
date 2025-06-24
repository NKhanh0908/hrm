package com.project.hrm.services.impl;

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

    @Override
    public Boolean checkExistence(Integer Id) {
        log.info("checkExistence with Id: {}", Id);
        return approvalsRepository.existsById(Id);
    }

    @Transactional(readOnly = true)
    @Override
    public ApprovalsDTO getById(Integer id) {
        log.info("ApprovalsDTO: {}", id);
        return approvalsMapper.toDTO(getEntityById(id));
    }

    @Transactional(readOnly = true)
    @Override
    public Approvals getEntityById(Integer id) {
        log.info("getEntityById: {}", id);
        return approvalsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No approvals found with id: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ApprovalsDTO> filter(ApprovalsFilter approvalsFilter, int page, int size) {
        log.info("filter: {}", approvalsFilter);

        Pageable pageable = PageRequest.of(page, size);

        Specification<Approvals> approvalsSpecification = ApprovalsSpecifications.filter(approvalsFilter);

        return approvalsRepository.findAll(approvalsSpecification, pageable)
                .getContent()
                .stream()
                .map(approvalsMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ApprovalsDTO> filterByApprovalDateRange(LocalDateTime fromDate, LocalDateTime toDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Approvals> spec = ApprovalsSpecifications.filterByApprovalDateRange(fromDate, toDate);

        return approvalsRepository.findAll(spec, pageable)
                .getContent()
                .stream()
                .map(approvalsMapper::toDTO)
                .collect(Collectors.toList());
    }
}
