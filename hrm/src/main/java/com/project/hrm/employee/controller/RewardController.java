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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
                    @ApiResponse(responseCode = "200", description = "Reward created successfully", content = @Content(schema = @Schema(implementation = RewardDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<RewardDTO>> create(@Valid @RequestBody RewardCreateDTO dto, HttpServletRequest request) {
        RewardDTO result = rewardService.createReward(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Create reward successfully", result, null, request.getRequestURI()));
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
                    @ApiResponse(responseCode = "200", description = "Reward updated successfully", content = @Content(schema = @Schema(implementation = RewardDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<RewardDTO>> update(@Valid @RequestBody RewardUpdateDTO dto, HttpServletRequest request) {
        RewardDTO result = rewardService.updateReward(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Update reward successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Reward by ID",
            parameters = @Parameter(name = "id", description = "Reward ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reward found", content = @Content(schema = @Schema(implementation = RewardDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<RewardDTO>> getById(@PathVariable Integer id, HttpServletRequest request) {
        RewardDTO result = rewardService.getDTO(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Get reward successfully", result, null, request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Reward by ID",
            parameters = @Parameter(name = "id", description = "Reward ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reward deleted successfully")
            }
    )
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id, HttpServletRequest request) {
        rewardService.deleteReward(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Delete reward successfully", null, null, request.getRequestURI()));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(
            summary = "Get Rewards by Employee ID and Date Range",
            description = "Retrieve rewards for an employee within a specific date range",
            parameters = {
                    @Parameter(name = "employeeId", description = "Employee ID", required = true),
                    @Parameter(name = "startDate", description = "Start date of the range", required = true),
                    @Parameter(name = "endDate", description = "End date of the range", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Rewards found for employee", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RewardDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<RewardDTO>>> getRewardByEmployeeIdAndDate(
            @PathVariable Integer employeeId,
            @RequestParam String startDate,
            @RequestParam String endDate,
            HttpServletRequest request) {
        List<RewardDTO> result = rewardService.getRewardByEmployeeIdAndDate(
                employeeId, LocalDateTime.parse(startDate), LocalDateTime.parse(endDate));
        return ResponseEntity.ok(new APIResponse<>(true, "Get rewards by employee successfully", result, null, request.getRequestURI()));
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
                    @Parameter(name = "startDate", description = "Start date of the range", required = true),
                    @Parameter(name = "endDate", description = "End date of the range", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Batch rewards retrieved", content = @Content(schema = @Schema(implementation = Map.class)))
            }
    )
    public ResponseEntity<APIResponse<Map<Integer, List<RewardDTO>>>> getBatchRewards(
            @RequestBody List<Integer> employeeIds,
            @RequestParam String startDate,
            @RequestParam String endDate,
            HttpServletRequest request) {
        Map<Integer, List<RewardDTO>> result = rewardService.getBatchRewards(
                employeeIds, LocalDateTime.parse(startDate), LocalDateTime.parse(endDate));
        return ResponseEntity.ok(new APIResponse<>(true, "Get batch rewards successfully", result, null, request.getRequestURI()));
    }
}