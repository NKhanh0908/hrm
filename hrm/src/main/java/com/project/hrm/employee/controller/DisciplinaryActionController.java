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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
                    @ApiResponse(responseCode = "200", description = "Disciplinary action created successfully", content = @Content(schema = @Schema(implementation = DisciplinaryActionDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<DisciplinaryActionDTO>> create(@Valid @RequestBody DisciplinaryActionCreateDTO dto, HttpServletRequest request) {
        DisciplinaryActionDTO result = disciplinaryActionService.createDisciplinaryAction(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Create disciplinary action successfully", result, null, request.getRequestURI()));
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
                    @ApiResponse(responseCode = "200", description = "Disciplinary action updated successfully", content = @Content(schema = @Schema(implementation = DisciplinaryActionDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<DisciplinaryActionDTO>> update(@Valid @RequestBody DisciplinaryActionUpdateDTO dto, HttpServletRequest request) {
        DisciplinaryActionDTO result = disciplinaryActionService.updateDisciplinaryAction(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Update disciplinary action successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Disciplinary Action by ID",
            parameters = @Parameter(name = "id", description = "Disciplinary Action ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Disciplinary action found", content = @Content(schema = @Schema(implementation = DisciplinaryActionDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<DisciplinaryActionDTO>> getById(@PathVariable Integer id, HttpServletRequest request) {
        DisciplinaryActionDTO result = disciplinaryActionService.getDTO(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Get disciplinary action successfully", result, null, request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Disciplinary Action by ID",
            parameters = @Parameter(name = "id", description = "Disciplinary Action ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Disciplinary action deleted successfully")
            }
    )
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id, HttpServletRequest request) {
        disciplinaryActionService.deleteDisciplinaryAction(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Delete disciplinary action successfully", null, null, request.getRequestURI()));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(
            summary = "Get Disciplinary Actions by Employee ID and Date Range",
            description = "Retrieve disciplinary actions for an employee within a specific date range",
            parameters = {
                    @Parameter(name = "employeeId", description = "Employee ID", required = true),
                    @Parameter(name = "startDate", description = "Start date of the range", required = true),
                    @Parameter(name = "endDate", description = "End date of the range", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Disciplinary actions found for employee", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DisciplinaryActionDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<DisciplinaryActionDTO>>> getDisciplinaryActionByEmployeeIdAndDate(
            @PathVariable Integer employeeId,
            @RequestParam String startDate,
            @RequestParam String endDate,
            HttpServletRequest request) {
        List<DisciplinaryActionDTO> result = disciplinaryActionService.getDisciplinaryActionByEmployeeIdAndDate(
                employeeId, LocalDateTime.parse(startDate), LocalDateTime.parse(endDate));
        return ResponseEntity.ok(new APIResponse<>(true, "Get disciplinary actions by employee successfully", result, null, request.getRequestURI()));
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
                    @Parameter(name = "startDate", description = "Start date of the range", required = true),
                    @Parameter(name = "endDate", description = "End date of the range", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Batch disciplinary actions retrieved", content = @Content(schema = @Schema(implementation = Map.class)))
            }
    )
    public ResponseEntity<APIResponse<Map<Integer, List<DisciplinaryActionDTO>>>> getBatchDisciplinaryActions(
            @RequestBody List<Integer> employeeIds,
            @RequestParam String startDate,
            @RequestParam String endDate,
            HttpServletRequest request) {
        Map<Integer, List<DisciplinaryActionDTO>> result = disciplinaryActionService.getBatchDisciplinaryActions(
                employeeIds, LocalDateTime.parse(startDate), LocalDateTime.parse(endDate));
        return ResponseEntity.ok(new APIResponse<>(true, "Get batch disciplinary actions successfully", result, null, request.getRequestURI()));
    }
}