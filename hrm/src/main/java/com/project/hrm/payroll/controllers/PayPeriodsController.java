package com.project.hrm.payroll.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.payroll.dto.payPeriodsDTO.PayPeriodsCreateDTO;
import com.project.hrm.payroll.dto.payPeriodsDTO.PayPeriodsDTO;
import com.project.hrm.payroll.dto.payPeriodsDTO.PayPeriodsFilter;
import com.project.hrm.payroll.dto.payPeriodsDTO.PayPeriodsUpdateDTO;
import com.project.hrm.payroll.entities.PayPeriods;
import com.project.hrm.payroll.mapper.PayPeriodMapper;
import com.project.hrm.payroll.services.PayPeriodsService;
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

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/pay-periods")
@RequiredArgsConstructor
@Tag(name = "Pay Periods Controller", description = "Manage pay period records")
public class PayPeriodsController {

    private final PayPeriodsService payPeriodsService;
    private final PayPeriodMapper payPeriodMapper;

    @PostMapping
    @Operation(
            summary = "Create Pay Period",
            description = "Create a new pay period record",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Pay period creation data",
                    content = @Content(schema = @Schema(implementation = PayPeriodsCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pay period created successfully", content = @Content(schema = @Schema(implementation = PayPeriodsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PayPeriodsDTO>> create(@Valid @RequestBody PayPeriodsCreateDTO dto, HttpServletRequest request) {
        PayPeriodsDTO result = payPeriodsService.create(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Create pay period successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update Pay Period",
            description = "Update an existing pay period record",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Pay period update data",
                    content = @Content(schema = @Schema(implementation = PayPeriodsUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pay period updated successfully", content = @Content(schema = @Schema(implementation = PayPeriodsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PayPeriodsDTO>> update(@Valid @RequestBody PayPeriodsUpdateDTO dto, HttpServletRequest request) {
        PayPeriodsDTO result = payPeriodsService.update(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Update pay period successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Pay Period by ID",
            parameters = @Parameter(name = "id", description = "Pay Period ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pay period found", content = @Content(schema = @Schema(implementation = PayPeriodsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PayPeriodsDTO>> getById(@PathVariable Integer id, HttpServletRequest request) {
        PayPeriodsDTO result = payPeriodsService.getById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Get pay period successfully", result, null, request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Pay Period by ID",
            parameters = @Parameter(name = "id", description = "Pay Period ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pay period deleted successfully")
            }
    )
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id, HttpServletRequest request) {
        payPeriodsService.delete(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Delete pay period successfully", null, null, request.getRequestURI()));
    }

    @PostMapping("/filter")
    @Operation(
            summary = "Filter Pay Periods",
            description = "Filter pay period records by attributes",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Filter criteria",
                    content = @Content(schema = @Schema(implementation = PayPeriodsFilter.class))
            ),
            parameters = {
                    @Parameter(name = "page", description = "Page number"),
                    @Parameter(name = "size", description = "Page size")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filtered pay periods", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PayPeriodsDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<PayPeriodsDTO>>> filter(@RequestBody PayPeriodsFilter filter,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size,
                                                                   HttpServletRequest request) {
        List<PayPeriodsDTO> result = payPeriodsService.filter(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter pay periods successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/by-date")
    @Operation(
            summary = "Get Pay Period by Date Range",
            description = "Retrieve a pay period by start and end date",
            parameters = {
                    @Parameter(name = "startDate", description = "Start date of the pay period", required = true),
                    @Parameter(name = "endDate", description = "End date of the pay period", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pay period found or null", content = @Content(schema = @Schema(implementation = PayPeriodsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PayPeriodsDTO>> getPayPeriodsByDate(
            @RequestParam String startDate,
            @RequestParam String endDate,
            HttpServletRequest request) {
        PayPeriods payPeriods = payPeriodsService.getPayPeriodsByDate(LocalDateTime.parse(startDate), LocalDateTime.parse(endDate));
        PayPeriodsDTO result = payPeriods != null ? payPeriodMapper.toPayPeriodDTO(payPeriods) : null;
        return ResponseEntity.ok(new APIResponse<>(true, "Get pay period by date successfully", result, null, request.getRequestURI()));
    }

    @PostMapping("/get-or-create")
    @Operation(
            summary = "Get or Create Pay Period",
            description = "Retrieve an existing pay period or create a new one if none exists for the given date range",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Pay period date range",
                    content = @Content(schema = @Schema(implementation = PayPeriodsCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pay period retrieved or created successfully", content = @Content(schema = @Schema(implementation = PayPeriodsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PayPeriodsDTO>> getOrCreatePayPeriod(@Valid @RequestBody PayPeriodsCreateDTO dto, HttpServletRequest request) {
        PayPeriods payPeriods = payPeriodsService.getOrCreatePayPeriod(dto.getStartDate(), dto.getEndDate());
        PayPeriodsDTO result = payPeriodMapper.toPayPeriodDTO(payPeriods);
        return ResponseEntity.ok(new APIResponse<>(true, "Get or create pay period successfully", result, null, request.getRequestURI()));
    }
}