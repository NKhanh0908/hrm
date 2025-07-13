package com.project.hrm.employee.controller;

import com.project.hrm.common.response.APIResponse;
import com.project.hrm.employee.dto.rewardDTO.RewardCreateDTO;
import com.project.hrm.employee.dto.rewardDTO.RewardDTO;
import com.project.hrm.employee.dto.rewardDTO.RewardUpdateDTO;
import com.project.hrm.employee.service.RewardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rewards")
@RequiredArgsConstructor
@Tag(name = "Reward Controller", description = "Manage reward records")
public class RewardController {

    private final RewardService rewardService;

    @PostMapping
    @Operation(
            summary = "Create Reward",
            description = "Create a new reward record",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Reward creation data",
                    content = @Content(schema = @Schema(implementation = RewardCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reward created successfully", content = @Content(schema = @Schema(implementation = RewardDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    public ResponseEntity<APIResponse<RewardDTO>> create(@Valid @RequestBody RewardCreateDTO dto, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return handleValidationErrors(result, request);
        }
        RewardDTO created = rewardService.createReward(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Reward created successfully", created, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update Reward",
            description = "Update an existing reward record",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Reward update data",
                    content = @Content(schema = @Schema(implementation = RewardUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reward updated successfully", content = @Content(schema = @Schema(implementation = RewardDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    public ResponseEntity<APIResponse<RewardDTO>> update(@Valid @RequestBody RewardUpdateDTO dto, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return handleValidationErrors(result, request);
        }
        RewardDTO updated = rewardService.updateReward(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Reward updated successfully", updated, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Reward by ID",
            parameters = @Parameter(name = "id", description = "Reward ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reward found", content = @Content(schema = @Schema(implementation = RewardDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid ID")
            }
    )
    public ResponseEntity<APIResponse<RewardDTO>> getById(@PathVariable @Positive(message = "Reward ID must be a positive number") Integer id, HttpServletRequest request) {
        RewardDTO result = rewardService.getDTO(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Reward retrieved successfully", result, null, request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Reward by ID",
            parameters = @Parameter(name = "id", description = "Reward ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reward deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid ID")
            }
    )
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable @Positive(message = "Reward ID must be a positive number") Integer id, HttpServletRequest request) {
        rewardService.deleteReward(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Reward deleted successfully", null, null, request.getRequestURI()));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(
            summary = "Get Rewards by Employee ID and Date Range",
            description = "Retrieve rewards for an employee within a specific date range",
            parameters = {
                    @Parameter(name = "employeeId", description = "Employee ID", required = true),
                    @Parameter(name = "startDate", description = "Start date of the range (ISO format: yyyy-MM-dd'T'HH:mm:ss)", required = true),
                    @Parameter(name = "endDate", description = "End date of the range (ISO format: yyyy-MM-dd'T'HH:mm:ss)", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Rewards found for employee", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RewardDTO.class)))),
                    @ApiResponse(responseCode = "400", description = "Invalid employee ID or date range")
            }
    )
    public ResponseEntity<APIResponse<List<RewardDTO>>> getRewardByEmployeeIdAndDate(
            @PathVariable @Positive(message = "Employee ID must be a positive number") Integer employeeId,
            @RequestParam @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}", message = "Start date must be in ISO format (yyyy-MM-dd'T'HH:mm:ss)") String startDate,
            @RequestParam @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}", message = "End date must be in ISO format (yyyy-MM-dd'T'HH:mm:ss)") String endDate,
            HttpServletRequest request) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        if (end.isBefore(start)) {
            List<String> errors = new ArrayList<>();
            errors.add("End date must be after or equal to start date");
            return ResponseEntity.badRequest().body(new APIResponse<>(false, "Validation failed", null, errors, request.getRequestURI()));
        }
        List<RewardDTO> result = rewardService.getRewardByEmployeeIdAndDate(employeeId, start, end);
        return ResponseEntity.ok(new APIResponse<>(true, "Rewards retrieved successfully", result, null, request.getRequestURI()));
    }

    @PostMapping("/batch")
    @Operation(
            summary = "Get Batch Rewards",
            description = "Retrieve rewards for multiple employees within a date range",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "List of employee IDs",
                    content = @Content(schema = @Schema(implementation = List.class))
            ),
            parameters = {
                    @Parameter(name = "startDate", description = "Start date of the range (ISO format: yyyy-MM-dd'T'HH:mm:ss)", required = true),
                    @Parameter(name = "endDate", description = "End date of the range (ISO format: yyyy-MM-dd'T'HH:mm:ss)", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Batch rewards retrieved", content = @Content(schema = @Schema(implementation = Map.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid employee IDs or date range")
            }
    )
    public ResponseEntity<APIResponse<Map<Integer, List<RewardDTO>>>> getBatchRewards(
            @RequestBody @NotEmpty(message = "Employee IDs list cannot be empty") List<@Positive(message = "Employee ID must be a positive number") Integer> employeeIds,
            @RequestParam @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?", message = "Start date must be in ISO format (yyyy-MM-dd'T'HH:mm:ss[.SSS...])") String startDate,
            @RequestParam @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?", message = "End date must be in ISO format (yyyy-MM-dd'T'HH:mm:ss[.SSS...])") String endDate,
            HttpServletRequest request) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        if (end.isBefore(start)) {
            List<String> errors = new ArrayList<>();
            errors.add("End date must be after or equal to start date");
            return ResponseEntity.badRequest().body(new APIResponse<>(false, "Validation failed", null, errors, request.getRequestURI()));
        }
        Map<Integer, List<RewardDTO>> result = rewardService.getBatchRewards(employeeIds, start, end);
        return ResponseEntity.ok(new APIResponse<>(true, "Batch rewards retrieved successfully", result, null, request.getRequestURI()));
    }

    private <T> ResponseEntity<APIResponse<T>> handleValidationErrors(BindingResult result, HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.add(error.getField());
        }
        return ResponseEntity.badRequest().body(new APIResponse<>(false, "Validation failed", null, errors, request.getRequestURI()));
    }
}