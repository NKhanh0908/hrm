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
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/subsidy")
@RequiredArgsConstructor
@Tag(name = "Subsidy Controller", description = "Subsidy manager")
public class SubsidyController {
    private final SubsidyService subsidyService;

    @PostMapping
    public ResponseEntity<APIResponse<SubsidyDTO>> create(@RequestBody SubsidyCreateDTO subsidyCreateDTO) {
        //TODO: process POST request
        SubsidyDTO subsidyDTO = subsidyService.create(subsidyCreateDTO);

        return ResponseEntity.ok(new APIResponse<>(true, "Create Subsidy successfully", subsidyDTO));
    }

    @PutMapping()
    public ResponseEntity<APIResponse<SubsidyDTO>> update(@RequestBody SubsidyUpdateDTO subsidyUpdateDTO) {
        SubsidyDTO subsidyDTO = subsidyService.update(subsidyUpdateDTO);

        return ResponseEntity.ok(new APIResponse<>(true, "Update Subsidy successfully", subsidyDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<SubsidyDTO>> getById(@PathVariable Integer id) {
        SubsidyDTO subsidyDTO = subsidyService.getById(id);

        return ResponseEntity.ok(new APIResponse<>(true, "Get By Id Subsidy successfully", subsidyDTO));
    }

     @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id) {
        subsidyService.delete(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Subsidy deleted successfully", null));
    }

    @GetMapping("/check-exists/{id}")
    public ResponseEntity<APIResponse<Boolean>> checkExists(@PathVariable Integer id) {
        Boolean exists = subsidyService.checkExists(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Check completed", exists));
    }
    
    @GetMapping("/getAll")
    public ResponseEntity<APIResponse<List<SubsidyDTO>>> getAll() {

        List<SubsidyDTO> results = subsidyService.getAll();
        return ResponseEntity.ok(new APIResponse<>(true, "Subsidy Get All successfully", results));
    }

}
