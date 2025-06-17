package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.trainingSession.TrainingSessionCreateDTO;
import com.project.hrm.dto.trainingSession.TrainingSessionDTO;
import com.project.hrm.services.TrainingSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}