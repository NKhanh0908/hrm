package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.applyDTO.ApplyCreateDTO;
import com.project.hrm.dto.applyDTO.ApplyDTO;
import com.project.hrm.dto.applyDTO.ApplyFilter;
import com.project.hrm.dto.applyDTO.ApplyUpdateDTO;
import com.project.hrm.enums.ApplyStatus;
import com.project.hrm.services.ApplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apply")
@RequiredArgsConstructor
@Tag(name = "Apply Controller", description = "Apply manager")
public class ApplyController {
    private final ApplyService applyService;

    @PostMapping
    @Operation(
            summary = "Create Apply",
            description = "Apply in Job upload by Recruitment",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Apply create information",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ApplyCreateDTO.class))
            ),

            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Create Apply successfully",
                            content = @Content(schema = @Schema(implementation = ApplyDTO.class))
                    )
            }
    )
    public ResponseEntity<APIResponse<ApplyDTO>> create(@RequestBody ApplyCreateDTO applyCreateDTO){
        ApplyDTO applyDTO = applyService.create(applyCreateDTO);
        return ResponseEntity.ok(new APIResponse<>(true, "Create Department successfully", applyDTO));
    }

    @PutMapping
    @Operation(
            summary = "Update Apply",
            description = "Update Apply in Job upload by Recruitment",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Apply update information",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ApplyUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Update Apply successfully",
                            content = @Content(schema = @Schema(implementation = ApplyDTO.class))
                    )
            }
    )
    public ResponseEntity<APIResponse<ApplyDTO>> update(@RequestBody ApplyUpdateDTO applyUpdateDTO){
        ApplyDTO applyDTO = applyService.update(applyUpdateDTO);
        return ResponseEntity.ok(new APIResponse<>(true, "Create Department successfully", applyDTO));
    }

    @GetMapping
    @Operation(
            summary = "Find Apply by id",
            description = "Find detail Apply by Apply id",
            parameters = {
                    @Parameter(name = "id", description = "Apply ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successfully retrieved Apply",
                            content = @Content(schema = @Schema(implementation = ApplyDTO.class))
                    )
            }
    )
    public ResponseEntity<APIResponse<ApplyDTO>> getById(@RequestParam Integer id){
        ApplyDTO applyDTO = applyService.getById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Create Department successfully", applyDTO));
    }

    @PostMapping("/filter")
    @Operation(
            summary = "Filter applies",
            description = "Filter apply by period of time apply, status, position, or recruitmentID with pagination.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Filter conditions",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ApplyFilter.class))
            ),
            parameters = {
                    @Parameter(name = "page", description = "Page number (0-based)", required = false, example = "0"),
                    @Parameter(name = "size", description = "Page size", required = false, example = "10")
            },
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Filtered apply list",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApplyDTO.class)))
                    )
            }
    )
    public ResponseEntity<APIResponse<List<ApplyDTO>>> filter(@RequestBody ApplyFilter applyFilter,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        List<ApplyDTO> applyDTOList = applyService.filter(applyFilter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter Apply successfully", applyDTOList));
    }

    @PutMapping("/{id}/status")
    @Operation(
            summary = "Update Apply Status",
            description = "Set a new status on an existing application",
            parameters = {
                    @Parameter(name = "id", description = "Application ID", required = true),
                    @Parameter(name = "status",
                            description = "New status",
                            schema = @Schema(implementation = ApplyStatus.class))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status updated successfully",
                            content = @Content(schema = @Schema(implementation = ApplyDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Application not found")
            }
    )
    public ResponseEntity<APIResponse<ApplyDTO>> updateStatus(
            @PathVariable Integer id,
            @RequestParam ApplyStatus status) {
        ApplyDTO updated = applyService.updateStatus(id, status);
        return ResponseEntity.ok(new APIResponse<>(true,
                "Apply status updated to " + status, updated));
    }

}
