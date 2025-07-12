package com.project.hrm.systemRegulation.controller;

import com.project.hrm.common.response.APIResponse;
import com.project.hrm.systemRegulation.dto.systemRegulationDTO.SystemRegulationCreateDTO;
import com.project.hrm.systemRegulation.dto.systemRegulationDTO.SystemRegulationDTO;
import com.project.hrm.systemRegulation.dto.systemRegulationDTO.SystemRegulationUpdateDTO;
import com.project.hrm.systemRegulation.enums.SystemRegulationKey;
import com.project.hrm.systemRegulation.service.SystemRegulationService;
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

@RestController
@RequestMapping("/system-regulations")
@RequiredArgsConstructor
@Tag(name = "System Regulation Controller", description = "Manage system regulation records")
public class SystemRegulationController {

    private final SystemRegulationService systemRegulationService;

    @GetMapping
    @Operation(
            summary = "Get All System Regulations",
            description = "Retrieve all system regulation records",
            responses = {
                    @ApiResponse(responseCode = "200", description = "System regulations retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = SystemRegulationDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<SystemRegulationDTO>>> getAllSystemRegulations(HttpServletRequest request) {
        List<SystemRegulationDTO> result = systemRegulationService.getAllSystemRegulations();
        return ResponseEntity.ok(new APIResponse<>(true, "Get all system regulations successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/value")
    @Operation(
            summary = "Get System Regulation Value by Key",
            description = "Retrieve the value of a system regulation by its key",
            parameters = @Parameter(name = "key", description = "System Regulation Key", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "System regulation value found", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    public ResponseEntity<APIResponse<String>> getValue(@RequestParam SystemRegulationKey key, HttpServletRequest request) {
        String result = systemRegulationService.getValue(key);
        return ResponseEntity.ok(new APIResponse<>(true, "Get system regulation value successfully", result, null, request.getRequestURI()));
    }

    @PutMapping("/value")
    @Operation(
            summary = "Set System Regulation Value",
            description = "Set the value for a system regulation by its key",
            parameters = @Parameter(name = "key", description = "System Regulation Key", required = true),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "New value for the system regulation",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "System regulation value set successfully")
            }
    )
    public ResponseEntity<APIResponse<Void>> setValue(@RequestParam SystemRegulationKey key, @RequestBody String value, HttpServletRequest request) {
        systemRegulationService.setValue(key, value);
        return ResponseEntity.ok(new APIResponse<>(true, "Set system regulation value successfully", null, null, request.getRequestURI()));
    }

    @PostMapping
    @Operation(
            summary = "Create System Regulation",
            description = "Create a new system regulation record",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "System regulation creation data",
                    content = @Content(schema = @Schema(implementation = SystemRegulationCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "System regulation created successfully", content = @Content(schema = @Schema(implementation = SystemRegulationDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<SystemRegulationDTO>> create(@Valid @RequestBody SystemRegulationCreateDTO dto, HttpServletRequest request) {
        SystemRegulationDTO result = systemRegulationService.createSystemRegulation(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Create system regulation successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update System Regulation",
            description = "Update an existing system regulation record",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "System regulation update data",
                    content = @Content(schema = @Schema(implementation = SystemRegulationUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "System regulation updated successfully", content = @Content(schema = @Schema(implementation = SystemRegulationDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<SystemRegulationDTO>> update(@Valid @RequestBody SystemRegulationUpdateDTO dto, HttpServletRequest request) {
        SystemRegulationDTO result = systemRegulationService.updateSystemRegulation(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Update system regulation successfully", result, null, request.getRequestURI()));
    }

    @DeleteMapping
    @Operation(
            summary = "Delete System Regulation",
            description = "Delete a system regulation by its key",
            parameters = @Parameter(name = "key", description = "System Regulation Key", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "System regulation deleted successfully")
            }
    )
    public ResponseEntity<APIResponse<Void>> delete(@RequestParam SystemRegulationKey key, HttpServletRequest request) {
        systemRegulationService.deleteSystemRegulation(key);
        return ResponseEntity.ok(new APIResponse<>(true, "Delete system regulation successfully", null, null, request.getRequestURI()));
    }
}