package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.payPeriodsDTO.PayPeriodsCreateDTO;
import com.project.hrm.dto.payPeriodsDTO.PayPeriodsDTO;
import com.project.hrm.dto.payPeriodsDTO.PayPeriodsFilter;
import com.project.hrm.dto.payPeriodsDTO.PayPeriodsUpdateDTO;
import com.project.hrm.services.PayPeriodsService;
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
@RequestMapping("/pay-periods")
@RequiredArgsConstructor
@Tag(name = "PayPeriods Controller", description = "Manage pay periods")
public class PayPeriodsController {

    private final PayPeriodsService payPeriodsService;

    @PostMapping
    @Operation(
            summary = "Create PayPeriod",
            description = "Create a new pay period",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "PayPeriod create DTO",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PayPeriodsCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created successfully", content = @Content(schema = @Schema(implementation = PayPeriodsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PayPeriodsDTO>> create(@RequestBody PayPeriodsCreateDTO dto, HttpServletRequest request) {
        PayPeriodsDTO result = payPeriodsService.create(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Created PayPeriod successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update PayPeriod",
            description = "Update an existing pay period by ID",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "PayPeriod update DTO",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PayPeriodsUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Updated successfully", content = @Content(schema = @Schema(implementation = PayPeriodsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PayPeriodsDTO>> update(@RequestBody PayPeriodsUpdateDTO dto, HttpServletRequest request) {
        PayPeriodsDTO result = payPeriodsService.update(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Updated PayPeriod successfully", result, null, request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete PayPeriod",
            description = "Delete a pay period by ID",
            parameters = {
                    @Parameter(name = "id", description = "PayPeriod ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Deleted successfully")
            }
    )
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id, HttpServletRequest request) {
        payPeriodsService.delete(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Deleted PayPeriod successfully", null, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get PayPeriod by ID",
            description = "Retrieve a pay period by ID",
            parameters = {
                    @Parameter(name = "id", description = "PayPeriod ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Retrieved successfully", content = @Content(schema = @Schema(implementation = PayPeriodsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PayPeriodsDTO>> getById(@PathVariable Integer id, HttpServletRequest request) {
        PayPeriodsDTO result = payPeriodsService.getById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Retrieved PayPeriod successfully", result, null, request.getRequestURI()));
    }

    @PostMapping("/filter")
    @Operation(
            summary = "Filter PayPeriods",
            description = "Filter pay periods by code, date range, and status",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Filter conditions",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PayPeriodsFilter.class))
            ),
            parameters = {
                    @Parameter(name = "page", description = "Page number", example = "0"),
                    @Parameter(name = "size", description = "Page size", example = "10")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filtered successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PayPeriodsDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<PayPeriodsDTO>>> filter(
            @RequestBody PayPeriodsFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request
    ) {
        List<PayPeriodsDTO> results = payPeriodsService.filter(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filtered PayPeriods successfully", results, null, request.getRequestURI()));
    }
}
