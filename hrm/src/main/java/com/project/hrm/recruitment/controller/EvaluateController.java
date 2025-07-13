package com.project.hrm.recruitment.controller;

import com.project.hrm.common.response.APIResponse;
import com.project.hrm.common.response.PageDTO;
import com.project.hrm.recruitment.dto.evaluateDTO.EvaluateCreateDTO;
import com.project.hrm.recruitment.dto.evaluateDTO.EvaluateDTO;
import com.project.hrm.recruitment.dto.evaluateDTO.EvaluateFilter;
import com.project.hrm.recruitment.dto.evaluateDTO.EvaluateUpdateDTO;
import com.project.hrm.recruitment.service.EvaluateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/evaluate")
@RequiredArgsConstructor
@Tag(name = "Evaluate Controller", description = "Manage evaluations for employees or candidates")
public class EvaluateController {

        private final EvaluateService evaluateService;

        @PostMapping
        @Operation(summary = "Create a new evaluation", description = "Creates a new evaluation record in the system", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Evaluation creation data", required = true, content = @Content(schema = @Schema(implementation = EvaluateCreateDTO.class))), responses = {
                        @ApiResponse(responseCode = "201", description = "Evaluation created successfully", content = @Content(schema = @Schema(implementation = EvaluateDTO.class)))
        })
        public ResponseEntity<APIResponse<EvaluateDTO>> create(@RequestBody EvaluateCreateDTO dto,
                        HttpServletRequest request) {
                EvaluateDTO result = evaluateService.create(dto);
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new APIResponse<>(true, "Evaluation created successfully", result, null,
                                                request.getRequestURI()));
        }

        @PutMapping
        @Operation(summary = "Update an evaluation", description = "Updates information of an existing evaluation", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Evaluation update data", required = true, content = @Content(schema = @Schema(implementation = EvaluateUpdateDTO.class))), responses = {
                        @ApiResponse(responseCode = "200", description = "Evaluation updated successfully", content = @Content(schema = @Schema(implementation = EvaluateDTO.class)))
        })
        public ResponseEntity<APIResponse<EvaluateDTO>> update(@RequestBody EvaluateUpdateDTO dto,
                        HttpServletRequest request) {
                EvaluateDTO result = evaluateService.update(dto);
                return ResponseEntity.ok(new APIResponse<>(true, "Evaluation updated successfully", result, null,
                                request.getRequestURI()));
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Delete an evaluation", description = "Deletes the evaluation with the specified ID", responses = {
                        @ApiResponse(responseCode = "200", description = "Evaluation deleted successfully"),
                        @ApiResponse(responseCode = "404", description = "Evaluation not found")
        })
        public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id, HttpServletRequest request) {
                evaluateService.delete(id);
                return ResponseEntity.ok(new APIResponse<>(true, "Evaluation deleted successfully", null, null,
                                request.getRequestURI()));
        }

        @GetMapping("/{id}")
        @Operation(summary = "Get evaluation by ID", description = "Retrieves evaluation information using the specified ID", responses = {
                        @ApiResponse(responseCode = "200", description = "Evaluation retrieved successfully", content = @Content(schema = @Schema(implementation = EvaluateDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Evaluation not found")
        })
        public ResponseEntity<APIResponse<EvaluateDTO>> getById(@PathVariable Integer id, HttpServletRequest request) {
                EvaluateDTO result = evaluateService.getById(id);
                return ResponseEntity.ok(new APIResponse<>(true, "Evaluation retrieved successfully", result, null,
                                request.getRequestURI()));
        }

        @GetMapping("/filter")
        @Operation(
                summary = "Filter evaluations",
                description = "Filters evaluations based on various criteria such as candidate or evaluator (employee)",
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                description = "Evaluations filtered successfully",
                                content = @Content(schema = @Schema(implementation = PageDTO.class))
                        )
                }
        )
        public ResponseEntity<APIResponse<PageDTO<EvaluateDTO>>> filter(
                @ParameterObject @ModelAttribute EvaluateFilter filter,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size,
                HttpServletRequest request) {

                PageDTO<EvaluateDTO> results = evaluateService.filter(filter, page, size);
                return ResponseEntity.ok(
                        new APIResponse<>(true, "Evaluations filtered successfully", results, null, request.getRequestURI())
                );
        }
}
