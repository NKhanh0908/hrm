package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementFilter;
import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementsCreateDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementsDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentRequirementsUpdateDTO;
import com.project.hrm.enums.RecruitmentRequirementsStatus;
import com.project.hrm.services.RecruitmentRequirementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recruitment-requirements")
@RequiredArgsConstructor
@Tag(name = "Recruitment Requirements Controller", description = "Manage recruitment requirement configurations")
@SecurityRequirement(name = "bearerAuth")
public class RecruitmentRequirementsController {

    private final RecruitmentRequirementService recruitmentRequirementService;

    @PostMapping
    @Operation(
            summary = "Create recruitment requirement",
            description = "Create a new recruitment requirement template for a job position",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Recruitment requirement creation data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RecruitmentRequirementsCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Requirement created successfully",
                            content = @Content(schema = @Schema(implementation = RecruitmentRequirementsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<RecruitmentRequirementsDTO>> create(
            @RequestBody RecruitmentRequirementsCreateDTO dto) {
        RecruitmentRequirementsDTO result = recruitmentRequirementService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(true, "Requirement created successfully", result));
    }

    @PutMapping
    @Operation(
            summary = "Update recruitment requirement",
            description = "Update an existing recruitment requirement by ID",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Recruitment requirement update data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RecruitmentRequirementsUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Requirement updated successfully",
                            content = @Content(schema = @Schema(implementation = RecruitmentRequirementsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<RecruitmentRequirementsDTO>> update(
            @RequestBody RecruitmentRequirementsUpdateDTO dto) {
        RecruitmentRequirementsDTO result = recruitmentRequirementService.update(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Requirement updated successfully", result));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete recruitment requirement",
            description = "Deletes a recruitment requirement by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Requirement deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Requirement not found")
            }
    )
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id) {
        recruitmentRequirementService.delete(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Requirement deleted successfully", null));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get recruitment requirement by ID",
            description = "Retrieves a specific recruitment requirement using its unique identifier",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Requirement retrieved successfully",
                            content = @Content(schema = @Schema(implementation = RecruitmentRequirementsDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Requirement not found")
            }
    )
    public ResponseEntity<APIResponse<RecruitmentRequirementsDTO>> getById(@PathVariable Integer id) {
        RecruitmentRequirementsDTO result = recruitmentRequirementService.getDTOById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Requirement retrieved successfully", result));
    }



    @PostMapping("/filter")
    @Operation(
            summary = "Filter recruitment requirements",
            description = "Filters recruitment requirements based on provided criteria",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Filter parameters",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RecruitmentRequirementFilter.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filter successful",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = RecruitmentRequirementsDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<RecruitmentRequirementsDTO>>> filter(
            @RequestBody RecruitmentRequirementFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<RecruitmentRequirementsDTO> result = recruitmentRequirementService.filterRecruitmentRequirements(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter completed", result));
    }

    @PutMapping("/{id}/status")
    @Operation(
            summary = "Update Requirement Status",
            description = "Set a new status on an existing recruitment requirement",
            parameters = {
                    @Parameter(name = "id", description = "Requirement ID", required = true),
                    @Parameter(name = "status",
                            description = "New status",
                            schema = @Schema(implementation = RecruitmentRequirementsStatus.class))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status updated successfully",
                            content = @Content(schema = @Schema(implementation = RecruitmentRequirementsDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Requirement not found")
            }
    )
    public ResponseEntity<APIResponse<RecruitmentRequirementsDTO>> updateStatus(
            @PathVariable Integer id,
            @RequestParam RecruitmentRequirementsStatus status) {
        RecruitmentRequirementsDTO dto = recruitmentRequirementService.updateStatus(id, status);
        return ResponseEntity.ok(new APIResponse<>(true,
                "Requirement status updated to " + status, dto));
    }
}

