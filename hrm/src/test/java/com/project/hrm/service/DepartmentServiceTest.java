package com.project.hrm.service;

import com.project.hrm.department.dto.departmentDTO.DepartmentCreateDTO;
import com.project.hrm.department.dto.departmentDTO.DepartmentDTO;
import com.project.hrm.department.entity.Departments;
import com.project.hrm.common.exceptions.CustomException;
import com.project.hrm.common.exceptions.Error;
import com.project.hrm.department.mapper.DepartmentMapper;
import com.project.hrm.department.repository.DepartmentRepository;
import com.project.hrm.department.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentMapper departmentMapper;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private Departments departments;
    private DepartmentCreateDTO departmentCreateDTO;

    @BeforeEach
    void setUp(){
        departments = new Departments();
        departments.setId(1);
        departments.setDepartmentName("IT department");
        departments.setDescription("Development");
        departments.setAddress("Q1, TpHCM");
        departments.setPhone("0123456789");

        departmentCreateDTO = new DepartmentCreateDTO();
        departmentCreateDTO.setDepartmentName("IT department");
        departmentCreateDTO.setDescription("Development");
        departmentCreateDTO.setAddress("Q1, TpHCM");
        departmentCreateDTO.setPhone("0123456789");
    }

    @Test
    void createDepartment_shouldSaveDepartmentWhenValid(){
        // 1. Stub repository.save(...)
        when(departmentRepository.save(any(Departments.class)))
                .thenReturn(departments);

        // 2. Stub mapper.convertCreateToEntity(...)
        when(departmentMapper.convertCreateToEntity(departmentCreateDTO))
                .thenReturn(departments);

        // 3. Stub mapper.toDepartmentDTO(...)
        DepartmentDTO dto = new DepartmentDTO();
        dto.setDepartmentName(departments.getDepartmentName());
        when(departmentMapper.toDepartmentDTO(departments))
                .thenReturn(dto);

        // Act
        DepartmentDTO result = departmentService.create(departmentCreateDTO);

        // Assert
        assertNotNull(result, "Service return DTO not null");
        assertEquals(departments.getDepartmentName(), result.getDepartmentName());

        // Verify
        verify(departmentRepository, times(1)).save(departments);
        verify(departmentMapper).convertCreateToEntity(departmentCreateDTO);
        verify(departmentMapper).toDepartmentDTO(departments);
    }

    @Test
    void findDepartmentEntity_shouldReturnDepartmentEntityWhenFound(){
        when(departmentRepository.findById(departments.getId())).thenReturn(Optional.of(departments));

        Departments result = departmentService.getEntityById(1);

        assertNotNull(result);
        assertEquals(departments.getId(), result.getId());
    }

    @Test
    void findDepartmentEntity_shouldReturnDepartmentEntityWhenNotFound(){
        when(departmentRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        CustomException ex = assertThrows(
                CustomException.class,
                () -> departmentService.getEntityById(999),
                "Id not found, should throws CustomException"
        );

        assertEquals(
                Collections.singletonList(Error.DEPARTMENT_NOT_FOUND),
                ex.getErrors(),
                "Return error DEPARTMENT_NOT_FOUND"
        );

        verify(departmentRepository, times(1)).findById(999);
    }



}
