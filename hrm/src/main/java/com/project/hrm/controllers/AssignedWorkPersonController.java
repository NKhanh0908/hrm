package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.assignedWorkPersonDTO.AssignedWorkPersonCreateDTO;
import com.project.hrm.dto.assignedWorkPersonDTO.AssignedWorkPersonDTO;
import com.project.hrm.dto.assignedWorkPersonDTO.AssignedWorkPersonUpdateDTO;
import com.project.hrm.services.AssignedWorkPersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assigned-work-person")
@Tag(name = "Assigned Work Person Controller", description = "Manage assignments of employees to work items")
public class AssignedWorkPersonController {
    private final AssignedWorkPersonService assignedWorkPersonService;

    @PostMapping
    @Operation(summary = "Create assignment",
            description = "Assign an employee to a work item",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Assignment creation data", required = true,
                    content = @Content(schema = @Schema(implementation = AssignedWorkPersonCreateDTO.class))
            ),
            responses = @ApiResponse(responseCode = "201", description = "Assignment created",
                    content = @Content(schema = @Schema(implementation = AssignedWorkPersonDTO.class))))
    public ResponseEntity<APIResponse<AssignedWorkPersonDTO>> create(
            @RequestBody AssignedWorkPersonCreateDTO dto,
            HttpServletRequest request) {
        AssignedWorkPersonDTO result = assignedWorkPersonService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(true, "Assignment created successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(summary = "Update assignment",
            description = "Update fields of an existing assignment",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Assignment update data", required = true,
                    content = @Content(schema = @Schema(implementation = AssignedWorkPersonUpdateDTO.class))
            ),
            responses = @ApiResponse(responseCode = "200", description = "Assignment updated",
                    content = @Content(schema = @Schema(implementation = AssignedWorkPersonDTO.class))))
    public ResponseEntity<APIResponse<AssignedWorkPersonDTO>> update(
            @RequestBody AssignedWorkPersonUpdateDTO dto,
            HttpServletRequest request) {
        AssignedWorkPersonDTO result = assignedWorkPersonService.update(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Assignment updated successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get assignment by ID",
            description = "Retrieve assignment details by its ID",
            parameters = @Parameter(name = "id", description = "Assignment ID", required = true),
            responses = @ApiResponse(responseCode = "200", description = "Assignment retrieved",
                    content = @Content(schema = @Schema(implementation = AssignedWorkPersonDTO.class))))
    public ResponseEntity<APIResponse<AssignedWorkPersonDTO>> getById(
            @PathVariable Integer id,
            HttpServletRequest request) {
        AssignedWorkPersonDTO result = assignedWorkPersonService.getDtoById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Assignment retrieved successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/by-employee/{employeeId}")
    @Operation(summary = "Get assignments by employee",
            description = "Retrieve all assignments linked to the specified employee",
            parameters = @Parameter(name = "employeeId", description = "Employee ID", required = true),
            responses = @ApiResponse(responseCode = "200", description = "Assignments retrieved",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AssignedWorkPersonDTO.class)))))
    public ResponseEntity<APIResponse<List<AssignedWorkPersonDTO>>> getByEmployee(
            @PathVariable Integer employeeId,
            HttpServletRequest request) {
        List<AssignedWorkPersonDTO> results = assignedWorkPersonService.filterByEmployeeId(employeeId);
        return ResponseEntity.ok(new APIResponse<>(true, "Assignments for employee retrieved", results, null, request.getRequestURI()));
    }
}
