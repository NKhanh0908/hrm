package com.project.hrm.services.impl;

import com.project.hrm.dto.departmentDTO.DepartmentCreateDTO;
import com.project.hrm.dto.departmentDTO.DepartmentDTO;
import com.project.hrm.dto.departmentDTO.DepartmentFilter;
import com.project.hrm.dto.departmentDTO.DepartmentUpdateDTO;
import com.project.hrm.entities.Departments;
import com.project.hrm.entities.Employees;
import com.project.hrm.mapper.DepartmentMapper;
import com.project.hrm.repositories.DepartmentRepository;
import com.project.hrm.repositories.EmployeeRepository;
import com.project.hrm.services.DepartmentService;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.specifications.DepartmentSpecification;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final EmployeeRepository employeeRepository;

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
        return departmentRepository.findById(id)
                .orElseThrow();
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
    public DepartmentDTO getDTOById(Integer id) {
        return departmentMapper.toDepartmentDTO(departmentRepository.findById(id)
                .orElseThrow());
    }

    /**
     * Creates a new department based on the information provided in {@link DepartmentCreateDTO}.
     * This method maps the DTO to an entity, generates a new ID, and persists it to the database.
     *
     * @param departmentCreateDTO the DTO containing the details of the department to be created
     * @return a {@link DepartmentDTO} representing the newly created department
     */
    @Override
    public DepartmentDTO create(DepartmentCreateDTO departmentCreateDTO) {
        Departments departments = new Departments();

        departments = departmentMapper.convertCreateToEntity(departmentCreateDTO);

        departments.setId(getGenerationId());

        return departmentMapper.toDepartmentDTO(
                departmentRepository.save(departments)
        );
    }

    /**
     * Updates an existing department based on the information provided in {@link DepartmentUpdateDTO}.
     * This method maps the DTO to the entity, applies the changes, and persists the updated entity.
     *
     * @param departmentUpdateDTO the DTO containing the updated department details
     * @return a {@link DepartmentDTO} representing the department after the update
     */
    @Override
    public DepartmentDTO update(DepartmentUpdateDTO departmentUpdateDTO) {
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
            department.setAddress(departmentUpdateDTO.getAddress());
        }

        if(departmentUpdateDTO.getDescription()!=null){
            department.setDescription(departmentUpdateDTO.getDescription());
        }

        return departmentMapper.toDepartmentDTO(departmentRepository.save(department));
    }

    /**
     * Filters the list of departments based on department name, address, and email by {@link DepartmentFilter}.
     * This method supports pagination and executes within a read-only transaction.
     * @param departmentFilter include
     *          departmentName the (possibly partial) name of the department to filter by
     *          address        the address of the department (optional)
     *          email          the email of the department (optional)
     * @param page           the zero-based page index
     * @param size           the number of records per page
     * @return a paginated result containing {@link DepartmentDTO} instances that match the filter criteria
     */
    @Transactional(readOnly = true)
    @Override
    public Page<DepartmentDTO> filterDepartment(DepartmentFilter departmentFilter, int page, int size) {
        Specification<Departments> departmentsSpecification = DepartmentSpecification.filterDepartment(departmentFilter);

        Pageable pageable = PageRequest.of(page, size);

        return departmentMapper.convertPageEntityToPageDTO(
                departmentRepository.findAll(departmentsSpecification, pageable)
        );
    }

    /**
     * Assigns an employee as the head of a department.
     *
     * @param departmentId the ID of the department
     * @param employeeId   the ID of the employee to be assigned as head
     * @return a {@link DepartmentDTO} representing the department with the updated head assignment
     */
    @Transactional
    @Override
    public DepartmentDTO appointmentOfDean(Integer departmentId, Integer employeeId) {
        Employees employee = employeeRepository.findById(employeeId).orElseThrow();

        return departmentMapper.toDepartmentDTO(
                departmentRepository.updateDean(employeeId, departmentId)
        );
    }


    private Integer getGenerationId(){
        UUID uuid = UUID.randomUUID();

        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
