package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.trainingRequestDTO.TrainingRequestCreateDTO;
import com.project.hrm.dto.trainingRequestDTO.TrainingRequestDTO;
import com.project.hrm.dto.trainingRequestDTO.TrainingRequestFilter;
import com.project.hrm.services.TrainingRequestService;
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
@RequestMapping("/training-request")
@RequiredArgsConstructor
@Tag(name = "Training Request Controller", description = "Training request management")
public class TrainingRequestController {
    private final TrainingRequestService trainingRequestService;

    @Operation(summary = "Create training request", description = "Create a new training request",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "TrainingRequest creation DTO",
                    required = true,
                    content = @Content(schema = @Schema(implementation = TrainingRequestCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Training request created successfully",
                            content = @Content(schema = @Schema(implementation = TrainingRequestDTO.class)))
            }
    )
    @PostMapping
    public ResponseEntity<APIResponse<TrainingRequestDTO>> create(
            @RequestBody @Valid TrainingRequestCreateDTO dto,
            HttpServletRequest request) {
        TrainingRequestDTO result = trainingRequestService.create(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Create TrainingRequest successfully", result, null, request.getRequestURI()));
    }

    @Operation(summary = "Get training request by ID", description = "Retrieve a training request by its ID",
            parameters = @Parameter(name = "id", description = "ID of the training request", required = true),
            responses = @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = TrainingRequestDTO.class)))
    )
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<TrainingRequestDTO>> getById(
            @PathVariable Integer id,
            HttpServletRequest request) {
        TrainingRequestDTO dto = trainingRequestService.getDTOById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Get TrainingRequest successfully", dto, null, request.getRequestURI()));
    }

    @Operation(summary = "Filter training requests", description = "Filter training requests with criteria and pagination",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Training request filter object",
                    required = true,
                    content = @Content(schema = @Schema(implementation = TrainingRequestFilter.class))
            ),
            parameters = {
                    @Parameter(name = "page", description = "Page number (0-based)", example = "0"),
                    @Parameter(name = "size", description = "Page size", example = "10")
            },
            responses = @ApiResponse(responseCode = "200", description = "Filtered list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TrainingRequestDTO.class))))
    )
    @PostMapping("/filter")
    public ResponseEntity<APIResponse<List<TrainingRequestDTO>>> filter(
            @RequestBody TrainingRequestFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        List<TrainingRequestDTO> results = trainingRequestService.filter(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter TrainingRequests successfully", results, null, request.getRequestURI()));
    }
}
