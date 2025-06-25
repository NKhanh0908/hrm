package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.payrollDetailsDTO.*;
import com.project.hrm.services.PayrollDetailsService;
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
@RequestMapping("/payroll-details")
@RequiredArgsConstructor
@Tag(name = "PayrollDetails Controller", description = "Manage payroll details")
public class PayrollDetailsController {

    private final PayrollDetailsService payrollDetailsService;

    @PostMapping
    @Operation(
            summary = "Create Payroll Detail",
            description = "Create a new payroll detail",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payroll detail creation data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PayrollDetailsCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payroll detail created successfully", content = @Content(schema = @Schema(implementation = PayrollDetailsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PayrollDetailsDTO>> create(@RequestBody PayrollDetailsCreateDTO dto,
                                                                 HttpServletRequest request) {
        PayrollDetailsDTO result = payrollDetailsService.create(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Create payroll detail successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update Payroll Detail",
            description = "Update an existing payroll detail",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payroll detail update data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PayrollDetailsUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payroll detail updated successfully", content = @Content(schema = @Schema(implementation = PayrollDetailsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PayrollDetailsDTO>> update(@RequestBody PayrollDetailsUpdateDTO dto,
                                                                 HttpServletRequest request) {
        PayrollDetailsDTO result = payrollDetailsService.update(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Update payroll detail successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Payroll Detail by ID",
            parameters = @Parameter(name = "id", description = "Payroll Detail ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payroll detail found", content = @Content(schema = @Schema(implementation = PayrollDetailsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PayrollDetailsDTO>> getById(@PathVariable Integer id,
                                                                  HttpServletRequest request) {
        PayrollDetailsDTO result = payrollDetailsService.getById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Get payroll detail successfully", result, null, request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Payroll Detail by ID",
            parameters = @Parameter(name = "id", description = "Payroll Detail ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payroll detail deleted successfully")
            }
    )
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id,
                                                    HttpServletRequest request) {
        payrollDetailsService.delete(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Delete payroll detail successfully", null, null, request.getRequestURI()));
    }

    @PostMapping("/filter")
    @Operation(
            summary = "Filter Payroll Details",
            description = "Filter payroll details based on criteria",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Filter criteria",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PayrollDetailsFilter.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filtered payroll details", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PayrollDetailsDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<PayrollDetailsDTO>>> filter(@RequestBody PayrollDetailsFilter filter,
                                                                       @RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size,
                                                                       HttpServletRequest request) {
        List<PayrollDetailsDTO> list = payrollDetailsService.filter(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter payroll details successfully", list, null, request.getRequestURI()));
    }

    @PostMapping("/filter-range")
    @Operation(
            summary = "Filter Payroll Details with Range",
            description = "Filter payroll details based on amount or percentage range",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Range filter criteria",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PayrollDetailsFilterWithRange.class))
            ),
            parameters = {
                    @Parameter(name = "page", description = "Page number"),
                    @Parameter(name = "size", description = "Page size")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filtered payroll details with range", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PayrollDetailsDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<PayrollDetailsDTO>>> filterRange(@RequestBody PayrollDetailsFilterWithRange filterWithRange,
                                                                            @RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "10") int size,
                                                                            HttpServletRequest request) {
        List<PayrollDetailsDTO> list = payrollDetailsService.filterWithRange(filterWithRange, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter payroll details with range successfully", list, null, request.getRequestURI()));
    }
}
