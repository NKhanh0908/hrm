package com.project.hrm.recruitment.controller;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.PageDTO;
import com.project.hrm.recruitment.dto.candidateProfileDTO.CandidateProfileCreateDTO;
import com.project.hrm.recruitment.dto.candidateProfileDTO.CandidateProfileDTO;
import com.project.hrm.recruitment.dto.candidateProfileDTO.CandidateProfileFilter;
import com.project.hrm.recruitment.dto.candidateProfileDTO.CandidateProfileUpdateDTO;
import com.project.hrm.recruitment.service.CandidateProfileService;
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

@RestController
@RequestMapping("/candidate-profile")
@RequiredArgsConstructor
@Tag(name = "Candidate Profile Controller", description = "Manage Candidate Profiles")
public class CandidateProfileController {

        private final CandidateProfileService candidateProfileService;

        @PostMapping
        @Operation(summary = "Create Candidate Profile", description = "Creates a new candidate profile in the system", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Candidate profile creation details", required = true, content = @Content(schema = @Schema(implementation = CandidateProfileCreateDTO.class))), responses = {
                        @ApiResponse(responseCode = "201", description = "Candidate profile created successfully", content = @Content(schema = @Schema(implementation = CandidateProfileDTO.class)))
        })
        public ResponseEntity<APIResponse<CandidateProfileDTO>> create(@RequestBody CandidateProfileCreateDTO dto,
                        HttpServletRequest request) {
                CandidateProfileDTO result = candidateProfileService.create(dto);
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new APIResponse<>(true, "Candidate profile created successfully", result, null,
                                                request.getRequestURI()));
        }

        @PutMapping
        @Operation(summary = "Update Candidate Profile", description = "Updates an existing candidate profile based on its ID", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated candidate profile data", required = true, content = @Content(schema = @Schema(implementation = CandidateProfileUpdateDTO.class))), responses = {
                        @ApiResponse(responseCode = "200", description = "Candidate profile updated successfully", content = @Content(schema = @Schema(implementation = CandidateProfileDTO.class)))
        })
        public ResponseEntity<APIResponse<CandidateProfileDTO>> update(@RequestBody CandidateProfileUpdateDTO dto,
                        HttpServletRequest request) {
                CandidateProfileDTO updated = candidateProfileService.update(dto);
                return ResponseEntity.ok(new APIResponse<>(true, "Candidate profile updated successfully", updated,
                                null, request.getRequestURI()));
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Delete Candidate Profile", description = "Deletes a candidate profile based on the given ID", responses = {
                        @ApiResponse(responseCode = "200", description = "Candidate profile deleted successfully"),
                        @ApiResponse(responseCode = "404", description = "Candidate profile not found")
        })
        public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id, HttpServletRequest request) {
                candidateProfileService.delete(id);
                return ResponseEntity.ok(new APIResponse<>(true, "Candidate profile deleted successfully", null, null,
                                request.getRequestURI()));
        }

        @GetMapping("/{id}")
        @Operation(summary = "Get Candidate Profile by ID", description = "Retrieves the candidate profile details for the given ID", responses = {
                        @ApiResponse(responseCode = "200", description = "Candidate profile retrieved successfully", content = @Content(schema = @Schema(implementation = CandidateProfileDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Candidate profile not found")
        })
        public ResponseEntity<APIResponse<CandidateProfileDTO>> getById(@PathVariable Integer id,
                        HttpServletRequest request) {
                CandidateProfileDTO dto = candidateProfileService.getById(id);
                return ResponseEntity.ok(new APIResponse<>(true, "Candidate profile retrieved successfully", dto, null,
                                request.getRequestURI()));
        }

        @PostMapping("/filter")
        @Operation(summary = "Filter Candidate Profiles", description = "Returns a filtered list of candidate profiles based on various criteria such as name, email, skills, etc.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Filtering criteria for candidate profiles", required = true, content = @Content(schema = @Schema(implementation = CandidateProfileFilter.class))), responses = {
                        @ApiResponse(responseCode = "200", description = "Candidate profiles filtered successfully", content = @Content(schema = @Schema(implementation = PageDTO.class)))
        })
        public ResponseEntity<APIResponse<PageDTO<CandidateProfileDTO>>> filter(
                        @RequestBody CandidateProfileFilter candidateProfileFilter,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size, HttpServletRequest request) {

                PageDTO<CandidateProfileDTO> results = candidateProfileService.filter(candidateProfileFilter, page, size);
                return ResponseEntity.ok(new APIResponse<>(true, "Candidate profiles filtered successfully", results,
                                null, request.getRequestURI()));
        }
}