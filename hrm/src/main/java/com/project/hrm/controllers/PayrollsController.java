package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.payrollsDTO.*;
import com.project.hrm.services.PayrollsService;
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
@RequestMapping("/payrolls")
@RequiredArgsConstructor
@Tag(name = "Payrolls Controller", description = "Manage payroll records")
public class PayrollsController {

    private final PayrollsService payrollsService;

    @PostMapping
    @Operation(
            summary = "Create Payroll",
            description = "Create a new payroll record",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Payroll creation data",
                    content = @Content(schema = @Schema(implementation = PayrollsCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payroll created successfully", content = @Content(schema = @Schema(implementation = PayrollsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PayrollsDTO>> create(@RequestBody PayrollsCreateDTO dto,
                                                           HttpServletRequest request) {
        PayrollsDTO result = payrollsService.create(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Create payroll successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update Payroll",
            description = "Update an existing payroll record",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Payroll update data",
                    content = @Content(schema = @Schema(implementation = PayrollsUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payroll updated successfully", content = @Content(schema = @Schema(implementation = PayrollsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PayrollsDTO>> update(@RequestBody PayrollsUpdateDTO dto,
                                                           HttpServletRequest request) {
        PayrollsDTO result = payrollsService.update(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Update payroll successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Payroll by ID",
            parameters = @Parameter(name = "id", description = "Payroll ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payroll found", content = @Content(schema = @Schema(implementation = PayrollsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PayrollsDTO>> getById(@PathVariable Integer id,
                                                            HttpServletRequest request) {
        PayrollsDTO result = payrollsService.getById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Get payroll successfully", result, null, request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Payroll by ID",
            parameters = @Parameter(name = "id", description = "Payroll ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payroll deleted successfully")
            }
    )
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id,
                                                    HttpServletRequest request) {
        payrollsService.delete(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Delete payroll successfully", null, null, request.getRequestURI()));
    }

    @PostMapping("/filter")
    @Operation(
            summary = "Filter Payrolls",
            description = "Filter payroll records by attributes",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Filter criteria",
                    content = @Content(schema = @Schema(implementation = PayrollsFilter.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filtered payrolls", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PayrollsDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<PayrollsDTO>>> filter(@RequestBody PayrollsFilter filter,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size,
                                                                 HttpServletRequest request) {
        List<PayrollsDTO> result = payrollsService.filter(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter payrolls successfully", result, null, request.getRequestURI()));
    }

    @PostMapping("/filter-range")
    @Operation(
            summary = "Filter Payrolls with Range",
            description = "Filter payrolls by income, deduction, and net salary range",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Filter criteria with ranges",
                    content = @Content(schema = @Schema(implementation = PayrollsFilterWithRange.class))
            ),
            parameters = {
                    @Parameter(name = "page", description = "Page number"),
                    @Parameter(name = "size", description = "Page size")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filtered payrolls with range", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PayrollsDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<PayrollsDTO>>> filterRange(@RequestBody PayrollsFilterWithRange filterWithRange,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size,
                                                                      HttpServletRequest request) {
        List<PayrollsDTO> result = payrollsService.filterWithRange(filterWithRange, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter payrolls with range successfully", result, null, request.getRequestURI()));
    }
}
