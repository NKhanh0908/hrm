package com.project.hrm.training.controller;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.PageDTO;
import com.project.hrm.training.dto.trainingSession.TrainingSessionCreateDTO;
import com.project.hrm.training.dto.trainingSession.TrainingSessionDTO;
import com.project.hrm.training.dto.trainingSession.TrainingSessionFilter;
import com.project.hrm.training.dto.trainingSession.TrainingSessionUpdateDTO;
import com.project.hrm.training.service.TrainingSessionService;
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
@RequestMapping("/training-session")
@RequiredArgsConstructor
@Tag(name = "Training Session Controller", description = "Manage training sessions and related operations")
public class TrainingSessionController {

    private final TrainingSessionService trainingSessionService;

    /**
     * Creates a new training session.
     *
     * @param dto     the training session creation data.
     * @param request HTTP servlet request.
     * @return created {@link TrainingSessionDTO}.
     */
    @PostMapping
    @Operation(summary = "Create a new training session",
            description = "Creates a new training session entry",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Training session creation data", required = true,
                    content = @Content(schema = @Schema(implementation = TrainingSessionCreateDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Training session created successfully",
                            content = @Content(schema = @Schema(implementation = TrainingSessionDTO.class)))
            })
    public ResponseEntity<APIResponse<TrainingSessionDTO>> create(
            @RequestBody TrainingSessionCreateDTO dto,
            HttpServletRequest request) {

        TrainingSessionDTO result = trainingSessionService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(true, "Training session created successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update training session",
            description = "Updates an existing training session based on provided fields. Only non-null fields will be applied.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Training session update data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = TrainingSessionUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Training session updated successfully",
                            content = @Content(schema = @Schema(implementation = TrainingSessionDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<TrainingSessionDTO>> update(
            @RequestBody TrainingSessionUpdateDTO dto,
            HttpServletRequest request) {
        TrainingSessionDTO result = trainingSessionService.update(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Training session updated successfully", result, null, request.getRequestURI()));
    }


    /**
     * Retrieves a training session by ID.
     *
     * @param id      the training session ID.
     * @param request HTTP servlet request.
     * @return the {@link TrainingSessionDTO}.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get training session by ID",
            description = "Retrieves a training session based on ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Training session found",
                            content = @Content(schema = @Schema(implementation = TrainingSessionDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Training session not found")
            })
    public ResponseEntity<APIResponse<TrainingSessionDTO>> getById(
            @PathVariable Integer id,
            HttpServletRequest request) {

        TrainingSessionDTO result = trainingSessionService.getDTOById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Training session retrieved successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/by-training-program/{trainingProgramId}")
    @Operation(summary = "Get all training sessions by training program ID",
            description = "Retrieves all training sessions associated with a specific training program.",
            parameters = @Parameter(name = "trainingProgramId", description = "ID of the training program", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of training sessions",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TrainingSessionDTO.class))))
            })
    public ResponseEntity<APIResponse<List<TrainingSessionDTO>>> getAllByTrainingProgramId(
            @PathVariable Integer trainingProgramId,
            HttpServletRequest request) {

        List<TrainingSessionDTO> result = trainingSessionService.getAllByTrainingProgramId(trainingProgramId);
        return ResponseEntity.ok(new APIResponse<>(true,
                "Training sessions by training program retrieved successfully",
                result, null, request.getRequestURI()));
    }

    @PostMapping("/filter")
    @Operation(summary = "Filter training sessions",
            description = "Filters training sessions based on location, cost, coordinator, etc.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Filter conditions",
                    required = true,
                    content = @Content(schema = @Schema(implementation = TrainingSessionFilter.class))),
            parameters = {
                    @Parameter(name = "page", description = "Page number (0-based)", required = false),
                    @Parameter(name = "size", description = "Page size", required = false)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filtered list of training sessions",
                            content = @Content(schema = @Schema(implementation = PageDTO.class)))
            })
    public ResponseEntity<APIResponse<PageDTO<TrainingSessionDTO>>> filterTrainingSessions(
            @RequestBody TrainingSessionFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        PageDTO<TrainingSessionDTO> result = trainingSessionService.filter(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true,
                "Filtered training sessions retrieved successfully",
                result, null, request.getRequestURI()));
    }
}