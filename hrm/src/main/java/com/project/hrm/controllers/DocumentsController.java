package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.documentApprovalsDTO.DocumentApprovalsUpdateDTO;
import com.project.hrm.dto.documentsDTO.DocumentsCreateDTO;
import com.project.hrm.dto.documentsDTO.DocumentsDTO;
import com.project.hrm.dto.documentsDTO.DocumentsUpdateDTO;
import com.project.hrm.services.DocumentsService;
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
@RequestMapping("/documents")
@RequiredArgsConstructor
@Tag(name = "Documents Controller", description = "Manage document data")
public class DocumentsController {
    private final DocumentsService documentsService;

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
    public ResponseEntity<APIResponse<DocumentsDTO>> create(@RequestBody DocumentsCreateDTO documentsCreateDTO, HttpServletRequest request) {
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
}
