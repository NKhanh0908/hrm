package com.project.hrm.employee.service.impl;

import com.project.hrm.auth.service.AccountService;
import com.project.hrm.common.redis.RedisKeys;
import com.project.hrm.common.response.PageDTO;
import com.project.hrm.common.service.RedisService;
import com.project.hrm.department.entity.Role;
import com.project.hrm.department.service.RoleService;
import com.project.hrm.employee.dto.employeeDTO.EmployeeCreateDTO;
import com.project.hrm.employee.dto.employeeDTO.EmployeeDTO;
import com.project.hrm.employee.dto.employeeDTO.EmployeeFilter;
import com.project.hrm.employee.dto.employeeDTO.EmployeeUpdateDTO;
import com.project.hrm.employee.entity.Employees;
import com.project.hrm.common.exceptions.CustomException;
import com.project.hrm.common.exceptions.Error;
import com.project.hrm.employee.mapper.EmployeeMapper;
import com.project.hrm.employee.repository.EmployeeRepository;
import com.project.hrm.employee.service.EmployeeService;
import com.project.hrm.common.service.FileService;
import com.project.hrm.employee.specification.EmployeeSpecification;
import com.project.hrm.common.utils.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final FileService imageEmployeeService;
    //private final RedisService redisService;
    private final RoleService roleService;
    private final AccountService accountService;

    private final EmployeeMapper employeeMapper;


    /**
     * Filters employees based on the given {@link EmployeeFilter} conditions with
     * pagination.
     *
     * @param employeeFilter the filter conditions to apply
     * @param page           the page number (0-based)
     * @param size           the size of each page
     * @return list of matching {@link EmployeeDTO}
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<EmployeeDTO> filter(EmployeeFilter employeeFilter, int page, int size) {
        log.info("Filter EmployeeDTO");

        String cacheKey = String.format("%s:page:%d:size:%d:filter:%s",
                RedisKeys.EMPLOYEES_LIST, page, size, employeeFilter.toString());
//        PageDTO<EmployeeDTO> cache = redisService.get(cacheKey, PageDTO.class);
//        if (cache != null) {
//            log.info("Retrieved employee from cache: {}", cacheKey);
//            return cache;
//        }

        Specification<Employees> spec = EmployeeSpecification.filterEmployee(employeeFilter);
        Pageable pageable = PageRequest.of(page, size);

        PageDTO<EmployeeDTO> result = employeeMapper.toEmployeePageDTO(employeeRepository.findAll(spec, pageable));

        //redisService.set(cacheKey, result, Duration.ofMinutes(10));

        return result;
    }

    @Override
    public Map<Integer, Employees> getBatchEmployeeForPayPeriod(List<Integer> employeeIds) {
        log.info("Get batch employee for pay period by {} employees: {}", employeeIds.size(), employeeIds);
        Map<Integer, Employees> employeesMap = new HashMap<>();

        List<Object[]> batchEmployees = employeeRepository.getBatchEmployeeForPayPeriod(employeeIds);
        log.info("Found {} employees from repository: {}", batchEmployees.size(), batchEmployees);

        for (Object[] batchEmployee : batchEmployees) {
            Integer employeeId = (Integer) batchEmployee[0];
            Employees employee = (Employees) batchEmployee[1];
            employeesMap.put(employeeId, employee);
            log.debug("Added employee to map: ID = {}, Employee = {}", employeeId, employee);
        }

        log.info("Returning employeesMap with {} entries", employeesMap.size());
        return employeesMap;
    }

    /**
     * Retrieves all active employee IDs.
     *
     * @return a list of active employee IDs
     */
    @Transactional(readOnly = true)
    @Override
    public List<Integer> getAllActiveEmployeeIds() {
        log.info("Get all active employee ids");
        return employeeRepository.getAllActiveEmployeeIds();
    }

    /**
     * Retrieves all employees associated with a specific department ID.
     * @param departmentId the ID of the department to filter employees by
     * @return a list of {@link Employees} entities belonging to the specified department
     */
    @Transactional(readOnly = true)
    @Override
    public List<Employees> getAllEmployeesByDepartmentId(Integer departmentId) {
        log.info("Get all employees by department id: {}", departmentId);

        return employeeRepository.findByDepartmentId(departmentId);
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
     * Get the currently authenticated employee's information.
     *
     * @return the DTO representation of the current logged-in employee
     */
    @Override
    public EmployeeDTO getCurrentEmployee() {
        return employeeMapper.toEmployeeDTO(accountService.getPrincipal());
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
     * Retrieves an employee who is currently marked as active.
     * The method delegates to {@code employeeRepository.findEmployeeIsActive}
     * which must return an {@link Employees} entity with status = ACTIVE.
     *
     * @param employeeId the ID of the employee to look up
     * @return the active {@link Employees} entity, or {@code null} if no active record exists
     */
    @Transactional(readOnly = true)
    @Override
    public Employees getEmployeeIsActive(Integer employeeId) {
        log.info("Fetching active employee by ID: {}", employeeId);

        return employeeRepository.findEmployeeIsActive(employeeId);
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
        if(employeeCreateDTO.getRoleId()!=null){
            Role role = roleService.getEntityById(employeeCreateDTO.getRoleId());
            employee.setRole(role);
        }

        employee.setId(IdGenerator.getGenerationId());

        if(employeeCreateDTO.getImage()!= null){
            employee.setImage(imageEmployeeService.saveImage(employeeCreateDTO.getImage()));
        }

        //redisService.deletePattern("employees:list:*");

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

        Employees employee = employeeMapper.toEntity(getDTOById(employeeUpdateDTO.getId()));

        if (employeeUpdateDTO.getFirstName() != null)
            employee.setFirstName(employeeUpdateDTO.getFirstName());

        if (employeeUpdateDTO.getLastName() != null)
            employee.setLastName(employeeUpdateDTO.getLastName());

        if (employeeUpdateDTO.getEmail() != null)
            employee.setEmail(employeeUpdateDTO.getEmail());

        if (employeeUpdateDTO.getPhone() != null)
            employee.setPhone(employeeUpdateDTO.getPhone());

        if (employeeUpdateDTO.getGender() != null)
            employee.setGender(employeeUpdateDTO.getGender());

        if (employeeUpdateDTO.getDateOfBirth() != null)
            employee.setDateOfBirth(employeeUpdateDTO.getDateOfBirth());

        if (employeeUpdateDTO.getCitizenIdentificationCard() != null)
            employee.setCitizenIdentificationCard(employeeUpdateDTO.getCitizenIdentificationCard());

        if (employeeUpdateDTO.getAddress() != null)
            employee.setAddress(employeeUpdateDTO.getAddress());

        if (employeeUpdateDTO.getImage() != null && !employeeUpdateDTO.getImage().isEmpty()){
            employee.setImage(imageEmployeeService.saveImage(employeeUpdateDTO.getImage()));
        }

        if(employeeUpdateDTO.getRoleId() != null){
            Role role = roleService.getEntityById(employeeUpdateDTO.getRoleId());
            employee.setRole(role);
        }

        //redisService.deletePattern("employees:list:*");

        return employeeMapper.toEmployeeDTO(employeeRepository.save(employee));
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

            //redisService.deletePattern("employees:list:*");
        } else {
            throw new CustomException(Error.EMPLOYEE_NOT_FOUND);
        }
    }

}
