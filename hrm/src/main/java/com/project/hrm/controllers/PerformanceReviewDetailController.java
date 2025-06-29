package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.performanceReviewDetailDTO.PerformanceReviewDetailCreateDTO;
import com.project.hrm.dto.performanceReviewDetailDTO.PerformanceReviewDetailDTO;
import com.project.hrm.dto.performanceReviewDetailDTO.PerformanceReviewDetailFilter;
import com.project.hrm.dto.performanceReviewDetailDTO.PerformanceReviewDetailUpdateDTO;
import com.project.hrm.services.PerformanceReviewDetailService;
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

import java.util.List;

@RestController
@RequestMapping("/performance-review-detail")
@RequiredArgsConstructor
@Tag(name = "Performance Review Detail Controller", description = "Manage individual performance review criteria")
public class PerformanceReviewDetailController {
    private final PerformanceReviewDetailService service;

    @PostMapping
    @Operation(
            summary = "Create performance review detail",
            description = "Create a new performance review detail for a specific performance review",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Performance review detail creation data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PerformanceReviewDetailCreateDTO.class))
            ),
            responses = @ApiResponse(responseCode = "200", description = "Created successfully", content = @Content(schema = @Schema(implementation = PerformanceReviewDetailDTO.class)))
    )
    public ResponseEntity<APIResponse<PerformanceReviewDetailDTO>> create(
            @RequestBody PerformanceReviewDetailCreateDTO dto,
            HttpServletRequest request
    ) {
        PerformanceReviewDetailDTO result = service.create(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Created successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update performance review detail",
            description = "Update an existing performance review detail by ID",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Performance review detail update data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PerformanceReviewDetailUpdateDTO.class))
            ),
            responses = @ApiResponse(responseCode = "200", description = "Updated successfully", content = @Content(schema = @Schema(implementation = PerformanceReviewDetailDTO.class)))
    )
    public ResponseEntity<APIResponse<PerformanceReviewDetailDTO>> update(
            @RequestBody PerformanceReviewDetailUpdateDTO dto,
            HttpServletRequest request
    ) {
        PerformanceReviewDetailDTO result = service.update(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Updated successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get performance review detail by ID",
            description = "Retrieve a specific performance review detail by its ID",
            parameters = @Parameter(name = "id", description = "Review detail ID", required = true),
            responses = @ApiResponse(responseCode = "200", description = "Retrieved successfully", content = @Content(schema = @Schema(implementation = PerformanceReviewDetailDTO.class)))
    )
    public ResponseEntity<APIResponse<PerformanceReviewDetailDTO>> getById(
            @PathVariable Integer id,
            HttpServletRequest request
    ) {
        PerformanceReviewDetailDTO result = service.getDTOById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Retrieved successfully", result, null, request.getRequestURI()));
    }

    @PostMapping("/filter")
    @Operation(
            summary = "Filter performance review details",
            description = "Filter performance review detail records based on criteria",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Filter conditions",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PerformanceReviewDetailFilter.class))
            ),
            parameters = {
                    @Parameter(name = "page", description = "Page number", required = false, example = "0"),
                    @Parameter(name = "size", description = "Page size", required = false, example = "10")
            },
            responses = @ApiResponse(responseCode = "200", description = "Filtered successfully", content = @Content(schema = @Schema(implementation = PageDTO.class)))
    )
    public ResponseEntity<APIResponse<PageDTO<PerformanceReviewDetailDTO>>> filter(
            @RequestBody PerformanceReviewDetailFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request
    ) {
        PageDTO<PerformanceReviewDetailDTO> results = service.filter(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filtered successfully", results, null, request.getRequestURI()));
    }
}
