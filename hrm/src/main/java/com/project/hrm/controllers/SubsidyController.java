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
import com.project.hrm.dto.salaryDTO.SubsidyCreateDTO;
import com.project.hrm.dto.salaryDTO.SubsidyDTO;
import com.project.hrm.dto.salaryDTO.SubsidyUpdateDTO;
import com.project.hrm.services.SubsidyService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/subsidy")
@RequiredArgsConstructor
@Tag(name = "Subsidy Controller", description = "Subsidy manager")
public class SubsidyController {
    private final SubsidyService subsidyService;

    @PostMapping
    public ResponseEntity<APIResponse<SubsidyDTO>> create(@RequestBody SubsidyCreateDTO subsidyCreateDTO,
            HttpServletRequest request) {
        // TODO: process POST request
        SubsidyDTO subsidyDTO = subsidyService.create(subsidyCreateDTO);

        return ResponseEntity
                .ok(new APIResponse<>(true, "Create Subsidy successfully", subsidyDTO, null, request.getRequestURI()));
    }

    @PutMapping()
    public ResponseEntity<APIResponse<SubsidyDTO>> update(@RequestBody SubsidyUpdateDTO subsidyUpdateDTO,
            HttpServletRequest request) {
        SubsidyDTO subsidyDTO = subsidyService.update(subsidyUpdateDTO);

        return ResponseEntity
                .ok(new APIResponse<>(true, "Update Subsidy successfully", subsidyDTO, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<SubsidyDTO>> getById(@PathVariable Integer id, HttpServletRequest request) {
        SubsidyDTO subsidyDTO = subsidyService.getById(id);

        return ResponseEntity.ok(
                new APIResponse<>(true, "Get By Id Subsidy successfully", subsidyDTO, null, request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id, HttpServletRequest request) {
        subsidyService.delete(id);
        return ResponseEntity
                .ok(new APIResponse<>(true, "Subsidy deleted successfully", null, null, request.getRequestURI()));
    }

    @GetMapping("/check-exists/{id}")
    public ResponseEntity<APIResponse<Boolean>> checkExists(@PathVariable Integer id, HttpServletRequest request) {
        Boolean exists = subsidyService.checkExists(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Check completed", exists, null, request.getRequestURI()));
    }

    @GetMapping("/getAll")
    public ResponseEntity<APIResponse<List<SubsidyDTO>>> getAll(HttpServletRequest request) {

        List<SubsidyDTO> results = subsidyService.getAll();
        return ResponseEntity
                .ok(new APIResponse<>(true, "Subsidy Get All successfully", results, null, request.getRequestURI()));
    }

}
