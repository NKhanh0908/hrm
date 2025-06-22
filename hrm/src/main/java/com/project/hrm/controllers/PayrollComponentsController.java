package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.payrollComponentsDTO.PayrollComponentsCreateDTO;
import com.project.hrm.dto.payrollComponentsDTO.PayrollComponentsDTO;
import com.project.hrm.dto.payrollComponentsDTO.PayrollComponentsFilter;
import com.project.hrm.dto.payrollComponentsDTO.PayrollComponentsUpdateDTO;
import com.project.hrm.enums.PayrollComponentType;
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

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/payroll-components")
@RequiredArgsConstructor
@Tag(name = "Payroll Components Controller", description = "Manage payroll components")
public class PayrollComponentsController {

    private final PayrollComponentsService payrollComponentsService;

    @PostMapping
    @Operation(
            summary = "Create payroll component",
            description = "Creates a new payroll component.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payroll component creation data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PayrollComponentsCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payroll component created successfully",
                            content = @Content(schema = @Schema(implementation = PayrollComponentsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PayrollComponentsDTO>> create(
            @RequestBody PayrollComponentsCreateDTO createDTO,
            HttpServletRequest request) {
        PayrollComponentsDTO created = payrollComponentsService.create(createDTO);
        return ResponseEntity.ok(new APIResponse<>(true, "Created payroll component successfully", created, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update payroll component",
            description = "Updates fields of an existing payroll component by ID.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payroll component update data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PayrollComponentsUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payroll component updated successfully",
                            content = @Content(schema = @Schema(implementation = PayrollComponentsDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Payroll component not found")
            }
    )
    public ResponseEntity<APIResponse<PayrollComponentsDTO>> update(
            @RequestBody PayrollComponentsUpdateDTO updateDTO,
            HttpServletRequest request) {
        PayrollComponentsDTO updated = payrollComponentsService.update(updateDTO);
        return ResponseEntity.ok(new APIResponse<>(true, "Updated payroll component successfully", updated, null, request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete payroll component",
            description = "Deletes a payroll component by its ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the payroll component", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Payroll component not found")
            }
    )
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id, HttpServletRequest request) {
        payrollComponentsService.delete(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Deleted payroll component successfully", null, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get payroll component by ID",
            description = "Retrieves payroll component details by ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the payroll component", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Found payroll component",
                            content = @Content(schema = @Schema(implementation = PayrollComponentsDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Payroll component not found")
            }
    )
    public ResponseEntity<APIResponse<PayrollComponentsDTO>> getById(@PathVariable Integer id, HttpServletRequest request) {
        PayrollComponentsDTO dto = payrollComponentsService.getById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Get payroll component successfully", dto, null, request.getRequestURI()));
    }

    @PostMapping("/filter")
    @Operation(
            summary = "Filter Payroll Components",
            description = "Lọc danh sách payroll components theo các tiêu chí động trong DTO.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Bộ lọc payroll components", required = true,
                    content = @Content(schema = @Schema(implementation = PayrollComponentsFilter.class))
            ),
            parameters = {
                    @Parameter(name = "page", description = "Page number (0-based)", required = false),
                    @Parameter(name = "size", description = "Page size", required = false)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Danh sách payroll components",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PayrollComponentsDTO.class))))
            }
    )

    public ResponseEntity<APIResponse<List<PayrollComponentsDTO>>> filter(
            @RequestBody PayrollComponentsFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request
    ) {
        List<PayrollComponentsDTO> result = payrollComponentsService.filter(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/filter-with-range")
    @Operation(
            summary = "Filter Payroll Components by Range",
            description = "Lọc payroll components theo khoảng amount, percentage và loại type cụ thể.",
            parameters = {
                    @Parameter(name = "minAmount", description = "Giá trị tối thiểu", required = false),
                    @Parameter(name = "maxAmount", description = "Giá trị tối đa", required = false),
                    @Parameter(name = "minPercentage", description = "Phần trăm tối thiểu", required = false),
                    @Parameter(name = "maxPercentage", description = "Phần trăm tối đa", required = false),
                    @Parameter(name = "type", description = "Loại component", required = false),
                    @Parameter(name = "page", description = "Page number (0-based)", required = false),
                    @Parameter(name = "size", description = "Page size", required = false)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Danh sách payroll components theo khoảng",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PayrollComponentsDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<PayrollComponentsDTO>>> filterWithRange(
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount,
            @RequestParam(required = false) Float minPercentage,
            @RequestParam(required = false) Float maxPercentage,
            @RequestParam(required = false) PayrollComponentType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request
    ) {
        List<PayrollComponentsDTO> result = payrollComponentsService
                .filterWithRange(minAmount, maxAmount, minPercentage, maxPercentage, type, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter with range successfully", result, null, request.getRequestURI()));
    }
}
