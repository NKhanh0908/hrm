package com.project.hrm.services.impl;


import com.project.hrm.dto.departmentDTO.DepartmentDTO;
import com.project.hrm.dto.employeeDTO.EmployeeCreateDTO;
import com.project.hrm.dto.employeeDTO.EmployeeDTO;
import com.project.hrm.dto.employeeDTO.EmployeeFilter;
import com.project.hrm.dto.employeeDTO.EmployeeUpdateDTO;
import com.project.hrm.entities.Employees;
import com.project.hrm.mapper.DepartmentMapper;
import com.project.hrm.mapper.EmployeeMapper;
import com.project.hrm.repositories.EmployeeRepository;
import com.project.hrm.services.DepartmentService;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.specifications.EmployeeSpecification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final @Lazy DepartmentService departmentService;
    private final EmployeeRepository employeeRepository;
    private final DepartmentMapper departmentMapper;

    @Transactional(readOnly = true)
    @Override
    public List<EmployeeDTO> filter(EmployeeFilter employeeFilter, int page, int size) {
        log.info("Filter EmployeeDTO");

        Specification<Employees> spec = EmployeeSpecification.filterEmployee(employeeFilter);

        Pageable pageable = PageRequest.of(page, size);

        return employeeMapper.pageToEmployeeDTOList(employeeRepository.findAll(spec, pageable));
    }

    @Transactional(readOnly = true)
    @Override
    public Employees getEntityById(Integer id){
        log.info("Find Employee entity by id: {}", id);

        return employeeRepository.findById(id)
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    @Override
    public EmployeeDTO getDTOById(Integer id) {
        log.info("Find Employee DTO by id: {}", id);

        return employeeMapper.toEmployeeDTO(
                employeeRepository.findById(id)
                       .orElseThrow(() -> new RuntimeException("Employee not found"))
        );
    }



    @Transactional(readOnly = true)
    @Override
    public Boolean checkExists(Integer employeeId) {
        log.info("Check exists Employee by id: {}", employeeId);

        return employeeRepository.findById(employeeId).isPresent();
    }

    @Transactional
    @Override
    public EmployeeDTO create(EmployeeCreateDTO employeeCreateDTO) {
        log.info("Create Employee");

        DepartmentDTO departmentDTO = departmentService.getById(employeeCreateDTO.getDepartmentId());

        Employees employee = employeeMapper.employeeCreateToEmployee(employeeCreateDTO, departmentDTO);

        employee.setId(getGenerationId());

        return employeeMapper.toEmployeeDTO(employeeRepository.save(employee));
    }


    @Transactional
    @Override
    public EmployeeDTO update(EmployeeUpdateDTO employeeUpdateDTO) {
        log.info("Update Employee");

        Employees employees = employeeMapper.toEntity(getDTOById(employeeUpdateDTO.getId()));

        if (employeeUpdateDTO.getFirstName() != null) employees.setFirstName(employeeUpdateDTO.getFirstName());

        if (employeeUpdateDTO.getLastName()!= null) employees.setLastName(employeeUpdateDTO.getLastName());

        if (employeeUpdateDTO.getDepartmentId()!= null) {
            DepartmentDTO departmentDTO = departmentService.getById(employeeUpdateDTO.getDepartmentId());

            employees.setDepartment(departmentMapper.toDepartment(
                    departmentService.getById(employeeUpdateDTO.getDepartmentId())
            ));
        }

        if(employeeUpdateDTO.getEmail() != null) employees.setEmail(employeeUpdateDTO.getEmail());

        if(employeeUpdateDTO.getPhone()!= null) employees.setPhone(employeeUpdateDTO.getPhone());

        if(employeeUpdateDTO.getGender() != null) employees.setGender(employeeUpdateDTO.getGender());

        if(employeeUpdateDTO.getDateOfBirth() != null) employees.setDateOfBirth(employeeUpdateDTO.getDateOfBirth());

        if((employeeUpdateDTO.getCitizenIdentificationCard()) != null) employees.setCitizenIdentificationCard(employees.getCitizenIdentificationCard());

        if(employeeUpdateDTO.getAddress() != null) employees.setAddress(employeeUpdateDTO.getAddress());

        return employeeMapper.toEmployeeDTO(employeeRepository.save(employees));
    }

    @Transactional
    @Override
    public void delete(Integer employeeId){
        log.info("Delete employee by id: {}", employeeId);

        if(checkExists(employeeId)) employeeRepository.deleteById(employeeId);
        else throw new RuntimeException("Employee not found");
    }


    private Integer getGenerationId(){
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
