package com.project.hrm.document.controller;

import com.project.hrm.common.response.APIResponse;
import com.project.hrm.document.dto.documentDTO.DocumentsCreateDTO;
import com.project.hrm.document.dto.documentDTO.DocumentsDTO;
import com.project.hrm.document.dto.documentDTO.DocumentsUpdateDTO;
import com.project.hrm.auth.entity.Account;
import com.project.hrm.document.service.DocumentsService;
import com.project.hrm.document.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
@Tag(name = "Documents Controller", description = "Manage document data")
public class DocumentsController {
    private final DocumentsService documentsService;
    private final PermissionService permissionService;

    @PostMapping
    @Operation(
            summary = "Create a new document",
            description = "Creates a new document entry in the system",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = DocumentsCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Document created successfully",
                            content = @Content(schema = @Schema(implementation = DocumentsCreateDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<DocumentsDTO>> create(@ModelAttribute DocumentsCreateDTO documentsCreateDTO, HttpServletRequest request) {
        DocumentsDTO result = documentsService.create(documentsCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(true, "Document created successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update documents information",
            description = "Updates the information of an existing document",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = DocumentsUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Document updated successfully",
                            content = @Content(schema = @Schema(implementation = DocumentsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<DocumentsDTO>> update(@RequestBody DocumentsUpdateDTO documentsUpdateDTO, HttpServletRequest request) {
        DocumentsDTO result = documentsService.update(documentsUpdateDTO);
        return ResponseEntity.ok(new APIResponse<>(true, "Document updated successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/{docId}/view")
    @PreAuthorize("@permissionService.canViewDocument(#account, #docId)")
    @Operation(
            summary = "View document",
            description = "Retrieves the document by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Document retrieved successfully",
                            content = @Content(schema = @Schema(implementation = DocumentsDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Document not found")
            }
    )
    public ResponseEntity<APIResponse<DocumentsDTO>> view(@PathVariable Integer docId, @AuthenticationPrincipal Account account, HttpServletRequest request) {
        if (!permissionService.canViewDocument(account, docId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new APIResponse<>(false, "You do not have permission to view this document", null, null, request.getRequestURI()));
        }
        DocumentsDTO result = documentsService.getDTOById(docId);
        return ResponseEntity.ok(new APIResponse<>(true, "Document retrieved successfully", result, null, request.getRequestURI()));
    }
}
