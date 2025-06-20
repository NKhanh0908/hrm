package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.trainingEnrollmentDTO.TrainingEnrollmentCreateDTO;
import com.project.hrm.dto.trainingEnrollmentDTO.TrainingEnrollmentDTO;
import com.project.hrm.dto.trainingEnrollmentDTO.TrainingEnrollmentFilter;
import com.project.hrm.services.TrainingEnrollmentService;
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
@RequestMapping("/training-enrollment")
@RequiredArgsConstructor
@Tag(name = "Training Enrollment Controller", description = "Training enrollment management")
public class TrainingEnrollmentController {
    private final TrainingEnrollmentService trainingEnrollmentService;

    @Operation(summary = "Create training enrollment", description = "Create a new training enrollment",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Training enrollment create DTO",
                    required = true,
                    content = @Content(schema = @Schema(implementation = TrainingEnrollmentCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Training enrollment created successfully",
                            content = @Content(schema = @Schema(implementation = TrainingEnrollmentDTO.class)))
            }
    )
    @PostMapping
    public ResponseEntity<APIResponse<TrainingEnrollmentDTO>> create(
            @RequestBody @Valid TrainingEnrollmentCreateDTO dto,
            HttpServletRequest request) {
        TrainingEnrollmentDTO result = trainingEnrollmentService.create(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Create TrainingEnrollment successfully", result, null, request.getRequestURI()));
    }

    @Operation(summary = "Get training enrollment by ID", description = "Retrieve a training enrollment by its ID",
            parameters = @Parameter(name = "id", description = "ID of the training enrollment", required = true),
            responses = @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = TrainingEnrollmentDTO.class)))
    )
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<TrainingEnrollmentDTO>> getById(
            @PathVariable Integer id,
            HttpServletRequest request) {
        TrainingEnrollmentDTO dto = trainingEnrollmentService.getDTOById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Get TrainingEnrollment successfully", dto, null, request.getRequestURI()));
    }

    @Operation(summary = "Filter training enrollments", description = "Filter training enrollments using provided criteria with pagination",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Filter conditions for training enrollments",
                    required = true,
                    content = @Content(schema = @Schema(implementation = TrainingEnrollmentFilter.class))
            ),
            parameters = {
                    @Parameter(name = "page", description = "Page number (0-based)", example = "0"),
                    @Parameter(name = "size", description = "Page size", example = "10")
            },
            responses = @ApiResponse(responseCode = "200", description = "Filtered training enrollment list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TrainingEnrollmentDTO.class))))
    )
    @PostMapping("/filter")
    public ResponseEntity<APIResponse<List<TrainingEnrollmentDTO>>> filter(
            @RequestBody TrainingEnrollmentFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        List<TrainingEnrollmentDTO> results = trainingEnrollmentService.filter(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter TrainingEnrollments successfully", results, null, request.getRequestURI()));
    }
}
