package com.project.hrm.payroll.controllers;

import com.project.hrm.common.response.APIResponse;
import com.project.hrm.payroll.dto.payrollComponentsDTO.PayrollComponentsCreateDTO;
import com.project.hrm.payroll.dto.payrollComponentsDTO.PayrollComponentsDTO;
import com.project.hrm.payroll.dto.payrollComponentsDTO.PayrollComponentsFilter;
import com.project.hrm.payroll.dto.payrollComponentsDTO.PayrollComponentsFilterWithRange;
import com.project.hrm.payroll.dto.payrollComponentsDTO.PayrollComponentsUpdateDTO;
import com.project.hrm.payroll.entities.PayrollComponents;
import com.project.hrm.payroll.enums.PayrollComponentType;
import com.project.hrm.payroll.mapper.PayrollComponentMapper;
import com.project.hrm.payroll.services.PayrollComponentsService;
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
@RequestMapping("/payroll-components")
@RequiredArgsConstructor
@Tag(name = "Payroll Components Controller", description = "Manage payroll component records")
public class PayrollComponentsController {

    private final PayrollComponentsService payrollComponentsService;
    private final PayrollComponentMapper payrollComponentMapper;

    @PostMapping
    @Operation(
            summary = "Create Payroll Component",
            description = "Create a new payroll component record",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Payroll component creation data",
                    content = @Content(schema = @Schema(implementation = PayrollComponentsCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payroll component created successfully", content = @Content(schema = @Schema(implementation = PayrollComponentsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PayrollComponentsDTO>> create(@Valid @RequestBody PayrollComponentsCreateDTO dto, HttpServletRequest request) {
        PayrollComponentsDTO result = payrollComponentsService.create(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Create payroll component successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update Payroll Component",
            description = "Update an existing payroll component record",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Payroll component update data",
                    content = @Content(schema = @Schema(implementation = PayrollComponentsUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payroll component updated successfully", content = @Content(schema = @Schema(implementation = PayrollComponentsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PayrollComponentsDTO>> update(@Valid @RequestBody PayrollComponentsUpdateDTO dto, HttpServletRequest request) {
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
    public ResponseEntity<APIResponse<PayrollComponentsDTO>> getById(@PathVariable Integer id, HttpServletRequest request) {
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
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id, HttpServletRequest request) {
        payrollComponentsService.delete(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Delete payroll component successfully", null, null, request.getRequestURI()));
    }

    @PostMapping("/filter")
    @Operation(
            summary = "Filter Payroll Components",
            description = "Filter payroll component records by attributes",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Filter criteria",
                    content = @Content(schema = @Schema(implementation = PayrollComponentsFilter.class))
            ),
            parameters = {
                    @Parameter(name = "page", description = "Page number"),
                    @Parameter(name = "size", description = "Page size")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filtered payroll components", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PayrollComponentsDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<PayrollComponentsDTO>>> filter(@RequestBody PayrollComponentsFilter filter,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") int size,
                                                                          HttpServletRequest request) {
        List<PayrollComponentsDTO> result = payrollComponentsService.filter(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter payroll components successfully", result, null, request.getRequestURI()));
    }

    @PostMapping("/filter-range")
    @Operation(
            summary = "Filter Payroll Components with Range",
            description = "Filter payroll component records by range of attributes",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Filter criteria with ranges",
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
        List<PayrollComponentsDTO> result = payrollComponentsService.filterWithRange(filterWithRange, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter payroll components with range successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/payroll/{payrollId}")
    @Operation(
            summary = "Get Payroll Component by Payroll ID and Type",
            description = "Retrieve a payroll component by payroll ID and component type",
            parameters = {
                    @Parameter(name = "payrollId", description = "Payroll ID", required = true),
                    @Parameter(name = "type", description = "Payroll Component Type", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payroll component found", content = @Content(schema = @Schema(implementation = PayrollComponentsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PayrollComponentsDTO>> getPayrollComponentByPayrollIdAndType(
            @PathVariable Integer payrollId,
            @RequestParam PayrollComponentType type,
            HttpServletRequest request) {
        PayrollComponents result = payrollComponentsService.getPayrollComponentByPayrollIdAndType(payrollId, type);
        PayrollComponentsDTO dto = payrollComponentMapper.toPayrollComponentsDTO(result);
        return ResponseEntity.ok(new APIResponse<>(true, "Get payroll component by payroll ID and type successfully", dto, null, request.getRequestURI()));
    }

}