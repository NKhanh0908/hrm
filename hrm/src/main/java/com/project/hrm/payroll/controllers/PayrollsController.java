package com.project.hrm.payroll.controllers;

import com.project.hrm.common.response.APIResponse;
import com.project.hrm.payroll.dto.payrollsDTO.*;
import com.project.hrm.payroll.services.PayrollsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
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
                    description = "Payroll creation data",
                    required = true,
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
            description = "Update an existing payroll",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payroll update data",
                    required = true,
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

    @GetMapping("/filter")
    @Operation(
            summary = "Filter Payrolls",
            description = "Filter payrolls by attributes",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Filtered payrolls",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PayrollsDTO.class)))
                    )
            }
    )
    public ResponseEntity<APIResponse<List<PayrollsDTO>>> filter(
            @ParameterObject @ModelAttribute PayrollsFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        List<PayrollsDTO> list = payrollsService.filter(filter, page, size);
        return ResponseEntity.ok(
                new APIResponse<>(true, "Filter payrolls successfully", list, null, request.getRequestURI())
        );
    }

    @GetMapping("/filter-range")
    @Operation(
            summary = "Filter Payrolls with Range",
            description = "Filter payrolls with salary or deduction ranges",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Filtered payrolls with range",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PayrollsDTO.class)))
                    )
            }
    )
    public ResponseEntity<APIResponse<List<PayrollsDTO>>> filterRange(
            @ParameterObject @ModelAttribute PayrollsFilterWithRange filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        List<PayrollsDTO> list = payrollsService.filterWithRange(filter, page, size);
        return ResponseEntity.ok(
                new APIResponse<>(true, "Filter payrolls with range successfully", list, null, request.getRequestURI())
        );
    }

    @PostMapping("/calculate/single")
    @Operation(
            summary = "Calculate Payroll for Single Employee",
            description = "Trigger payroll calculation for one employee",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payroll input data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PayrollsCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payroll calculated", content = @Content(schema = @Schema(implementation = PayrollsResponseDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PayrollsResponseDTO>> createPayrollForEmployee(@RequestBody PayrollsCreateDTO dto,
                                                                                     HttpServletRequest request) {
        PayrollsResponseDTO result = payrollsService.createPayrollForEmployee(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Calculate payroll successfully", result, null, request.getRequestURI()));
    }

    @PostMapping("/calculate/department/{departmentId}")
    @Operation(
            summary = "Calculate Payrolls for Department",
            description = "Batch payroll calculation for a specific department",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payroll input template",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PayrollsCreateDTO.class))
            ),
            parameters = @Parameter(name = "departmentId", description = "Department ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payrolls calculated", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PayrollsResponseDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<PayrollsResponseDTO>>> createPayrollsForDepartment(@PathVariable Integer departmentId,
                                                                                              @RequestBody PayrollsCreateDTO dto,
                                                                                              HttpServletRequest request) {
        List<PayrollsResponseDTO> result = payrollsService.createPayrollsForDepartment(departmentId, dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Calculate department payrolls successfully", result, null, request.getRequestURI()));
    }

    @PostMapping("/calculate/all")
    @Operation(
            summary = "Calculate Payrolls for All Employees",
            description = "Trigger payroll generation for all employees",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payroll input template",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PayrollsCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "All payrolls calculated", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PayrollsResponseDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<PayrollsResponseDTO>>> createPayrollsForAllEmployee(@RequestBody PayrollsCreateDTO dto,
                                                                                               HttpServletRequest request) {
        List<PayrollsResponseDTO> result = payrollsService.createPayrollsForAllEmployee(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Calculate all payrolls successfully", result, null, request.getRequestURI()));
    }
}
