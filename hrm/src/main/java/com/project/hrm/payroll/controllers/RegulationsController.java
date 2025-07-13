package com.project.hrm.payroll.controllers;

import com.project.hrm.common.response.APIResponse;
import com.project.hrm.payroll.dto.regulationsDTO.RegulationsCreateDTO;
import com.project.hrm.payroll.dto.regulationsDTO.RegulationsDTO;
import com.project.hrm.payroll.dto.regulationsDTO.RegulationsFilter;
import com.project.hrm.payroll.dto.regulationsDTO.RegulationsUpdateDTO;
import com.project.hrm.payroll.entities.Regulations;
import com.project.hrm.payroll.enums.PayrollComponentType;
import com.project.hrm.payroll.mapper.RegulationsMapper;
import com.project.hrm.payroll.services.RegulationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                    @ApiResponse(responseCode = "200", description = "Regulation created successfully", content = @Content(schema = @Schema(implementation = RegulationsDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    public ResponseEntity<APIResponse<RegulationsDTO>> create(@Valid @RequestBody RegulationsCreateDTO dto, BindingResult result, HttpServletRequest request) {
        RegulationsDTO created = regulationsService.create(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Regulation created successfully", created, null, request.getRequestURI()));
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
                    @ApiResponse(responseCode = "200", description = "Regulation updated successfully", content = @Content(schema = @Schema(implementation = RegulationsDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    public ResponseEntity<APIResponse<RegulationsDTO>> update(@Valid @RequestBody RegulationsUpdateDTO dto, BindingResult result, HttpServletRequest request) {
        RegulationsDTO updated = regulationsService.update(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Regulation updated successfully", updated, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Regulation by ID",
            parameters = @Parameter(name = "id", description = "Regulation ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Regulation found", content = @Content(schema = @Schema(implementation = RegulationsDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid ID"),
                    @ApiResponse(responseCode = "404", description = "Regulation not found")
            }
    )
    public ResponseEntity<APIResponse<RegulationsDTO>> getById(@PathVariable @Positive(message = "Regulation ID must be a positive number") Integer id, HttpServletRequest request) {
        RegulationsDTO result = regulationsService.getById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Regulation retrieved successfully", result, null, request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Regulation by ID",
            parameters = @Parameter(name = "id", description = "Regulation ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Regulation deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid ID"),
                    @ApiResponse(responseCode = "404", description = "Regulation not found")
            }
    )
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable @Positive(message = "Regulation ID must be a positive number") Integer id, HttpServletRequest request) {
        regulationsService.delete(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Regulation deleted successfully", null, null, request.getRequestURI()));
    }

    @GetMapping("/filter")
    @Operation(
            summary = "Filter Regulations",
            description = "Filter regulation records by attributes",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filtered regulations", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RegulationsDTO.class)))),
                    @ApiResponse(responseCode = "400", description = "Invalid filter parameters")
            }
    )
    public ResponseEntity<APIResponse<List<RegulationsDTO>>> filter(
            @ParameterObject @ModelAttribute RegulationsFilter filter,
            @RequestParam(defaultValue = "0") @PositiveOrZero(message = "Page number must be zero or positive") int page,
            @RequestParam(defaultValue = "10") @Positive(message = "Page size must be a positive number") int size,
            HttpServletRequest request) {
        List<RegulationsDTO> result = regulationsService.filter(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Regulations filtered successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/by-key")
    @Operation(
            summary = "Get Regulation by Key",
            description = "Retrieve a regulation by its unique key",
            parameters = @Parameter(name = "regulationKey", description = "Regulation Key", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Regulation found", content = @Content(schema = @Schema(implementation = RegulationsDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid regulation key"),
                    @ApiResponse(responseCode = "404", description = "Regulation not found")
            }
    )
    public ResponseEntity<APIResponse<RegulationsDTO>> getByKey(
            @RequestParam @NotBlank(message = "Regulation key cannot be blank")
            @Pattern(regexp = "[A-Za-z0-9_-]+", message = "Regulation key can only contain letters, numbers, underscores, and hyphens") String regulationKey,
            HttpServletRequest request) {
        Regulations regulation = regulationsService.getRegulationsByKey(regulationKey);
        RegulationsDTO result = regulationsMapper.toRegulationsDTO(regulation);
        return ResponseEntity.ok(new APIResponse<>(true, "Regulation retrieved by key successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/by-type")
    @Operation(
            summary = "Find Regulations by Type",
            description = "Retrieve regulations by payroll component type",
            parameters = @Parameter(name = "type", description = "Payroll Component Type", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Regulations found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RegulationsDTO.class)))),
                    @ApiResponse(responseCode = "400", description = "Invalid payroll component type")
            }
    )
    public ResponseEntity<APIResponse<List<RegulationsDTO>>> findByType(
            @RequestParam @NotNull(message = "Payroll component type cannot be null") PayrollComponentType type,
            HttpServletRequest request) {
        List<Regulations> regulations = regulationsService.findRegulationByType(type);
        List<RegulationsDTO> result = regulations.stream()
                .map(regulationsMapper::toRegulationsDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new APIResponse<>(true, "Regulations found by type successfully", result, null, request.getRequestURI()));
    }


}