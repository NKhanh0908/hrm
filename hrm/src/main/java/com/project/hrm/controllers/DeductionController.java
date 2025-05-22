package com.project.hrm.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.salaryDTO.DeductionCreateDTO;
import com.project.hrm.dto.salaryDTO.DeductionDTO;
import com.project.hrm.dto.salaryDTO.DeductionUpdateDTO;
import com.project.hrm.services.DeductionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/deduction")
@RequiredArgsConstructor
@Tag(name = "Deduction Controller", description = "Deduction manager")
public class DeductionController {
    private final DeductionService deductionService;

    @PostMapping
    public ResponseEntity<APIResponse<DeductionDTO>> create(@RequestBody DeductionCreateDTO deductionCreateDTO,
            HttpServletRequest request) {
        // TODO: process POST request
        DeductionDTO deductionDTO = deductionService.create(deductionCreateDTO);

        return ResponseEntity.ok(
                new APIResponse<>(true, "Create Deduction successfully", deductionDTO, null, request.getRequestURI()));
    }

    @PutMapping()
    public ResponseEntity<APIResponse<DeductionDTO>> update(@RequestBody DeductionUpdateDTO deductionUpdateDTO,
            HttpServletRequest request) {
        DeductionDTO deductionDTO = deductionService.update(deductionUpdateDTO);

        return ResponseEntity.ok(
                new APIResponse<>(true, "Update Deduction successfully", deductionDTO, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<DeductionDTO>> getById(@PathVariable Integer id, HttpServletRequest request) {
        DeductionDTO deductionDTO = deductionService.getById(id);

        return ResponseEntity.ok(new APIResponse<>(true, "Get By Id Deduction successfully", deductionDTO, null,
                request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id, HttpServletRequest request) {
        deductionService.delete(id);

        return ResponseEntity
                .ok(new APIResponse<>(true, "Deduction deleted successfully", null, null, request.getRequestURI()));
    }

    @GetMapping("/check-exists/{id}")
    public ResponseEntity<APIResponse<Boolean>> checkExists(@PathVariable Integer id, HttpServletRequest request) {
        Boolean exists = deductionService.checkExists(id);

        return ResponseEntity.ok(new APIResponse<>(true, "Check completed", exists, null, request.getRequestURI()));
    }

    @GetMapping("/getAll")
    public ResponseEntity<APIResponse<List<DeductionDTO>>> getAll(HttpServletRequest request) {

        List<DeductionDTO> results = deductionService.getAll();
        return ResponseEntity
                .ok(new APIResponse<>(true, "Deduction Get All successfully", results, null, request.getRequestURI()));
    }
}
