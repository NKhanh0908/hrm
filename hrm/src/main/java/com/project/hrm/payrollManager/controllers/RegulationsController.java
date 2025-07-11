package com.project.hrm.payrollManager.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.payrollManager.dto.regulationsDTO.RegulationsCreateDTO;
import com.project.hrm.payrollManager.dto.regulationsDTO.RegulationsDTO;
import com.project.hrm.payrollManager.dto.regulationsDTO.RegulationsFilter;
import com.project.hrm.payrollManager.dto.regulationsDTO.RegulationsUpdateDTO;
import com.project.hrm.payrollManager.entities.Regulations;
import com.project.hrm.payrollManager.enums.PayrollComponentType;
import com.project.hrm.payrollManager.mapper.RegulationsMapper;
import com.project.hrm.payrollManager.services.RegulationsService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/regulations")
@RequiredArgsConstructor
@Tag(name = "Regulations Controller", description = "Manage regulation records")
public class RegulationsController {

    private final RegulationsService regulationsService;
    private final RegulationsMapper regulationsMapper;

    @PostMapping
    @Operation(
            summary = "Create Regulation",
            description = "Create a new regulation record",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Regulation creation data",
                    content = @Content(schema = @Schema(implementation = RegulationsCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Regulation created successfully", content = @Content(schema = @Schema(implementation = RegulationsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<RegulationsDTO>> create(@Valid @RequestBody RegulationsCreateDTO dto, HttpServletRequest request) {
        RegulationsDTO result = regulationsService.create(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Create regulation successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update Regulation",
            description = "Update an existing regulation record",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Regulation update data",
                    content = @Content(schema = @Schema(implementation = RegulationsUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Regulation updated successfully", content = @Content(schema = @Schema(implementation = RegulationsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<RegulationsDTO>> update(@Valid @RequestBody RegulationsUpdateDTO dto, HttpServletRequest request) {
        RegulationsDTO result = regulationsService.update(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Update regulation successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Regulation by ID",
            parameters = @Parameter(name = "id", description = "Regulation ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Regulation found", content = @Content(schema = @Schema(implementation = RegulationsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<RegulationsDTO>> getById(@PathVariable Integer id, HttpServletRequest request) {
        RegulationsDTO result = regulationsService.getById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Get regulation successfully", result, null, request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Regulation by ID",
            parameters = @Parameter(name = "id", description = "Regulation ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Regulation deleted successfully")
            }
    )
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id, HttpServletRequest request) {
        regulationsService.delete(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Delete regulation successfully", null, null, request.getRequestURI()));
    }

    @PostMapping("/filter")
    @Operation(
            summary = "Filter Regulations",
            description = "Filter regulation records by attributes",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Filter criteria",
                    content = @Content(schema = @Schema(implementation = RegulationsFilter.class))
            ),
            parameters = {
                    @Parameter(name = "page", description = "Page number"),
                    @Parameter(name = "size", description = "Page size")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filtered regulations", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RegulationsDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<RegulationsDTO>>> filter(@RequestBody RegulationsFilter filter,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size,
                                                                    HttpServletRequest request) {
        List<RegulationsDTO> result = regulationsService.filter(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter regulations successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/by-key")
    @Operation(
            summary = "Get Regulation by Key",
            description = "Retrieve a regulation by its unique key",
            parameters = @Parameter(name = "regulationsKey", description = "Regulation Key", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Regulation found", content = @Content(schema = @Schema(implementation = RegulationsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<RegulationsDTO>> getByKey(@RequestParam String regulationsKey, HttpServletRequest request) {
        Regulations regulation = regulationsService.getRegulationsByKey(regulationsKey);
        RegulationsDTO result = regulationsMapper.toRegulationsDTO(regulation);
        return ResponseEntity.ok(new APIResponse<>(true, "Get regulation by key successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/by-type")
    @Operation(
            summary = "Find Regulations by Type",
            description = "Retrieve regulations by payroll component type",
            parameters = @Parameter(name = "type", description = "Payroll Component Type", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Regulations found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RegulationsDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<RegulationsDTO>>> findByType(@RequestParam PayrollComponentType type, HttpServletRequest request) {
        List<Regulations> regulations = regulationsService.findRegulationByType(type);
        List<RegulationsDTO> result = regulations.stream()
                .map(regulationsMapper::toRegulationsDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new APIResponse<>(true, "Find regulations by type successfully", result, null, request.getRequestURI()));
    }
}