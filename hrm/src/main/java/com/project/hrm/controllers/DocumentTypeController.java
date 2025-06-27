package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.documentTypeDTO.DocumentTypeCreateDTO;
import com.project.hrm.dto.documentTypeDTO.DocumentTypeDTO;
import com.project.hrm.dto.documentTypeDTO.DocumentTypeUpdateDTO;
import com.project.hrm.services.DocumentTypeService;
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
@RequestMapping("/document-type")
@RequiredArgsConstructor
@Tag(name = "Document Type Controller", description = "Manage document type data")
public class DocumentTypeController {
    private final DocumentTypeService documentTypeService;

    @PostMapping
    @Operation(
            summary = "Create new document type",
            description = "Creates a new document type in the system",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Document type creation data", required = true,
                    content = @Content(schema = @Schema(implementation = DocumentTypeCreateDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Document type created successfully",
                            content = @Content(schema = @Schema(implementation = DocumentTypeDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<DocumentTypeDTO>> create(@RequestBody DocumentTypeCreateDTO documentTypeCreateDTO, HttpServletRequest request)  {
        DocumentTypeDTO result = documentTypeService.create(documentTypeCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(true, "Document type created successfully", result, null,
                        request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update existing document type",
            description = "Updates the information of an existing document type",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Document type update data", required = true,
                    content = @Content(schema = @Schema(implementation = DocumentTypeUpdateDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Document type updated successfully",
                            content = @Content(schema = @Schema(implementation = DocumentTypeDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<DocumentTypeDTO>> update(
            @RequestBody DocumentTypeUpdateDTO updateDTO,
            HttpServletRequest request) {

        DocumentTypeDTO result = documentTypeService.update(updateDTO);

        return ResponseEntity.ok(new APIResponse<>(
                true,
                "Document type updated successfully",
                result,
                null,
                request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get document type by ID",
            description = "Retrieves the document type data using the given ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Document type retrieved successfully",
                            content = @Content(schema = @Schema(implementation = DocumentTypeDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Document type not found")
            }
    )
    public ResponseEntity<APIResponse<DocumentTypeDTO>> getById(
            @PathVariable Integer id,
            HttpServletRequest request) {

        DocumentTypeDTO result = documentTypeService.getDTOById(id);

        return ResponseEntity.ok(new APIResponse<>(
                true,
                "Document type retrieved successfully",
                result,
                null,
                request.getRequestURI()));
    }
}
