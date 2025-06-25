package com.project.hrm.service;

import com.project.hrm.dto.employeeDTO.EmployeeCreateDTO;
import com.project.hrm.dto.employeeDTO.EmployeeDTO;
import com.project.hrm.entities.Employees;
import com.project.hrm.enums.EmployeeStatus;
import com.project.hrm.exceptions.CustomException;
import com.project.hrm.mapper.EmployeeMapper;
import com.project.hrm.repositories.EmployeeRepository;
import com.project.hrm.services.EmployeeService;
import com.project.hrm.services.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employees employees;
    private EmployeeCreateDTO employeeCreateDTO;

    @BeforeEach
    void setUp() {
        employees = new Employees();
        employees.setId(1);
        employees.setFirstName("John");
        employees.setLastName("Smith");
        employees.setEmail("john.smith@gmail.com");
        employees.setPhone("0457812547");
        employees.setGender("Male");
        employees.setDateOfBirth("2002");
        employees.setAddress("Tp HCM");
        employees.setPosition("IT");
        employees.setStatus(EmployeeStatus.SUSPENDED);

        employeeCreateDTO = new EmployeeCreateDTO();
        employeeCreateDTO.setFirstName("John");
        employeeCreateDTO.setLastName("Smith");
        employeeCreateDTO.setEmail("john.smith@gmail.com");
        employeeCreateDTO.setPhone("0457812547");
        employeeCreateDTO.setGender("Male");
        employeeCreateDTO.setDateOfBirth("2002");
        employeeCreateDTO.setAddress("Tp HCM");
        employeeCreateDTO.setPosition("IT");
        employeeCreateDTO.setStatus("SUSPENDED");
        employeeCreateDTO.setImage(null);
    }

    @Test
    void createEmployee_shouldSaveEmployeeWhenValid() {
        EmployeeDTO expectedDto = new EmployeeDTO();
        expectedDto.setFirstName("John");

        when(employeeMapper.employeeCreateToEmployee(employeeCreateDTO)).thenReturn(employees);
        when(employeeRepository.save(any(Employees.class))).thenReturn(employees);
        when(employeeMapper.toEmployeeDTO(employees)).thenReturn(expectedDto);

        EmployeeDTO employeeDTO = employeeService.create(employeeCreateDTO);

        assertNotNull (employeeDTO, "Employee should not be null");
        assertEquals(employeeCreateDTO.getFirstName(), employeeDTO.getFirstName(), "First name should be equal");

        verify(employeeRepository, times(1)).save(employees);
        verify(employeeMapper).employeeCreateToEmployee(employeeCreateDTO);
        verify(employeeMapper).toEmployeeDTO(employees);

    }

    @Test
    void create_ShouldThrowIllegalArgument_WhenDtoNull(){
        assertThrows(NullPointerException.class, () -> employeeService.create(null));
    }

    @Test
    void findEmployeeEntity_shouldReturnEmployeeEntityWhenFound(){
        Employees e = new Employees();
        e.setId(1);

        when(employeeRepository.findById(e.getId())).thenReturn(Optional.of(e));

        Employees emp = employeeService.getEntityById(1);

        assertNotNull(emp, "Employee should not be null");
        assertEquals(e.fullName(), emp.fullName());
    }

    @Test
    void findEmployee_shouldReturnCustomExceptionEmployeeNotFound(){
        assertThrows(CustomException.class,
                ()-> employeeService.getEntityById(999));
    }


}
