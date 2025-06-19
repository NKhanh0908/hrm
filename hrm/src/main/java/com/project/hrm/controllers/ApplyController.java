package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.applyDTO.*;
import com.project.hrm.dto.othersDTO.InterviewLetter;
import com.project.hrm.enums.ApplyStatus;
import com.project.hrm.services.ApplyService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apply")
@RequiredArgsConstructor
@Tag(name = "Apply Controller", description = "Apply manager")
public class ApplyController {
    private final ApplyService applyService;

        @PostMapping
        @Operation(summary = "Create Apply",
                description = "Apply in Job upload by Recruitment",
                requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                        description = "Apply create information", required = true,
                        content = @Content(schema = @Schema(implementation = ApplyCreateDTO.class))),
                responses = {
                        @ApiResponse(responseCode = "201",
                                description = "Create Apply successfully",
                                content = @Content(schema = @Schema(implementation = ApplyDTO.class)))
                })
        public ResponseEntity<APIResponse<ApplyDTO>> create(@RequestBody ApplyCreateDTO applyCreateDTO,
                        HttpServletRequest request) {
                ApplyDTO applyDTO = applyService.create(applyCreateDTO);
                return ResponseEntity.ok(new APIResponse<>(true, "Create Department successfully", applyDTO, null,
                                request.getRequestURI()));
        }

        @PutMapping
        @Operation(summary = "Update Apply", description = "Update Apply in Job upload by Recruitment", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Apply update information", required = true, content = @Content(schema = @Schema(implementation = ApplyUpdateDTO.class))), responses = {
                        @ApiResponse(responseCode = "201", description = "Update Apply successfully", content = @Content(schema = @Schema(implementation = ApplyDTO.class)))
        })
        public ResponseEntity<APIResponse<ApplyDTO>> update(@RequestBody ApplyUpdateDTO applyUpdateDTO,
                        HttpServletRequest request) {
                ApplyDTO applyDTO = applyService.update(applyUpdateDTO);
                return ResponseEntity.ok(new APIResponse<>(true, "Create Department successfully", applyDTO, null,
                                request.getRequestURI()));
        }

        @GetMapping
        @Operation(summary = "Find Apply by id", description = "Find detail Apply by Apply id", parameters = {
                        @Parameter(name = "id", description = "Apply ID", required = true)
        }, responses = {
                        @ApiResponse(responseCode = "200", description = "Successfully retrieved Apply", content = @Content(schema = @Schema(implementation = ApplyDTO.class)))
        })
        public ResponseEntity<APIResponse<ApplyDTO>> getById(@RequestParam Integer id, HttpServletRequest request) {
                ApplyDTO applyDTO = applyService.getById(id);
                return ResponseEntity.ok(new APIResponse<>(true, "Create Department successfully", applyDTO, null,
                                request.getRequestURI()));
        }

        @PostMapping("/filter")
        @Operation(summary = "Filter applies", description = "Filter apply by period of time apply, status, position, or recruitmentID with pagination.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Filter conditions", required = true, content = @Content(schema = @Schema(implementation = ApplyFilter.class))), parameters = {
                        @Parameter(name = "page", description = "Page number (0-based)", required = false, example = "0"),
                        @Parameter(name = "size", description = "Page size", required = false, example = "10")
        }, responses = {
                        @ApiResponse(responseCode = "200", description = "Filtered apply list", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApplyDTO.class))))
        })
        public ResponseEntity<APIResponse<List<ApplyDTO>>> filter(@RequestBody ApplyFilter applyFilter,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size, HttpServletRequest request) {
                List<ApplyDTO> applyDTOList = applyService.filter(applyFilter, page, size);
                return ResponseEntity.ok(new APIResponse<>(true, "Filter Apply successfully", applyDTOList, null,
                                request.getRequestURI()));
        }

        @PutMapping("/{id}/status")
        @Operation(summary = "Update Apply Status", description = "Set a new status on an existing application", parameters = {
                        @Parameter(name = "id", description = "Application ID", required = true),
                        @Parameter(name = "status", description = "New status", schema = @Schema(implementation = ApplyStatus.class))
        }, responses = {
                        @ApiResponse(responseCode = "200", description = "Status updated successfully", content = @Content(schema = @Schema(implementation = ApplyDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Application not found")
        })
        public ResponseEntity<APIResponse<ApplyDTO>> updateStatus(
                        @PathVariable Integer id,
                        @RequestParam ApplyStatus status, HttpServletRequest request) {
                ApplyDTO updated = applyService.updateStatus(id, status);
                return ResponseEntity.ok(new APIResponse<>(true,
                                "Apply status updated to " + status, updated, null, request.getRequestURI()));
        }

        @PostMapping("/interview")
        @Operation(summary = "Invite to Interview", description = "Update application status to INTERVIEW and send an interview invitation email", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Interview scheduling details", required = true, content = @Content(schema = @Schema(implementation = InterviewLetter.class))), responses = {
                        @ApiResponse(responseCode = "200", description = "Interview invitation sent and status updated", content = @Content(schema = @Schema(implementation = ApplyDTO.class)))
        })
        public ResponseEntity<APIResponse<ApplyDTO>> interview(
                        @RequestBody InterviewLetter interviewLetter, HttpServletRequest request) {
                ApplyDTO updated = applyService.interview(interviewLetter);
                return ResponseEntity.ok(new APIResponse<>(true,
                                "Interview invitation sent successfully", updated, null, request.getRequestURI()));
        }

    @PutMapping("/reject")
    @Operation(summary = "Reject application",
            description = "Rejects an application by ID and sends a rejection notification email.",
            parameters = @Parameter(name = "applyId", description = "ID of the application to reject", required = true),
            responses = @ApiResponse(responseCode = "200", description = "Application rejected successfully", content = @Content(schema = @Schema(implementation = ApplyDTO.class)))
    )
    public ResponseEntity<APIResponse<ApplyDTO>> rejectApply(
            @RequestParam Integer applyId,
            HttpServletRequest request) {

        ApplyDTO result = applyService.rejectApply(applyId);
        return ResponseEntity.ok(new APIResponse<>(true, "Reject application successfully", result, null, request.getRequestURI()));
    }

    @PutMapping("/hire")
    @Operation(
            summary = "Hire candidate",
            description = """
        Marks an application as HIRED, creates an employee, 
        generates a virtual contract, and sends a job offer email with reporting details.
        """,
            parameters = {
                    @Parameter(name = "applyId", description = "ID of the application to mark as hired", required = true)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Job offer details including report date, location, and contact info",
                    required = true,
                    content = @Content(schema = @Schema(implementation = JobOfferDetailsDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Candidate hired successfully", content = @Content(schema = @Schema(implementation = ApplyDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<ApplyDTO>> hiredApply(
            @RequestParam Integer applyId,
            @RequestBody JobOfferDetailsDTO details,
            HttpServletRequest request) {

        ApplyDTO result = applyService.hiredApply(applyId, details);
        return ResponseEntity.ok(new APIResponse<>(true, "Hire application successfully", result, null, request.getRequestURI()));
    }

}
