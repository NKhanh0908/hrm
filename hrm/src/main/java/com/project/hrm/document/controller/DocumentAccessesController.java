package com.project.hrm.document.controller;

import com.project.hrm.common.response.APIResponse;
import com.project.hrm.document.dto.documentAccessesDTO.DocumentAccessesCreateDTO;
import com.project.hrm.document.dto.documentAccessesDTO.DocumentAccessesDTO;
import com.project.hrm.document.dto.documentAccessesDTO.DocumentAccessesUpdateDTO;
import com.project.hrm.document.service.DocumentAccessesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/document-accesses")
@RequiredArgsConstructor
@Tag(name = "Document Accesses Controller", description = "Manage document accesses data")
public class DocumentAccessesController {
    private final DocumentAccessesService documentAccessesService;

    @PostMapping
    @Operation(
            summary = "Create a new document access",
            description = "Creates a new document access entry in the system",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = DocumentAccessesCreateDTO.class))
            ),
            responses = {
                   @ApiResponse(responseCode = "201",description = "Created successfully",
                            content = @Content(schema = @Schema(implementation = DocumentAccessesCreateDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<DocumentAccessesDTO>> create(@RequestBody DocumentAccessesCreateDTO documentAccessesCreateDTO, HttpServletRequest request) {
        DocumentAccessesDTO result = documentAccessesService.create(documentAccessesCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(true, "Created successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update document access",
            description = "Updates the information of an existing document access",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = DocumentAccessesUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Updated successfully",
                            content = @Content(schema = @Schema(implementation = DocumentAccessesDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<DocumentAccessesDTO>> update(DocumentAccessesUpdateDTO documentAccessesUpdateDTO) {
        DocumentAccessesDTO result = documentAccessesService.update(documentAccessesUpdateDTO);
        return ResponseEntity.ok(new APIResponse<>(true, "Updated successfully", result, null, null));
    }

    @GetMapping("/filter")
    @Operation(
            summary = "Get document accesses by filter",
            description = "Filter accesses based on documentId, access level, and employee name",
            parameters = {
                    @Parameter(name = "documentId", description = "ID of the document to filter"),
                    @Parameter(name = "accessLevel", description = "Access level (e.g., READ, WRITE, etc.)"),
                    @Parameter(name = "employeeName", description = "Name of the employee who accessed"),
                    @Parameter(name = "page", description = "Page number", example = "0"),
                    @Parameter(name = "size", description = "Page size", example = "10")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Document accesses retrieved successfully",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentAccessesDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<DocumentAccessesDTO>>> filter(
            @RequestParam(required = false) Integer documentId,
            @RequestParam(required = false) String accessLevel,
            @RequestParam(required = false) String employeeName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<DocumentAccessesDTO> result = documentAccessesService.filterByDocumentId(documentId, accessLevel, employeeName, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filtered successfully", result, null, null));
    }
}
