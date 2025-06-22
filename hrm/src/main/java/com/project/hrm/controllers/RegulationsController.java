package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.regulationsDTO.RegulationsCreateDTO;
import com.project.hrm.dto.regulationsDTO.RegulationsDTO;
import com.project.hrm.dto.regulationsDTO.RegulationsFilter;
import com.project.hrm.dto.regulationsDTO.RegulationsUpdateDTO;
import com.project.hrm.services.RegulationsService;
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
@RequestMapping("/regulations")
@RequiredArgsConstructor
@Tag(name = "Regulations Controller", description = "Manage payroll regulations")
public class RegulationsController {

    private final RegulationsService regulationsService;

    @PostMapping
    @Operation(
            summary = "Create regulation",
            description = "Create a new regulation",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Regulation creation data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RegulationsCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Regulation created successfully", content = @Content(schema = @Schema(implementation = RegulationsDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<RegulationsDTO>> create(
            @RequestBody RegulationsCreateDTO dto,
            HttpServletRequest request
    ) {
        RegulationsDTO result = regulationsService.create(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Create Regulation successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get regulation by ID",
            parameters = {
                    @Parameter(name = "id", description = "Regulation ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Found regulation", content = @Content(schema = @Schema(implementation = RegulationsDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Regulation not found")
            }
    )
    public ResponseEntity<APIResponse<RegulationsDTO>> getById(
            @PathVariable Integer id,
            HttpServletRequest request
    ) {
        RegulationsDTO result = regulationsService.getById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Get Regulation successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update regulations",
            description = "Update existing regulations. Chỉ những trường không null sẽ được cập nhật.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Thông tin cần cập nhật",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RegulationsUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Update thành công", content = @Content(schema = @Schema(implementation = RegulationsDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Không tìm thấy regulations")
            }
    )
    public ResponseEntity<APIResponse<RegulationsDTO>> update(@RequestBody RegulationsUpdateDTO updateDTO,
                                                              HttpServletRequest request) {
        RegulationsDTO updated = regulationsService.update(updateDTO);
        return ResponseEntity.ok(
                new APIResponse<>(true, "Update Regulations successfully", updated, null, request.getRequestURI())
        );
    }


    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete regulation",
            parameters = {
                    @Parameter(name = "id", description = "Regulation ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Regulation deleted"),
                    @ApiResponse(responseCode = "404", description = "Regulation not found")
            }
    )
    public ResponseEntity<APIResponse<Void>> delete(
            @PathVariable Integer id,
            HttpServletRequest request
    ) {
        regulationsService.delete(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Delete Regulation successfully", null, null, request.getRequestURI()));
    }

    @PostMapping("/filter")
    @Operation(
            summary = "Filter regulations",
            description = "Filter regulations by name, applicable salary, effective date",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Filtering criteria",
                    content = @Content(schema = @Schema(implementation = RegulationsFilter.class))
            ),
            parameters = {
                    @Parameter(name = "page", description = "Page number", required = false, example = "0"),
                    @Parameter(name = "size", description = "Page size", required = false, example = "10")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filtered regulations list", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RegulationsDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<RegulationsDTO>>> filter(
            @RequestBody RegulationsFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request
    ) {
        List<RegulationsDTO> results = regulationsService.filter(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter Regulations successfully", results, null, request.getRequestURI()));
    }
}
