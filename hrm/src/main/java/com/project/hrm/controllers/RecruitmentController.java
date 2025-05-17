package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.recruitmentDTO.RecruitmentCreateDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentDTO;
import com.project.hrm.dto.recruitmentDTO.RecruitmentFilter;
import com.project.hrm.dto.recruitmentDTO.RecruitmentUpdateDTO;
import com.project.hrm.services.RecruitmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recruitment")
@RequiredArgsConstructor
@Tag(name = "Recruitment Controller", description = "Manage recruitment postings and related actions")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    @PostMapping
    @Operation(
            summary = "Create a new recruitment post",
            description = "Creates a new recruitment post with job details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Recruitment creation data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RecruitmentCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recruitment post created successfully",
                            content = @Content(schema = @Schema(implementation = RecruitmentDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<RecruitmentDTO>> create(@RequestBody RecruitmentCreateDTO dto) {
        RecruitmentDTO result = recruitmentService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(true, "Recruitment created successfully", result));
    }

    @PutMapping
    @Operation(
            summary = "Update recruitment post",
            description = "Updates the information of an existing recruitment post",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Recruitment update data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RecruitmentUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recruitment post updated successfully",
                            content = @Content(schema = @Schema(implementation = RecruitmentDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<RecruitmentDTO>> update(@RequestBody RecruitmentUpdateDTO dto) {
        RecruitmentDTO result = recruitmentService.update(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Recruitment updated successfully", result));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a recruitment post",
            description = "Deletes a recruitment post by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recruitment post deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Recruitment not found")
            }
    )
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id) {
        recruitmentService.delete(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Recruitment deleted successfully", null));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get recruitment post by ID",
            description = "Retrieves a recruitment post by its unique ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recruitment found",
                            content = @Content(schema = @Schema(implementation = RecruitmentDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Recruitment not found")
            }
    )
    public ResponseEntity<APIResponse<RecruitmentDTO>> getById(@PathVariable Integer id) {
        RecruitmentDTO result = recruitmentService.getDTOById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Recruitment retrieved successfully", result));
    }

    @GetMapping("/check-exists/{id}")
    @Operation(
            summary = "Check if recruitment post exists",
            description = "Checks whether a recruitment post exists using the specified ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Existence check successful",
                            content = @Content(schema = @Schema(implementation = Boolean.class)))
            }
    )
    public ResponseEntity<APIResponse<Boolean>> checkExists(@PathVariable Integer id) {
        Boolean exists = recruitmentService.checkExists(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Existence check completed", exists));
    }

    @PostMapping("/filter")
    @Operation(
            summary = "Filter recruitment posts",
            description = "Filters recruitment posts based on given criteria",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Filter conditions",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RecruitmentFilter.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recruitments filtered successfully",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = RecruitmentDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<RecruitmentDTO>>> filter(
            @RequestBody RecruitmentFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<RecruitmentDTO> results = recruitmentService.filter(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Recruitments filtered successfully", results));
    }
}
