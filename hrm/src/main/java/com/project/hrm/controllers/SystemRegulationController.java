package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.systemRegulationDTO.SystemRegulationCreateDTO;
import com.project.hrm.dto.systemRegulationDTO.SystemRegulationDTO;
import com.project.hrm.dto.systemRegulationDTO.SystemRegulationUpdateDTO;
import com.project.hrm.enums.SystemRegulationKey;
import com.project.hrm.services.SystemRegulationService;
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
@RequestMapping("/system-regulations")
@RequiredArgsConstructor
@Tag(name = "System Regulation Controller", description = "Manage system-wide configuration settings")
public class SystemRegulationController {

    private final SystemRegulationService systemRegulationService;

    @GetMapping
    @Operation(
            summary = "Get all system regulations",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Get list all system regulations successfully",
                            content = @Content(array = @ArraySchema(schema =@Schema(implementation = SystemRegulationDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<SystemRegulationDTO>>> getAllSystemRegulations(HttpServletRequest request) {
        List<SystemRegulationDTO> systemRegulationDTOList = systemRegulationService.getAllSystemRegulations();
        return ResponseEntity.ok(new APIResponse<>(true, "Get value successfully", systemRegulationDTOList, null, request.getRequestURI()));
    }

    @GetMapping("/{key}")
    @Operation(
            summary = "Get system regulation value by key",
            parameters = @Parameter(name = "key", description = "Key of the system regulation", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Value retrieved successfully",
                            content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    public ResponseEntity<APIResponse<String>> getValue(@PathVariable SystemRegulationKey key,
                                                        HttpServletRequest request) {
        String value = systemRegulationService.getValue(key);
        return ResponseEntity.ok(new APIResponse<>(true, "Get value successfully", value, null, request.getRequestURI()));
    }

    @PutMapping("/{key}")
    @Operation(
            summary = "Set system regulation value by key",
            description = "Update the value of a system regulation",
            parameters = @Parameter(name = "key", description = "Key of the system regulation", required = true),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New value",
                    required = true,
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Value updated successfully")
            }
    )
    public ResponseEntity<APIResponse<Void>> setValue(@PathVariable SystemRegulationKey key,
                                                      @RequestBody String value,
                                                      HttpServletRequest request) {
        systemRegulationService.setValue(key, value);
        return ResponseEntity.ok(new APIResponse<>(true, "Update value successfully", null, null, request.getRequestURI()));
    }

    @PostMapping
    @Operation(
            summary = "Create system regulation",
            description = "Create a new system configuration rule",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = SystemRegulationCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Created successfully",
                            content = @Content(schema = @Schema(implementation = SystemRegulationDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<SystemRegulationDTO>> create(@RequestBody SystemRegulationCreateDTO dto,
                                                                   HttpServletRequest request) {
        SystemRegulationDTO result = systemRegulationService.createSystemRegulation(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Create regulation successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update system regulation",
            description = "Update an existing system configuration rule",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = SystemRegulationUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Updated successfully",
                            content = @Content(schema = @Schema(implementation = SystemRegulationDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<SystemRegulationDTO>> update(@RequestBody SystemRegulationUpdateDTO dto,
                                                                   HttpServletRequest request) {
        SystemRegulationDTO result = systemRegulationService.updateSystemRegulation(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Update regulation successfully", result, null, request.getRequestURI()));
    }

    @DeleteMapping("/{key}")
    @Operation(
            summary = "Delete system regulation",
            description = "Remove a system configuration rule by key",
            parameters = @Parameter(name = "key", description = "Key of the system regulation", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Deleted successfully")
            }
    )
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable SystemRegulationKey key,
                                                    HttpServletRequest request) {
        systemRegulationService.deleteSystemRegulation(key);
        return ResponseEntity.ok(new APIResponse<>(true, "Delete regulation successfully", null, null, request.getRequestURI()));
    }
}
