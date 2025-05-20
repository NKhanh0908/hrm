package com.project.hrm.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.employeeDTO.EmployeeDTO;
import com.project.hrm.dto.employeeDTO.EmployeeFilter;
import com.project.hrm.dto.salaryDTO.SalaryCreateDTO;
import com.project.hrm.dto.salaryDTO.SalaryDTO;
import com.project.hrm.dto.salaryDTO.SalaryUpdateDTO;
import com.project.hrm.services.SalaryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/salary")
@RequiredArgsConstructor
@Tag(name = "Salary Controller", description = "Salary manager")
public class SalaryController {
    private final SalaryService salaryService;

    @PostMapping
    public ResponseEntity<APIResponse<SalaryDTO>> create(@RequestBody SalaryCreateDTO SalaryCreateDTO) {
        //TODO: process POST request
        SalaryDTO salaryDTO = salaryService.create(SalaryCreateDTO);

        return ResponseEntity.ok(new APIResponse<>(true, "Create Salary successfully", salaryDTO));
    }

    @PutMapping()
    public ResponseEntity<APIResponse<SalaryDTO>> update(@RequestBody SalaryUpdateDTO salaryUpdateDTO) {
        SalaryDTO salaryDTO = salaryService.update(salaryUpdateDTO);

        return ResponseEntity.ok(new APIResponse<>(true, "Update Salary successfully", salaryDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<SalaryDTO>> getById(@PathVariable Integer id) {
        SalaryDTO salaryDTO = salaryService.getById(id);

        return ResponseEntity.ok(new APIResponse<>(true, "Get By Id Salary successfully", salaryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id) {
        salaryService.delete(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Salary deleted successfully", null));
    }

    @GetMapping("/check-exists/{id}")
    public ResponseEntity<APIResponse<Boolean>> checkExists(@PathVariable Integer id) {
        Boolean exists = salaryService.checkExists(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Check completed", exists));
    }
    
    @GetMapping("/getAll")
    public ResponseEntity<APIResponse<List<SalaryDTO>>> getAll() {

        List<SalaryDTO> results = salaryService.getAll();
        return ResponseEntity.ok(new APIResponse<>(true, "Salary Get All successfully", results));
    }
    
    
}
