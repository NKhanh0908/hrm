package com.project.hrm.services.impl;


import com.project.hrm.dto.employeeDTO.EmployeeCreateDTO;
import com.project.hrm.dto.employeeDTO.EmployeeDTO;
import com.project.hrm.dto.employeeDTO.EmployeeFilter;
import com.project.hrm.dto.employeeDTO.EmployeeUpdateDTO;
import com.project.hrm.entities.Employees;
import com.project.hrm.exceptions.CustomException;
import com.project.hrm.exceptions.Error;
import com.project.hrm.mapper.DepartmentMapper;
import com.project.hrm.mapper.EmployeeMapper;
import com.project.hrm.repositories.EmployeeRepository;
import com.project.hrm.services.DepartmentService;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.specifications.EmployeeSpecification;
import com.project.hrm.utils.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final @Lazy DepartmentService departmentService;
    private final EmployeeRepository employeeRepository;
    private final DepartmentMapper departmentMapper;


    /**
     * Filters employees based on the given {@link EmployeeFilter} conditions with pagination.
     *
     * @param employeeFilter the filter conditions to apply
     * @param page           the page number (0-based)
     * @param size           the size of each page
     * @return list of matching {@link EmployeeDTO}
     */
    @Transactional(readOnly = true)
    @Override
    public List<EmployeeDTO> filter(EmployeeFilter employeeFilter, int page, int size) {
        log.info("Filter EmployeeDTO");

        Specification<Employees> spec = EmployeeSpecification.filterEmployee(employeeFilter);
        Pageable pageable = PageRequest.of(page, size);

        return employeeMapper.pageToEmployeeDTOList(employeeRepository.findAll(spec, pageable));
    }

    /**
     * Retrieves an {@link Employees} entity by its ID.
     *
     * @param id the employee ID
     * @return the {@link Employees} entity
     */
    @Transactional(readOnly = true)
    @Override
    public Employees getEntityById(Integer id) {
        log.info("Find Employee entity by id: {}", id);

        return employeeRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.EMPLOYEE_NOT_FOUND));
    }

    /**
     * Retrieves an {@link EmployeeDTO} by its ID.
     *
     * @param id the employee ID
     * @return the corresponding {@link EmployeeDTO}
     * @throws RuntimeException if the employee is not found
     */
    @Transactional(readOnly = true)
    @Override
    public EmployeeDTO getDTOById(Integer id) {
        log.info("Find Employee by id: {}", id);

        return employeeMapper.toEmployeeDTO(getEntityById(id));
    }

    /**
     * Checks if an employee with the given ID exists.
     *
     * @param employeeId the employee ID
     * @return {@code true} if exists, {@code false} otherwise
     */
    @Transactional(readOnly = true)
    @Override
    public Boolean checkExists(Integer employeeId) {
        log.info("Check exists Employee by id: {}", employeeId);

        return employeeRepository.findById(employeeId).isPresent();
    }

    /**
     * Creates a new employee.
     *
     * @param employeeCreateDTO the information used to create the employee
     * @return the created {@link EmployeeDTO}
     */
    @Transactional
    @Override
    public EmployeeDTO create(EmployeeCreateDTO employeeCreateDTO) {
        log.info("Create Employee");

        Employees employee = employeeMapper.employeeCreateToEmployee(employeeCreateDTO);
        employee.setId(IdGenerator.getGenerationId());

        return employeeMapper.toEmployeeDTO(employeeRepository.save(employee));
    }

    /**
     * Updates an existing employee based on provided {@link EmployeeUpdateDTO}.
     *
     * @param employeeUpdateDTO the DTO containing update data
     * @return the updated {@link EmployeeDTO}
     */
    @Transactional
    @Override
    public EmployeeDTO update(EmployeeUpdateDTO employeeUpdateDTO) {
        log.info("Update Employee");

        Employees employees = employeeMapper.toEntity(getDTOById(employeeUpdateDTO.getId()));

        if (employeeUpdateDTO.getFirstName() != null)
            employees.setFirstName(employeeUpdateDTO.getFirstName());

        if (employeeUpdateDTO.getLastName() != null)
            employees.setLastName(employeeUpdateDTO.getLastName());


        if (employeeUpdateDTO.getEmail() != null)
            employees.setEmail(employeeUpdateDTO.getEmail());

        if (employeeUpdateDTO.getPhone() != null)
            employees.setPhone(employeeUpdateDTO.getPhone());

        if (employeeUpdateDTO.getGender() != null)
            employees.setGender(employeeUpdateDTO.getGender());

        if (employeeUpdateDTO.getDateOfBirth() != null)
            employees.setDateOfBirth(employeeUpdateDTO.getDateOfBirth());

        if (employeeUpdateDTO.getCitizenIdentificationCard() != null)
            employees.setCitizenIdentificationCard(employeeUpdateDTO.getCitizenIdentificationCard());

        if (employeeUpdateDTO.getAddress() != null)
            employees.setAddress(employeeUpdateDTO.getAddress());

        return employeeMapper.toEmployeeDTO(employeeRepository.save(employees));
    }

    /**
     * Deletes an employee by their ID.
     *
     * @param employeeId the ID of the employee to delete
     * @throws RuntimeException if employee does not exist
     */
    @Transactional
    @Override
    public void delete(Integer employeeId) {
        log.info("Delete employee by id: {}", employeeId);

        if (checkExists(employeeId)) {
            employeeRepository.deleteById(employeeId);
        } else {
            throw new CustomException(Error.EMPLOYEE_NOT_FOUND);
        }
    }


}
