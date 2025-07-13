package com.project.hrm.employee.controller;

import com.project.hrm.common.response.APIResponse;
import com.project.hrm.employee.dto.disciplinaryActionDTO.DisciplinaryActionCreateDTO;
import com.project.hrm.employee.dto.disciplinaryActionDTO.DisciplinaryActionDTO;
import com.project.hrm.employee.dto.disciplinaryActionDTO.DisciplinaryActionUpdateDTO;
import com.project.hrm.employee.service.DisciplinaryActionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/disciplinary-actions")
@RequiredArgsConstructor
@Tag(name = "Disciplinary Action Controller", description = "Manage disciplinary action records")
public class DisciplinaryActionController {

    private final DisciplinaryActionService disciplinaryActionService;

    @PostMapping
    @Operation(
            summary = "Create Disciplinary Action",
            description = "Create a new disciplinary action record",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Disciplinary action creation data",
                    content = @Content(schema = @Schema(implementation = DisciplinaryActionCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Disciplinary action created successfully", content = @Content(schema = @Schema(implementation = DisciplinaryActionDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    public ResponseEntity<APIResponse<DisciplinaryActionDTO>> create(@Valid @RequestBody DisciplinaryActionCreateDTO dto, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return handleValidationErrors(result, request);
        }
        DisciplinaryActionDTO created = disciplinaryActionService.createDisciplinaryAction(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Disciplinary action created successfully", created, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update Disciplinary Action",
            description = "Update an existing disciplinary action record",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Disciplinary action update data",
                    content = @Content(schema = @Schema(implementation = DisciplinaryActionUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Disciplinary action updated successfully", content = @Content(schema = @Schema(implementation = DisciplinaryActionDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    public ResponseEntity<APIResponse<DisciplinaryActionDTO>> update(@Valid @RequestBody DisciplinaryActionUpdateDTO dto, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return handleValidationErrors(result, request);
        }
        DisciplinaryActionDTO updated = disciplinaryActionService.updateDisciplinaryAction(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Disciplinary action updated successfully", updated, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Disciplinary Action by ID",
            parameters = @Parameter(name = "id", description = "Disciplinary Action ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Disciplinary action found", content = @Content(schema = @Schema(implementation = DisciplinaryActionDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid ID")
            }
    )
    public ResponseEntity<APIResponse<DisciplinaryActionDTO>> getById(@PathVariable @Positive(message = "Disciplinary action ID must be a positive number") Integer id, HttpServletRequest request) {
        DisciplinaryActionDTO result = disciplinaryActionService.getDTO(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Disciplinary action retrieved successfully", result, null, request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Disciplinary Action by ID",
            parameters = @Parameter(name = "id", description = "Disciplinary Action ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Disciplinary action deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid ID")
            }
    )
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable @Positive(message = "Disciplinary action ID must be a positive number") Integer id, HttpServletRequest request) {
        disciplinaryActionService.deleteDisciplinaryAction(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Disciplinary action deleted successfully", null, null, request.getRequestURI()));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(
            summary = "Get Disciplinary Actions by Employee ID and Date Range",
            description = "Retrieve disciplinary actions for an employee within a specific date range",
            parameters = {
                    @Parameter(name = "employeeId", description = "Employee ID", required = true),
                    @Parameter(name = "startDate", description = "Start date of the range (ISO format: yyyy-MM-dd'T'HH:mm:ss[.SSS...])", required = true),
                    @Parameter(name = "endDate", description = "End date of the range (ISO format: yyyy-MM-dd'T'HH:mm:ss[.SSS...])", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Disciplinary actions found for employee", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DisciplinaryActionDTO.class)))),
                    @ApiResponse(responseCode = "400", description = "Invalid employee ID or date range")
            }
    )
    public ResponseEntity<APIResponse<List<DisciplinaryActionDTO>>> getDisciplinaryActionByEmployeeIdAndDate(
            @PathVariable @Positive(message = "Employee ID must be a positive number") Integer employeeId,
            @RequestParam @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?", message = "Start date must be in ISO format (yyyy-MM-dd'T'HH:mm:ss[.SSS...])") String startDate,
            @RequestParam @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?", message = "End date must be in ISO format (yyyy-MM-dd'T'HH:mm:ss[.SSS...])") String endDate,
            HttpServletRequest request) {
        try {
            LocalDateTime start = LocalDateTime.parse(startDate);
            LocalDateTime end = LocalDateTime.parse(endDate);
            if (end.isBefore(start)) {
                List<String> errors = new ArrayList<>();
                errors.add("End date must be after or equal to start date");
                return ResponseEntity.badRequest().body(new APIResponse<>(false, "Validation failed", null, errors, request.getRequestURI()));
            }
            List<DisciplinaryActionDTO> result = disciplinaryActionService.getDisciplinaryActionByEmployeeIdAndDate(employeeId, start, end);
            return ResponseEntity.ok(new APIResponse<>(true, "Disciplinary actions retrieved successfully", result, null, request.getRequestURI()));
        } catch (DateTimeParseException e) {
            List<String> errors = new ArrayList<>();
            errors.add("Invalid date format: " + e.getMessage());
            return ResponseEntity.badRequest().body(new APIResponse<>(false, "Validation failed", null, errors, request.getRequestURI()));
        }
    }

    @PostMapping("/batch")
    @Operation(
            summary = "Get Batch Disciplinary Actions",
            description = "Retrieve disciplinary actions for multiple employees within a date range",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "List of employee IDs",
                    content = @Content(schema = @Schema(implementation = List.class))
            ),
            parameters = {
                    @Parameter(name = "startDate", description = "Start date of the range (ISO format: yyyy-MM-dd'T'HH:mm:ss[.SSS...])", required = true),
                    @Parameter(name = "endDate", description = "End date of the range (ISO format: yyyy-MM-dd'T'HH:mm:ss[.SSS...])", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Batch disciplinary actions retrieved", content = @Content(schema = @Schema(implementation = Map.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid employee IDs or date range")
            }
    )
    public ResponseEntity<APIResponse<Map<Integer, List<DisciplinaryActionDTO>>>> getBatchDisciplinaryActions(
            @RequestBody @NotEmpty(message = "Employee IDs list cannot be empty") List<@Positive(message = "Employee ID must be a positive number") Integer> employeeIds,
            @RequestParam @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?", message = "Start date must be in ISO format (yyyy-MM-dd'T'HH:mm:ss[.SSS...])") String startDate,
            @RequestParam @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?", message = "End date must be in ISO format (yyyy-MM-dd'T'HH:mm:ss[.SSS...])") String endDate,
            HttpServletRequest request) {
        try {
            LocalDateTime start = LocalDateTime.parse(startDate);
            LocalDateTime end = LocalDateTime.parse(endDate);
            if (end.isBefore(start)) {
                List<String> errors = new ArrayList<>();
                errors.add("End date must be after or equal to start date");
                return ResponseEntity.badRequest().body(new APIResponse<>(false, "Validation failed", null, errors, request.getRequestURI()));
            }
            Map<Integer, List<DisciplinaryActionDTO>> result = disciplinaryActionService.getBatchDisciplinaryActions(employeeIds, start, end);
            return ResponseEntity.ok(new APIResponse<>(true, "Batch disciplinary actions retrieved successfully", result, null, request.getRequestURI()));
        } catch (DateTimeParseException e) {
            List<String> errors = new ArrayList<>();
            errors.add("Invalid date format: " + e.getMessage());
            return ResponseEntity.badRequest().body(new APIResponse<>(false, "Validation failed", null, errors, request.getRequestURI()));
        }
    }

    private <T> ResponseEntity<APIResponse<T>> handleValidationErrors(BindingResult result, HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.add(error.getField());
        }
        return ResponseEntity.badRequest().body(new APIResponse<>(false, "Validation failed", null, errors, request.getRequestURI()));
    }
}