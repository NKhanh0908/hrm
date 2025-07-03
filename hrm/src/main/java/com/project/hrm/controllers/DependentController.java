package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dependents")
@RequiredArgsConstructor
@Tag(name = "Dependent Controller", description = "Manage employee dependents")
public class DependentController {

    private final DependentService dependentService;

    @PostMapping
    @Operation(
            summary = "Create new dependent",
            description = "Create a new dependent record",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dependent creation data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = DependentCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dependent created successfully", content = @Content(schema = @Schema(implementation = DependentDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<DependentDTO>> create(@RequestBody DependentCreateDTO dto,
                                                            HttpServletRequest request) {
        DependentDTO result = dependentService.create(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Create Dependent successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update dependent",
            description = "Update information of an existing dependent",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dependent update data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = DependentUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dependent updated successfully", content = @Content(schema = @Schema(implementation = DependentDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<DependentDTO>> update(@RequestBody DependentUpdateDTO dto,
                                                            HttpServletRequest request) {
        DependentDTO result = dependentService.update(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Update Dependent successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get dependent by ID",
            parameters = {
                    @Parameter(name = "id", description = "ID of the dependent", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dependent found", content = @Content(schema = @Schema(implementation = DependentDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<DependentDTO>> getById(@PathVariable Integer id,
                                                             HttpServletRequest request) {
        DependentDTO result = dependentService.getById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Get Dependent successfully", result, null, request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete dependent by ID",
            parameters = {
                    @Parameter(name = "id", description = "ID of the dependent", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dependent deleted successfully")
            }
    )
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id,
                                                    HttpServletRequest request) {
        dependentService.delete(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Delete Dependent successfully", null, null, request.getRequestURI()));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(
            summary = "Get all dependents of an employee",
            parameters = {
                    @Parameter(name = "employeeId", description = "Employee ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of dependents", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DependentDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<DependentDTO>>> getByEmployeeId(@PathVariable Integer employeeId,
                                                                           HttpServletRequest request) {
        List<DependentDTO> list = dependentService.getDependentsByEmployeeId(employeeId);
        return ResponseEntity.ok(new APIResponse<>(true, "Get Dependents by Employee ID successfully", list, null, request.getRequestURI()));
    }

//    @GetMapping
//    @Operation(
//            summary = "Get all dependents",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "List of all dependents", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DependentDTO.class))))
//            }
//    )
//    public ResponseEntity<APIResponse<List<DependentDTO>>> getAll(HttpServletRequest request) {
//        List<DependentDTO> list = dependentService.getAllDependents();
//        return ResponseEntity.ok(new APIResponse<>(true, "Get All Dependents successfully", list, null, request.getRequestURI()));
//    }
}
