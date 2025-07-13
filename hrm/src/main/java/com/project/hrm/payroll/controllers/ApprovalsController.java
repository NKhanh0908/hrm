package com.project.hrm.payroll.controllers;

import com.project.hrm.common.response.APIResponse;
import com.project.hrm.common.response.PageDTO;
import com.project.hrm.payroll.dto.approvalsDTO.ApprovalsCreateDTO;
import com.project.hrm.payroll.dto.approvalsDTO.ApprovalsDTO;
import com.project.hrm.payroll.dto.approvalsDTO.ApprovalsFilter;
import com.project.hrm.payroll.dto.approvalsDTO.ApprovalsUpdateDTO;
import com.project.hrm.payroll.services.ApprovalsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/approvals")
@RequiredArgsConstructor
@Tag(name = "Approvals Controller", description = "Manage payroll approval records")
public class ApprovalsController {

    private final ApprovalsService approvalsService;

    @PostMapping
    @Operation(
            summary = "Create new approval",
            description = "Create a new payroll approval record",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Approval creation data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ApprovalsCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Approval created successfully", content = @Content(schema = @Schema(implementation = ApprovalsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<ApprovalsDTO>> create(@RequestBody ApprovalsCreateDTO dto,
                                                            HttpServletRequest request) {
        ApprovalsDTO result = approvalsService.create(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Create Approval successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update approval",
            description = "Update existing payroll approval record",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Approval update data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ApprovalsUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Approval updated successfully", content = @Content(schema = @Schema(implementation = ApprovalsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<ApprovalsDTO>> update(@RequestBody ApprovalsUpdateDTO dto,
                                                            HttpServletRequest request) {
        ApprovalsDTO result = approvalsService.update(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Update Approval successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get approval by ID",
            parameters = {
                    @Parameter(name = "id", description = "Approval ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Approval found", content = @Content(schema = @Schema(implementation = ApprovalsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<ApprovalsDTO>> getById(@PathVariable Integer id,
                                                             HttpServletRequest request) {
        ApprovalsDTO result = approvalsService.getById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Get Approval by ID successfully", result, null, request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete approval by ID",
            parameters = {
                    @Parameter(name = "id", description = "Approval ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Approval deleted successfully")
            }
    )
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id,
                                                    HttpServletRequest request) {
        approvalsService.delete(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Delete Approval successfully", null, null, request.getRequestURI()));
    }

    @GetMapping("/filter")
    @Operation(
            summary = "Filter approvals",
            description = "Filter approvals by various conditions",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Filtered approvals list",
                            content = @Content(schema = @Schema(implementation = PageDTO.class))
                    )
            }
    )
    public ResponseEntity<APIResponse<PageDTO<ApprovalsDTO>>> filter(
            @ParameterObject @ModelAttribute ApprovalsFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        PageDTO<ApprovalsDTO> result = approvalsService.filter(filter, page, size);
        return ResponseEntity.ok(
                new APIResponse<>(true, "Filter Approvals successfully", result, null, request.getRequestURI())
        );
    }

    @GetMapping("/filter-by-date")
    @Operation(
            summary = "Filter approvals by approval date range",
            parameters = {
                    @Parameter(name = "fromDate", description = "Start of approval date range", required = false),
                    @Parameter(name = "toDate", description = "End of approval date range", required = false),
                    @Parameter(name = "page", description = "Page number", required = false),
                    @Parameter(name = "size", description = "Page size", required = false)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filtered approvals list", content = @Content(schema = @Schema(implementation = PageDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PageDTO<ApprovalsDTO>>> filterByDateRange(
            @RequestParam(required = false) LocalDateTime fromDate,
            @RequestParam(required = false) LocalDateTime toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request
    ) {
        PageDTO<ApprovalsDTO> result = approvalsService.filterByApprovalDateRange(fromDate, toDate, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter approvals by approvalDate successfully", result, null, request.getRequestURI()));
    }
}
