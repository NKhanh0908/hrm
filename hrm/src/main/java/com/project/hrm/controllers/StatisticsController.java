package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.statisticsDTO.*;
import com.project.hrm.enums.ContractStatus;
import com.project.hrm.services.StatisticsService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
@Tag(name = "Statistics Controller", description = "Statistics hrm")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @Operation(
            summary = "Get total employees by department",
            description = "Retrieve the total number of employees for each department.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successfully retrieved total employees by department",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TotalEmployeeByDepartment.class)))
                    )
            }
    )
    @GetMapping("/employees-by-department")
    public ResponseEntity<APIResponse<List<TotalEmployeeByDepartment>>> getTotalEmployeeByDepartment(HttpServletRequest request) {
        List<TotalEmployeeByDepartment> result = statisticsService.getTotalEmployeeByDepartment();
        return ResponseEntity.ok(new APIResponse<>(true, "Successfully retrieved", result, null, request.getRequestURI()));
    }

    @Operation(
            summary = "Get total employees by role",
            description = "Retrieve the total number of employees for each role.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successfully retrieved total employees by role",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TotalEmployeeByRole.class)))
                    )
            }
    )
    @GetMapping("/employees-by-role")
    public ResponseEntity<APIResponse<List<TotalEmployeeByRole>>> getTotalEmployeeByRole(HttpServletRequest request) {
        List<TotalEmployeeByRole> result = statisticsService.getTotalEmployeeByRole();
        return ResponseEntity.ok(new APIResponse<>(true, "Successfully retrieved", result, null, request.getRequestURI()));
    }

    @Operation(
            summary = "Get total employees by department and role",
            description = "Retrieve the total number of employees for each department and role combination.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successfully retrieved total employees by department and role",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TotalEmployeeByDepartmentAndRole.class)))
                    )
            }
    )
    @GetMapping("/employees-by-department-role")
    public ResponseEntity<APIResponse<List<TotalEmployeeByDepartmentAndRole>>> getTotalEmployeeByDepartmentAndRole(HttpServletRequest request) {
        List<TotalEmployeeByDepartmentAndRole> result = statisticsService.getTotalEmployeeByDepartmentAndRole();
        return ResponseEntity.ok(new APIResponse<>(true, "Successfully retrieved", result, null, request.getRequestURI()));
    }

    @Operation(
            summary = "Get total contracts by status",
            description = "Retrieve the total number of contracts for each status.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successfully retrieved total contracts by status",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TotalContractByStatus.class)))
                    )
            }
    )
    @GetMapping("/contracts-by-status")
    public ResponseEntity<APIResponse<List<TotalContractByStatus>>> getTotalContractByStatus(HttpServletRequest request) {
        List<TotalContractByStatus> result = statisticsService.getTotalContractByStatus();
        return ResponseEntity.ok(new APIResponse<>(true, "Successfully retrieved", result, null, request.getRequestURI()));
    }

    @Operation(
            summary = "Get total contracts by status and salary",
            description = "Retrieve the total number of contracts by status along with total salary.",
            parameters = {
                    @Parameter(name = "status", description = "Contract Status", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successfully retrieved total contracts by status and salary",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TotalContractByStatusAndSalary.class)))
                    )
            }
    )
    @GetMapping("/contracts-by-status-salary")
    public ResponseEntity<APIResponse<List<TotalContractByStatusAndSalary>>> getTotalContractByStatusAndSalary(
            @RequestParam ContractStatus status, HttpServletRequest request) {

        List<TotalContractByStatusAndSalary> result = statisticsService.getTotalContractByStatusAndSalary(status);
        return ResponseEntity.ok(new APIResponse<>(true, "Successfully retrieved", result, null, request.getRequestURI()));
    }
}
