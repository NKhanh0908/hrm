package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.trainingProgramDTO.TrainingProgramCreateDTO;
import com.project.hrm.dto.trainingProgramDTO.TrainingProgramDTO;
import com.project.hrm.dto.trainingProgramDTO.TrainingProgramFilter;
import com.project.hrm.dto.trainingProgramDTO.TrainingProgramUpdateDTO;
import com.project.hrm.services.TrainingProgramService;
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

import java.util.List;

@RestController
@RequestMapping("/training-program")
@RequiredArgsConstructor
@Tag(name = "Training Program Controller", description = "Manage training programs and related operations")
public class TrainingProgramController {
    private final TrainingProgramService trainingProgramService;

    @PostMapping
    @Operation(summary = "Create a new training program",
            description = "Creates a new training program entry",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Training program creation data", required = true,
                    content = @Content(schema = @Schema(implementation = TrainingProgramCreateDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Training program created successfully",
                            content = @Content(schema = @Schema(implementation = TrainingProgramDTO.class)))
            })
    public ResponseEntity<APIResponse<TrainingProgramDTO>> create(
            @RequestBody TrainingProgramCreateDTO dto,
            HttpServletRequest request) {

        TrainingProgramDTO result = trainingProgramService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(true, "Training program created successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(summary = "Update a training program",
            description = "Updates an existing training program based on provided data. Only non-null fields will be changed.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Training program update data", required = true,
                    content = @Content(schema = @Schema(implementation = TrainingProgramUpdateDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Training program updated successfully",
                            content = @Content(schema = @Schema(implementation = TrainingProgramDTO.class)))
            })
    public ResponseEntity<APIResponse<TrainingProgramDTO>> update(
            @RequestBody TrainingProgramUpdateDTO dto,
            HttpServletRequest request) {
        TrainingProgramDTO result = trainingProgramService.update(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Training program updated successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get training program by ID",
            description = "Retrieve detailed information of a training program by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Training program retrieved successfully",
                            content = @Content(schema = @Schema(implementation = TrainingProgramDTO.class)))
            })
    public ResponseEntity<APIResponse<TrainingProgramDTO>> getById(
            @PathVariable Integer id,
            HttpServletRequest request) {

        TrainingProgramDTO result = trainingProgramService.getDTOById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Training program retrieved successfully", result, null, request.getRequestURI()));
    }

    @PostMapping("/filter")
    @Operation(summary = "Filter training programs",
            description = "Filter training programs based on multiple criteria with pagination",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Filter data", required = true,
                    content = @Content(schema = @Schema(implementation = TrainingProgramFilter.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Training programs filtered successfully",
                            content = @Content(schema = @Schema(implementation = PageDTO.class)))
            })
    public ResponseEntity<APIResponse<PageDTO<TrainingProgramDTO>>> filter(
            @RequestBody TrainingProgramFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        PageDTO<TrainingProgramDTO> result = trainingProgramService.filter(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Training programs filtered successfully", result, null, request.getRequestURI()));
    }
}
