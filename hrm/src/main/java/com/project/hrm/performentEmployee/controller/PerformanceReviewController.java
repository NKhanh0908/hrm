package com.project.hrm.performentEmployee.controller;

import com.project.hrm.common.response.APIResponse;
import com.project.hrm.common.response.PageDTO;
import com.project.hrm.performentEmployee.dto.performanceReviewDTO.PerformanceReviewCreateDTO;
import com.project.hrm.performentEmployee.dto.performanceReviewDTO.PerformanceReviewDTO;
import com.project.hrm.performentEmployee.dto.performanceReviewDTO.PerformanceReviewFilter;
import com.project.hrm.performentEmployee.dto.performanceReviewDTO.PerformanceReviewUpdateDTO;
import com.project.hrm.performentEmployee.service.PerformanceReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/performance-review")
@RequiredArgsConstructor
@Tag(name = "Performance Review Controller", description = "Manage employee performance reviews and related actions")
public class PerformanceReviewController {
    private final PerformanceReviewService performanceReviewService;

    @PostMapping
    @Operation(
            summary = "Create performance review",
            description = "Create a new performance review entry.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Performance review creation data", required = true,
                    content = @Content(schema = @Schema(implementation = PerformanceReviewCreateDTO.class))
            ),
            responses = @ApiResponse(responseCode = "201", description = "Created performance review successfully",
                    content = @Content(schema = @Schema(implementation = PerformanceReviewDTO.class)))
    )
    public ResponseEntity<APIResponse<PerformanceReviewDTO>> create(
            @RequestBody PerformanceReviewCreateDTO dto,
            HttpServletRequest request) {
        PerformanceReviewDTO result = performanceReviewService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(true, "Created performance review successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update performance review",
            description = "Update existing performance review by ID.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Performance review update data", required = true,
                    content = @Content(schema = @Schema(implementation = PerformanceReviewUpdateDTO.class))
            ),
            responses = @ApiResponse(responseCode = "200", description = "Updated performance review successfully",
                    content = @Content(schema = @Schema(implementation = PerformanceReviewDTO.class)))
    )
    public ResponseEntity<APIResponse<PerformanceReviewDTO>> update(
            @RequestBody PerformanceReviewUpdateDTO dto,
            HttpServletRequest request) {
        PerformanceReviewDTO result = performanceReviewService.update(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Updated performance review successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get performance review by ID",
            description = "Retrieve performance review details by ID.",
            parameters = @Parameter(name = "id", description = "ID of the performance review", required = true),
            responses = @ApiResponse(responseCode = "200", description = "Successfully retrieved performance review",
                    content = @Content(schema = @Schema(implementation = PerformanceReviewDTO.class)))
    )
    public ResponseEntity<APIResponse<PerformanceReviewDTO>> getById(
            @PathVariable Integer id,
            HttpServletRequest request) {
        PerformanceReviewDTO result = performanceReviewService.getDTOById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Retrieved performance review successfully", result, null, request.getRequestURI()));
    }

    @PutMapping("/update-status")
    @Operation(
            summary = "Update performance review status",
            description = "Update status of a performance review by ID.",
            parameters = {
                    @Parameter(name = "id", description = "Performance review ID", required = true),
                    @Parameter(name = "status", description = "New status", required = true)
            },
            responses = @ApiResponse(responseCode = "200", description = "Updated status successfully",
                    content = @Content(schema = @Schema(implementation = PerformanceReviewDTO.class)))
    )
    public ResponseEntity<APIResponse<PerformanceReviewDTO>> updateStatus(
            @RequestParam Integer id,
            @RequestParam String status,
            HttpServletRequest request) {
        PerformanceReviewDTO result = performanceReviewService.updateStatus(id, status);
        return ResponseEntity.ok(new APIResponse<>(true, "Updated status successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/filter")
    @Operation(
            summary = "Filter performance reviews",
            description = "Filter performance reviews by multiple criteria with pagination.",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Filtered performance reviews successfully",
                    content = @Content(schema = @Schema(implementation = PageDTO.class))
            )
    )
    public ResponseEntity<APIResponse<PageDTO<PerformanceReviewDTO>>> filter(
            @ParameterObject @ModelAttribute PerformanceReviewFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        PageDTO<PerformanceReviewDTO> result = performanceReviewService.filter(filter, page, size);
        return ResponseEntity.ok(
                new APIResponse<>(true, "Filtered performance reviews successfully", result, null, request.getRequestURI())
        );
    }
}
