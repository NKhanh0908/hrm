package com.project.hrm.department.service.impl;

import com.project.hrm.common.response.PageDTO;
import com.project.hrm.department.dto.departmentDTO.DepartmentCreateDTO;
import com.project.hrm.department.dto.departmentDTO.DepartmentDTO;
import com.project.hrm.department.dto.departmentDTO.DepartmentFilter;
import com.project.hrm.department.dto.departmentDTO.DepartmentUpdateDTO;
import com.project.hrm.department.entity.Departments;
import com.project.hrm.common.exceptions.CustomException;
import com.project.hrm.common.exceptions.Error;
import com.project.hrm.department.mapper.DepartmentMapper;
import com.project.hrm.department.repository.DepartmentRepository;
import com.project.hrm.department.service.DepartmentService;
import com.project.hrm.department.specification.DepartmentSpecification;
import com.project.hrm.common.utils.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    private final DepartmentMapper departmentMapper;

    /**
     * Finds a department by its unique identifier.
     *
     * @param id the ID of the department to search for
     * @return the {@link Departments} entity if found;
     *         returns the raw entity for use by other services
     */
    @Transactional(readOnly = true)
    @Override
    public Departments getEntityById(Integer id) {
        log.info("Get entity department by id: {}", id);

        return departmentRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.DEPARTMENT_NOT_FOUND));
    }

    /**
     * Finds a department by employee Id
     *
     * @param employeeId the ID of the department
     * @return the {@link DepartmentDTO} entity if found
     * returns the raw DTO for use by controller required
     */
    @Transactional(readOnly = true)
    @Override
    public DepartmentDTO getDepartmentDTOByEmployeeId(Integer employeeId) {
        log.info("Get department by employee id: {}", employeeId);

        return departmentMapper.toDepartmentDTO(departmentRepository.getDepartmentsByEmployeeId(employeeId));
    }

    /**
     * Finds a department by its unique identifier.
     *
     * @param id the ID of the department to search for
     * @return the {@link DepartmentDTO} entity if found
     * returns the raw DTO for use by controller required
     */
    @Transactional(readOnly = true)
    @Override
    public DepartmentDTO getById(Integer id) {
        log.info("Get department by id: {}", id);

        return departmentMapper.toDepartmentDTO(getEntityById(id));
    }

    /**
     * Creates a new department based on the information provided in {@link DepartmentCreateDTO}.
     * This method maps the DTO to an entity, generates a new ID, and persists it to the database.
     *
     * @param departmentCreateDTO the DTO containing the details of the department to be created
     * @return a {@link DepartmentDTO} representing the newly created department
     */
    @Transactional
    @Override
    public DepartmentDTO create(DepartmentCreateDTO departmentCreateDTO) {
        log.info("Create Department");

        Departments departments = departmentMapper.convertCreateToEntity(departmentCreateDTO);
        departments.setId(IdGenerator.getGenerationId());

        return departmentMapper.toDepartmentDTO(departmentRepository.save(departments));
    }

    /**
     * Updates an existing department based on the information provided in {@link DepartmentUpdateDTO}.
     * This method maps the DTO to the entity, applies the changes, and persists the updated entity.
     *
     * @param departmentUpdateDTO the DTO containing the updated department details
     * @return a {@link DepartmentDTO} representing the department after the update
     */
    @Transactional
    @Override
    public DepartmentDTO update(DepartmentUpdateDTO departmentUpdateDTO) {
        log.info("Update Department");

        Departments department = getEntityById(departmentUpdateDTO.getId());

        if(departmentUpdateDTO.getDepartmentName()!=null){
            department.setDepartmentName(departmentUpdateDTO.getDepartmentName());
        }

        if(departmentUpdateDTO.getPhone()!=null){
            department.setPhone(departmentUpdateDTO.getPhone());
        }

        if(departmentUpdateDTO.getAddress()!=null){
            department.setAddress(departmentUpdateDTO.getAddress());
        }

        if(departmentUpdateDTO.getEmail()!=null){
            department.setEmail(departmentUpdateDTO.getAddress());
        }

        if(departmentUpdateDTO.getDescription()!=null){
            department.setDescription(departmentUpdateDTO.getDescription());
        }

        return departmentMapper.toDepartmentDTO(departmentRepository.save(department));
    }

    /**
     * Filters the list of departments based on department name, address, and email by {@link DepartmentFilter}.
     * This method supports pagination and executes within a read-only transaction.
     *
     * @param departmentFilter include
     *                         departmentName the (possibly partial) name of the department to filter by
     *                         address        the address of the department (optional)
     *                         email          the email of the department (optional)
     * @param page             the zero-based page index
     * @param size             the number of records per page
     * @return a paginated result containing {@link DepartmentDTO} instances that match the filter criteria
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<DepartmentDTO> filter(DepartmentFilter departmentFilter, int page, int size) {
        log.info("Filter Department");

        Specification<Departments> departmentsSpecification = DepartmentSpecification.filterDepartment(departmentFilter);

        Pageable pageable = PageRequest.of(page, size);

        return departmentMapper.toDepartmentPageDTO(
                departmentRepository.findAll(departmentsSpecification, pageable));
    }

}
