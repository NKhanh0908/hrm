package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.feedbackEmployeeDTO.FeedbackEmployeeCreateDTO;
import com.project.hrm.dto.feedbackEmployeeDTO.FeedbackEmployeeDTO;
import com.project.hrm.dto.feedbackEmployeeDTO.FeedbackEmployeeFilter;
import com.project.hrm.dto.feedbackEmployeeDTO.FeedbackEmployeeUpdateDTO;
import com.project.hrm.services.FeedbackEmployeeService;
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
@RequestMapping("/feedback-employee")
@RequiredArgsConstructor
@Tag(name = "Feedback Employee Controller", description = "Manage feedbacks given to employees")
public class FeedbackEmployeeController {
    private final FeedbackEmployeeService feedbackEmployeeService;

    @PostMapping
    @Operation(summary = "Create employee feedback",
            description = "Submit feedback for an employee's performance review",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Feedback creation data", required = true,
                    content = @Content(schema = @Schema(implementation = FeedbackEmployeeCreateDTO.class))
            ),
            responses = @ApiResponse(responseCode = "201", description = "Feedback created",
                    content = @Content(schema = @Schema(implementation = FeedbackEmployeeDTO.class))))
    public ResponseEntity<APIResponse<FeedbackEmployeeDTO>> create(
            @RequestBody FeedbackEmployeeCreateDTO dto,
            HttpServletRequest request) {
        FeedbackEmployeeDTO result = feedbackEmployeeService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(true, "Feedback created successfully", result, null, request.getRequestURI()));
    }

    /**
     * Update an existing feedback entry.
     *
     * @param dto     the update DTO
     * @param request the HTTP servlet request
     * @return the updated {@link FeedbackEmployeeDTO}
     */
    @PutMapping
    @Operation(summary = "Update employee feedback",
            description = "Modify fields of an existing employee feedback",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Feedback update data", required = true,
                    content = @Content(schema = @Schema(implementation = FeedbackEmployeeUpdateDTO.class))
            ),
            responses = @ApiResponse(responseCode = "200", description = "Feedback updated",
                    content = @Content(schema = @Schema(implementation = FeedbackEmployeeDTO.class))))
    public ResponseEntity<APIResponse<FeedbackEmployeeDTO>> update(
            @RequestBody FeedbackEmployeeUpdateDTO dto,
            HttpServletRequest request) {
        FeedbackEmployeeDTO result = feedbackEmployeeService.update(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Feedback updated successfully", result, null, request.getRequestURI()));
    }

    /**
     * Retrieve a specific feedback by its ID.
     *
     * @param id      the feedback ID
     * @param request the HTTP servlet request
     * @return the {@link FeedbackEmployeeDTO}
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get feedback by ID",
            description = "Retrieve feedback details by its ID",
            parameters = @Parameter(name = "id", description = "Feedback ID", required = true),
            responses = @ApiResponse(responseCode = "200", description = "Feedback retrieved",
                    content = @Content(schema = @Schema(implementation = FeedbackEmployeeDTO.class))))
    public ResponseEntity<APIResponse<FeedbackEmployeeDTO>> getById(
            @PathVariable Integer id,
            HttpServletRequest request) {
        FeedbackEmployeeDTO result = feedbackEmployeeService.getDTOById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Feedback retrieved successfully", result, null, request.getRequestURI()));
    }

    /**
     * Filter employee feedback records based on criteria with pagination.
     *
     * @param filter  the filter conditions
     * @param page    zero-based page index
     * @param size    the page size
     * @param request the HTTP servlet request
     * @return list of {@link FeedbackEmployeeDTO}
     */
    @PostMapping("/filter")
    @Operation(summary = "Filter employee feedback",
            description = "Filter feedback records by reviewer, review ID, date range, etc.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Feedback filter conditions", required = true,
                    content = @Content(schema = @Schema(implementation = FeedbackEmployeeFilter.class))
            ),
            parameters = {
                    @Parameter(name = "page", description = "Page number (0-based)", example = "0"),
                    @Parameter(name = "size", description = "Page size", example = "10")
            },
            responses = @ApiResponse(responseCode = "200", description = "Filtered feedback list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = FeedbackEmployeeDTO.class))))
    )
    public ResponseEntity<APIResponse<List<FeedbackEmployeeDTO>>> filter(
            @RequestBody FeedbackEmployeeFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        List<FeedbackEmployeeDTO> results = feedbackEmployeeService.filter(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filtered feedback retrieved successfully", results, null, request.getRequestURI()));
    }
}
