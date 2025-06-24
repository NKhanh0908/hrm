package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.payrollsDTO.PayrollsCreateDTO;
import com.project.hrm.dto.payrollsDTO.PayrollsDTO;
import com.project.hrm.dto.payrollsDTO.PayrollsFilter;
import com.project.hrm.dto.payrollsDTO.PayrollsUpdateDTO;
import com.project.hrm.enums.PayrollStatus;
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

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/payrolls")
@RequiredArgsConstructor
@Tag(name = "Payrolls Controller", description = "Payrolls Management API")
public class PayrollsController {

    private final PayrollsService payrollsService;

    @PostMapping
    @Operation(summary = "Create payroll", description = "Create new payroll", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true, content = @Content(schema = @Schema(implementation = PayrollsCreateDTO.class))
    ))
    public ResponseEntity<APIResponse<PayrollsDTO>> create(@RequestBody PayrollsCreateDTO dto,
                                                           HttpServletRequest request) {
        PayrollsDTO result = payrollsService.create(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Create payroll successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(summary = "Update payroll", description = "Update payroll with non-null fields", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true, content = @Content(schema = @Schema(implementation = PayrollsUpdateDTO.class))
    ))
    public ResponseEntity<APIResponse<PayrollsDTO>> update(@RequestBody PayrollsUpdateDTO dto,
                                                           HttpServletRequest request) {
        PayrollsDTO result = payrollsService.update(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Update payroll successfully", result, null, request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete payroll", description = "Delete payroll by ID")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id,
                                                    HttpServletRequest request) {
        payrollsService.delete(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Delete payroll successfully", null, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get payroll by ID", description = "Get payroll details by ID")
    public ResponseEntity<APIResponse<PayrollsDTO>> getById(@PathVariable Integer id,
                                                            HttpServletRequest request) {
        PayrollsDTO dto = payrollsService.getById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Get payroll successfully", dto, null, request.getRequestURI()));
    }

    @PostMapping("/filter")
    @Operation(summary = "Filter payrolls", description = "Filter payrolls by conditions",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true, content = @Content(schema = @Schema(implementation = PayrollsFilter.class))
            ),
            parameters = {
                    @Parameter(name = "page", description = "Page number (default 0)", example = "0"),
                    @Parameter(name = "size", description = "Page size (default 10)", example = "10")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filtered payroll list", content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = PayrollsDTO.class))
                    ))
            })
    public ResponseEntity<APIResponse<List<PayrollsDTO>>> filter(@RequestBody PayrollsFilter filter,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size,
                                                                 HttpServletRequest request) {
        List<PayrollsDTO> result = payrollsService.filter(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter payrolls successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/filter-range")
    @Operation(summary = "Filter payrolls by range", description = "Filter by income, deduction, and net salary ranges",
            parameters = {
                    @Parameter(name = "minIncome", description = "Min income", example = "10000000"),
                    @Parameter(name = "maxIncome", description = "Max income", example = "20000000"),
                    @Parameter(name = "minDeduction", description = "Min deduction", example = "1000000"),
                    @Parameter(name = "maxDeduction", description = "3000000"),
                    @Parameter(name = "minNetSalary", description = "Min net salary", example = "8000000"),
                    @Parameter(name = "maxNetSalary", description = "17000000"),
                    @Parameter(name = "page", description = "Page number", example = "0"),
                    @Parameter(name = "size", description = "Page size", example = "10")
            })
    public ResponseEntity<APIResponse<List<PayrollsDTO>>> filterWithRange(
            @RequestParam(required = false) BigDecimal minIncome,
            @RequestParam(required = false) BigDecimal maxIncome,
            @RequestParam(required = false) BigDecimal minDeduction,
            @RequestParam(required = false) BigDecimal maxDeduction,
            @RequestParam(required = false) BigDecimal minNetSalary,
            @RequestParam(required = false) BigDecimal maxNetSalary,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        List<PayrollsDTO> result = payrollsService.filterWithRange(minIncome, maxIncome, minDeduction, maxDeduction, minNetSalary, maxNetSalary, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter with range successfully", result, null, request.getRequestURI()));
    }
}
