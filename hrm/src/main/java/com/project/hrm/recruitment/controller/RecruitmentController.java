package com.project.hrm.recruitment.controller;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.PageDTO;
import com.project.hrm.recruitment.enums.RecruitmentStatus;
import com.project.hrm.recruitment.dto.recruitmentDTO.RecruitmentCreateDTO;
import com.project.hrm.recruitment.dto.recruitmentDTO.RecruitmentDTO;
import com.project.hrm.recruitment.dto.recruitmentDTO.RecruitmentFilter;
import com.project.hrm.recruitment.dto.recruitmentDTO.RecruitmentUpdateDTO;
import com.project.hrm.recruitment.service.RecruitmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recruitment")
@RequiredArgsConstructor
@Tag(name = "Recruitment Controller", description = "Manage recruitment postings and related actions")
public class RecruitmentController {

        private final RecruitmentService recruitmentService;

        @PostMapping
        @Operation(summary = "Create a new recruitment post", description = "Creates a new recruitment post with job details", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Recruitment creation data", required = true, content = @Content(schema = @Schema(implementation = RecruitmentCreateDTO.class))), responses = {
                        @ApiResponse(responseCode = "201", description = "Recruitment post created successfully", content = @Content(schema = @Schema(implementation = RecruitmentDTO.class)))
        })
        public ResponseEntity<APIResponse<RecruitmentDTO>> create(@RequestBody RecruitmentCreateDTO dto,
                        HttpServletRequest request) {
                RecruitmentDTO result = recruitmentService.create(dto);
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new APIResponse<>(true, "Recruitment created successfully", result, null,
                                                request.getRequestURI()));
        }

        @PutMapping
        @Operation(summary = "Update recruitment post", description = "Updates the information of an existing recruitment post", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Recruitment update data", required = true, content = @Content(schema = @Schema(implementation = RecruitmentUpdateDTO.class))), responses = {
                        @ApiResponse(responseCode = "200", description = "Recruitment post updated successfully", content = @Content(schema = @Schema(implementation = RecruitmentDTO.class)))
        })
        public ResponseEntity<APIResponse<RecruitmentDTO>> update(@RequestBody RecruitmentUpdateDTO dto,
                        HttpServletRequest request) {
                RecruitmentDTO result = recruitmentService.update(dto);
                return ResponseEntity.ok(new APIResponse<>(true, "Recruitment updated successfully", result, null,
                                request.getRequestURI()));
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Delete a recruitment post", description = "Deletes a recruitment post by its ID", responses = {
                        @ApiResponse(responseCode = "200", description = "Recruitment post deleted successfully"),
                        @ApiResponse(responseCode = "404", description = "Recruitment not found")
        })
        public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id, HttpServletRequest request) {
                recruitmentService.delete(id);
                return ResponseEntity.ok(new APIResponse<>(true, "Recruitment deleted successfully", null, null,
                                request.getRequestURI()));
        }

        @GetMapping("/{id}")
        @Operation(summary = "Get recruitment post by ID", description = "Retrieves a recruitment post by its unique ID", responses = {
                        @ApiResponse(responseCode = "200", description = "Recruitment found", content = @Content(schema = @Schema(implementation = RecruitmentDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Recruitment not found")
        })
        public ResponseEntity<APIResponse<RecruitmentDTO>> getById(@PathVariable Integer id,
                        HttpServletRequest request) {
                RecruitmentDTO result = recruitmentService.getDTOById(id);
                return ResponseEntity.ok(new APIResponse<>(true, "Recruitment retrieved successfully", result, null,
                                request.getRequestURI()));
        }

        @PostMapping("/filter")
        @Operation(summary = "Filter recruitment posts", description = "Filters recruitment posts based on given criteria", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Filter conditions", required = true, content = @Content(schema = @Schema(implementation = RecruitmentFilter.class))), responses = {
                        @ApiResponse(responseCode = "200", description = "Recruitments filtered successfully", content = @Content(schema = @Schema(implementation = PageDTO.class)))
        })
        public ResponseEntity<APIResponse<PageDTO<RecruitmentDTO>>> filter(
                        @RequestBody RecruitmentFilter filter,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size, HttpServletRequest request) {
                PageDTO<RecruitmentDTO> results = recruitmentService.filter(filter, page, size);
                return ResponseEntity.ok(new APIResponse<>(true, "Recruitments filtered successfully", results, null,
                                request.getRequestURI()));
        }

        @PostMapping("/approve")
        @Operation(
                summary = "Approve Recruitment Requirement",
                description = "Create a public Recruitment from an approved internal RecruitmentRequirement"
        )
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Approval details and posting information",
                required = true,
                content = @Content(schema = @Schema(implementation = RecruitmentCreateDTO.class))
        )
        @ApiResponses({
                @ApiResponse(responseCode = "200",
                        description = "Recruitment published successfully",
                        content = @Content(schema = @Schema(implementation = RecruitmentDTO.class))),
                @ApiResponse(responseCode = "401", description = "User not authenticated"),
                @ApiResponse(responseCode = "404", description = "RecruitmentRequirement not found")
        })
        public ResponseEntity<APIResponse<RecruitmentDTO>> approve(
                @RequestBody RecruitmentCreateDTO recruitmentCreateDTO,
                HttpServletRequest request) {

                RecruitmentDTO result = recruitmentService.approved(recruitmentCreateDTO);
                return ResponseEntity.ok(new APIResponse<>(true,
                        "Recruitment requirement approved and published", result, null,
                        request.getRequestURI()));
        }

        @PutMapping("/update-status")
        @Operation(
                summary = "Update recruitment status",
                description = "Update the status of a recruitment by its ID. Status can be OPEN, CLOSE, etc.",
                parameters = {
                        @Parameter(name = "id", description = "Recruitment ID", required = true),
                        @Parameter(name = "status", description = "New status for the recruitment", required = true)
                },
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                description = "Recruitment status updated successfully",
                                content = @Content(schema = @Schema(implementation = RecruitmentDTO.class))
                        ),
                        @ApiResponse(
                                responseCode = "404",
                                description = "Recruitment not found"
                        )
                }
        )
        public ResponseEntity<APIResponse<RecruitmentDTO>> updateStatus(
                @RequestParam Integer id,
                @RequestParam RecruitmentStatus status,
                HttpServletRequest request
        ) {
                RecruitmentDTO result = recruitmentService.updateStatus(id, status);
                return ResponseEntity.ok(
                        new APIResponse<>(true, "Recruitment status updated successfully", result, null, request.getRequestURI())
                );
        }
}

