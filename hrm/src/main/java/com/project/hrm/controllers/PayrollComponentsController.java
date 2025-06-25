package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.payrollComponentsDTO.*;
import com.project.hrm.services.PayrollComponentsService;
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
@RequestMapping("/payroll-components")
@RequiredArgsConstructor
@Tag(name = "PayrollComponents Controller", description = "Manage payroll components")
public class PayrollComponentsController {

    private final PayrollComponentsService payrollComponentsService;

    @PostMapping
    @Operation(
            summary = "Create Payroll Component",
            description = "Create a new payroll component",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payroll component creation data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PayrollComponentsCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payroll component created successfully", content = @Content(schema = @Schema(implementation = PayrollComponentsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PayrollComponentsDTO>> create(@RequestBody PayrollComponentsCreateDTO dto,
                                                                    HttpServletRequest request) {
        PayrollComponentsDTO result = payrollComponentsService.create(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Create payroll component successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update Payroll Component",
            description = "Update an existing payroll component",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payroll component update data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PayrollComponentsUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payroll component updated successfully", content = @Content(schema = @Schema(implementation = PayrollComponentsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PayrollComponentsDTO>> update(@RequestBody PayrollComponentsUpdateDTO dto,
                                                                    HttpServletRequest request) {
        PayrollComponentsDTO result = payrollComponentsService.update(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Update payroll component successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Payroll Component by ID",
            parameters = @Parameter(name = "id", description = "Payroll Component ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payroll component found", content = @Content(schema = @Schema(implementation = PayrollComponentsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PayrollComponentsDTO>> getById(@PathVariable Integer id,
                                                                     HttpServletRequest request) {
        PayrollComponentsDTO result = payrollComponentsService.getById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Get payroll component successfully", result, null, request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Payroll Component by ID",
            parameters = @Parameter(name = "id", description = "Payroll Component ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payroll component deleted successfully")
            }
    )
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id,
                                                    HttpServletRequest request) {
        payrollComponentsService.delete(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Delete payroll component successfully", null, null, request.getRequestURI()));
    }

    @PostMapping("/filter")
    @Operation(
            summary = "Filter Payroll Components",
            description = "Filter payroll components by attributes",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Filter criteria",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PayrollComponentsFilter.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filtered payroll components", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PayrollComponentsDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<PayrollComponentsDTO>>> filter(@RequestBody PayrollComponentsFilter filter,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") int size,
                                                                          HttpServletRequest request) {
        List<PayrollComponentsDTO> list = payrollComponentsService.filter(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter payroll components successfully", list, null, request.getRequestURI()));
    }

    @PostMapping("/filter-range")
    @Operation(
            summary = "Filter Payroll Components with Range",
            description = "Filter payroll components with numeric range filtering",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Range filter criteria",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PayrollComponentsFilterWithRange.class))
            ),
            parameters = {
                    @Parameter(name = "page", description = "Page number"),
                    @Parameter(name = "size", description = "Page size")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filtered payroll components with range", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PayrollComponentsDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<PayrollComponentsDTO>>> filterRange(@RequestBody PayrollComponentsFilterWithRange filterWithRange,
                                                                               @RequestParam(defaultValue = "0") int page,
                                                                               @RequestParam(defaultValue = "10") int size,
                                                                               HttpServletRequest request) {
        List<PayrollComponentsDTO> list = payrollComponentsService.filterWithRange(filterWithRange, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter payroll components with range successfully", list, null, request.getRequestURI()));
    }
}
