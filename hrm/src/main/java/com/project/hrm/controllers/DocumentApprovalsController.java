package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.DocumentFilter.DocumentFilterDTO;
import com.project.hrm.dto.documentApprovalsDTO.DocumentApprovalsCreateDTO;
import com.project.hrm.dto.documentApprovalsDTO.DocumentApprovalsDTO;
import com.project.hrm.dto.documentApprovalsDTO.DocumentApprovalsUpdateDTO;
import com.project.hrm.services.DocumentApprovalsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/document-approvals")
@RequiredArgsConstructor
@Tag(name = "Document Approvals Controller", description = "Manage document approvals data")
public class DocumentApprovalsController {
    private final DocumentApprovalsService documentApprovalsService;

    @PostMapping
    @Operation(
            summary = "Create a new document approval request",
            description = "Creates a new document approval entry",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = DocumentApprovalsCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Approval request created successfully",
                            content = @Content(schema = @Schema(implementation = DocumentApprovalsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<DocumentApprovalsDTO>> create(@RequestBody DocumentApprovalsCreateDTO dto, HttpServletRequest request) {
        DocumentApprovalsDTO result = documentApprovalsService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(true, "Created successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update approval request",
            description = "Update document approval information",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = DocumentApprovalsUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Update successfully",
                            content = @Content(schema = @Schema(implementation = DocumentApprovalsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<DocumentApprovalsDTO>> update(@RequestBody DocumentApprovalsUpdateDTO dto, HttpServletRequest request) {
        DocumentApprovalsDTO result = documentApprovalsService.update(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Updated successfully", result, null, request.getRequestURI()));
    }

    @PutMapping("/status/{id}")
    @Operation(
            summary = "Update approval status",
            description = "Update the approval status by ID",
            parameters = {
                    @Parameter(name = "id", description = "Approval request ID", required = true),
                    @Parameter(name = "status", description = "New status", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status updated successfully",
                            content = @Content(schema = @Schema(implementation = DocumentApprovalsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<DocumentApprovalsDTO>> updateStatus(
            @RequestParam Integer id,
            @RequestParam String status,
            HttpServletRequest request) {
        DocumentApprovalsDTO result = documentApprovalsService.updateStatus(id, status);
        return ResponseEntity.ok(new APIResponse<>(true, "Status updated successfully", result, null, request.getRequestURI()));
    }


    @PostMapping("/filter")
    @Operation(
            summary = "Filter document approvals",
            description = "Filter approval requests by status, time, or document ID",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = DocumentFilterDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filtered successfully",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentApprovalsDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<DocumentApprovalsDTO>>> filterByStatus(
            @RequestBody DocumentFilterDTO filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        List<DocumentApprovalsDTO> results = documentApprovalsService.filterByStatus(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter completed", results, null, request.getRequestURI()));
    }
}
