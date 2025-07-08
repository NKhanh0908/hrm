package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.dependentDTO.DependentCreateDTO;
import com.project.hrm.dto.dependentDTO.DependentDTO;
import com.project.hrm.dto.dependentDTO.DependentUpdateDTO;
import com.project.hrm.services.DependentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dependents")
@RequiredArgsConstructor
@Tag(name = "Dependent Controller", description = "Manage dependent records")
public class DependentController {

    private final DependentService dependentService;

    @PostMapping
    @Operation(
            summary = "Create Dependent",
            description = "Create a new dependent record",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Dependent creation data",
                    content = @Content(schema = @Schema(implementation = DependentCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dependent created successfully", content = @Content(schema = @Schema(implementation = DependentDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<DependentDTO>> create(@Valid @RequestBody DependentCreateDTO dto, HttpServletRequest request) {
        DependentDTO result = dependentService.create(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Create dependent successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update Dependent",
            description = "Update an existing dependent record",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Dependent update data",
                    content = @Content(schema = @Schema(implementation = DependentUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dependent updated successfully", content = @Content(schema = @Schema(implementation = DependentDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<DependentDTO>> update(@Valid @RequestBody DependentUpdateDTO dto, HttpServletRequest request) {
        DependentDTO result = dependentService.update(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Update dependent successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Dependent by ID",
            parameters = @Parameter(name = "id", description = "Dependent ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dependent found", content = @Content(schema = @Schema(implementation = DependentDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<DependentDTO>> getById(@PathVariable Integer id, HttpServletRequest request) {
        DependentDTO result = dependentService.getById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Get dependent successfully", result, null, request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Dependent by ID",
            parameters = @Parameter(name = "id", description = "Dependent ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dependent deleted successfully")
            }
    )
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id, HttpServletRequest request) {
        dependentService.delete(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Delete dependent successfully", null, null, request.getRequestURI()));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(
            summary = "Get Dependents by Employee ID",
            parameters = @Parameter(name = "employeeId", description = "Employee ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dependents found for employee", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DependentDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<DependentDTO>>> getDependentsByEmployeeId(@PathVariable Integer employeeId, HttpServletRequest request) {
        List<DependentDTO> result = dependentService.getDependentsByEmployeeId(employeeId);
        return ResponseEntity.ok(new APIResponse<>(true, "Get dependents by employee successfully", result, null, request.getRequestURI()));
    }

    @GetMapping
    @Operation(
            summary = "Get All Dependents",
            description = "Retrieve all dependent records with pagination",
            parameters = {
                    @Parameter(name = "page", description = "Page number"),
                    @Parameter(name = "size", description = "Page size")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "All dependents retrieved", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DependentDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<PageDTO<DependentDTO>>> getAllDependents(@RequestParam(defaultValue = "0") int page,
                                                                               @RequestParam(defaultValue = "10") int size,
                                                                               HttpServletRequest request) {
        PageDTO<DependentDTO> result = dependentService.getAllDependents(page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Get all dependents successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/count/{employeeId}")
    @Operation(
            summary = "Count Dependents by Employee",
            description = "Count the number of dependents for an employee",
            parameters = @Parameter(name = "employeeId", description = "Employee ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dependent count retrieved", content = @Content(schema = @Schema(implementation = Integer.class)))
            }
    )
    public ResponseEntity<APIResponse<Integer>> countDependentsOfEmployee(@PathVariable Integer employeeId, HttpServletRequest request) {
        int result = dependentService.countDependentsOfEmployee(employeeId);
        return ResponseEntity.ok(new APIResponse<>(true, "Count dependents successfully", result, null, request.getRequestURI()));
    }

    @PostMapping("/batch-count")
    @Operation(
            summary = "Get Batch Dependent Count",
            description = "Count dependents for multiple employees",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "List of employee IDs",
                    content = @Content(schema = @Schema(implementation = List.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Batch dependent count retrieved", content = @Content(schema = @Schema(implementation = Map.class)))
            }
    )
    public ResponseEntity<APIResponse<Map<Integer, Integer>>> getDependentCountsForEmployees(@RequestBody List<Integer> employeeIds, HttpServletRequest request) {
        Map<Integer, Integer> result = dependentService.getDependentCountsForEmployees(employeeIds);
        return ResponseEntity.ok(new APIResponse<>(true, "Get batch dependent count successfully", result, null, request.getRequestURI()));
    }
}