package com.project.hrm.services.impl;

import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementFilter;
import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementsCreateDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementsDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementsUpdateDTO;
import com.project.hrm.entities.Departments;
import com.project.hrm.entities.Employees;
import com.project.hrm.entities.RecruitmentRequirements;
import com.project.hrm.mapper.RecruitmentRequirementsMapper;
import com.project.hrm.repositories.RecruitmentRequirementsRepository;
import com.project.hrm.services.DepartmentService;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.services.RecruitmentRequirementService;
import com.project.hrm.specifications.RecruitmentRequirementsSpecification;
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
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class RecruitmentRequirementServiceImpl implements RecruitmentRequirementService {
    private final RecruitmentRequirementsRepository recruitmentRequirementsRepository;
    private final RecruitmentRequirementsMapper recruitmentRequirementsMapper;
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    @Transactional(readOnly = true)
    @Override
    public List<RecruitmentRequirementsDTO> filterRecruitmentRequirements(RecruitmentRequirementFilter recruitmentRequirementFilter, int page, int size) {
        Specification<RecruitmentRequirements> recruitmentRequirementsSpecification
                = RecruitmentRequirementsSpecification.filter(recruitmentRequirementFilter);

        Pageable pageable = PageRequest.of(page, size);

        Page<RecruitmentRequirements> recruitmentRequirementsPage
                = recruitmentRequirementsRepository.findAll(recruitmentRequirementsSpecification, pageable);

        return recruitmentRequirementsMapper
                .toPageEntityToPageDTO(recruitmentRequirementsPage);
    }

    @Transactional(readOnly = true)
    @Override
    public RecruitmentRequirements getEntityById(Integer id) {
        log.info("Find RecruitmentRequirements by id: {}", id);

        return recruitmentRequirementsRepository.findById(id)
                .orElseThrow(() -> {
                    String message = "Find RecruitmentRequirements with id " + id + " not found";

                    log.error(message);

                    return new RuntimeException(message);
                });
    }

    @Transactional(readOnly = true)
    @Override
    public RecruitmentRequirementsDTO getDTOById(Integer id) {
        log.info("Find RecruitmentRequirementsDTO by id: {}", id);

        RecruitmentRequirements recruitmentRequirements
                = recruitmentRequirementsRepository.findById(id)
                .orElseThrow(() -> {
                    String message = "Find RecruitmentRequirementsDTO with id " + id + " not found";

                    log.error(message);

                    return new RuntimeException(message);
                });

        return recruitmentRequirementsMapper
                .toDTO(recruitmentRequirements);
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean checkExists(Integer requirementId) {
        return null;
    }

    @Transactional
    @Override
    public RecruitmentRequirementsDTO create(RecruitmentRequirementsCreateDTO recruitmentRequirementsCreateDTO) {
        log.info("Create RecruitmentRequirements");

        Departments departments = departmentService.getEntityById(recruitmentRequirementsCreateDTO.getDepartmentId());

        Employees employees = employeeService.getEntityById(recruitmentRequirementsCreateDTO.getCreatedBy());

        RecruitmentRequirements recruitmentRequirements
                = recruitmentRequirementsMapper.convertCreateDTOtoEntity(recruitmentRequirementsCreateDTO, departments, employees);

        recruitmentRequirements.setId(getGenerationId());
        recruitmentRequirements.setDateRequired(LocalDateTime.now());

        return recruitmentRequirementsMapper.toDTO(
                recruitmentRequirementsRepository.save(recruitmentRequirements)
        );
    }

    @Transactional
    @Override
    public RecruitmentRequirementsDTO update(RecruitmentRequirementsUpdateDTO recruitmentRequirementsUpdateDTO) {
        log.info("Update RecruitmentRequirements");

        RecruitmentRequirements entity = getEntityById(recruitmentRequirementsUpdateDTO.getId());

        if (recruitmentRequirementsUpdateDTO.getDescription() != null) {
            entity.setDescription(recruitmentRequirementsUpdateDTO.getDescription());
        }

        if (recruitmentRequirementsUpdateDTO.getPositions() != null) {
            entity.setPositions(recruitmentRequirementsUpdateDTO.getPositions());
        }

        if (recruitmentRequirementsUpdateDTO.getQuantity() != null) {
            entity.setQuantity(recruitmentRequirementsUpdateDTO.getQuantity());
        }

        if (recruitmentRequirementsUpdateDTO.getExpectedSalary() != null) {
            entity.setExpectedSalary(recruitmentRequirementsUpdateDTO.getExpectedSalary());
        }

        if (recruitmentRequirementsUpdateDTO.getStatus() != null) {
            entity.setStatus(recruitmentRequirementsUpdateDTO.getStatus());
        }

        if (recruitmentRequirementsUpdateDTO.getDepartmentId() != null) {
            Departments department = departmentService.getEntityById(recruitmentRequirementsUpdateDTO.getDepartmentId());
            entity.setDepartments(department);
        }

        if (recruitmentRequirementsUpdateDTO.getCreatedBy() != null) {
            Employees employee = employeeService.getEntityById(recruitmentRequirementsUpdateDTO.getCreatedBy());
            entity.setEmployees(employee);
        }

        return recruitmentRequirementsMapper.toDTO(
                recruitmentRequirementsRepository.save(entity)
        );
    }


    @Transactional
    @Override
    public void delete(Integer requirementId) {
        log.info("Delete RecruitmentRequirements");

        RecruitmentRequirements recruitmentRequirements
                = getEntityById(requirementId);

        recruitmentRequirementsRepository.delete(recruitmentRequirements);
    }

    private Integer getGenerationId(){
        UUID uuid = UUID.randomUUID();

        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
