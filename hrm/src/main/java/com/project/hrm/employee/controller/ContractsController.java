package com.project.hrm.employee.controller;

import com.project.hrm.common.response.APIResponse;
import com.project.hrm.common.response.PageDTO;
import com.project.hrm.employee.dto.contractDTO.ContractCreateDTO;
import com.project.hrm.employee.dto.contractDTO.ContractDTO;
import com.project.hrm.employee.dto.contractDTO.ContractFilter;
import com.project.hrm.employee.dto.contractDTO.ContractUpdateDTO;
import com.project.hrm.employee.enums.ContractStatus;
import com.project.hrm.employee.service.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contract")
@RequiredArgsConstructor
@Tag(name = "Contract Controller", description = "Contract manager")
public class ContractsController {
        private final ContractService contractService;

        @PostMapping
        @Operation(summary = "Create Contract", description = "Creates a new employment contract for an employee", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Contract creation details", required = true, content = @Content(schema = @Schema(implementation = ContractCreateDTO.class))), responses = {
                        @ApiResponse(responseCode = "201", description = "Contract created successfully", content = @Content(schema = @Schema(implementation = ContractDTO.class)))
        })
        public ResponseEntity<APIResponse<ContractDTO>> create(
                        @RequestBody ContractCreateDTO dto, HttpServletRequest request) {
                ContractDTO result = contractService.create(dto);
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new APIResponse<>(true, "Contract created successfully", result, null,
                                                request.getRequestURI()));
        }

        @PutMapping
        @Operation(summary = "Update Contract", description = "Updates an existing contract based on provided data", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Contract update details", required = true, content = @Content(schema = @Schema(implementation = ContractUpdateDTO.class))), responses = {
                        @ApiResponse(responseCode = "200", description = "Contract updated successfully", content = @Content(schema = @Schema(implementation = ContractDTO.class)))
        })
        public ResponseEntity<APIResponse<ContractDTO>> update(
                        @RequestBody ContractUpdateDTO dto, HttpServletRequest request) {
                ContractDTO updated = contractService.update(dto);
                return ResponseEntity.ok(new APIResponse<>(true, "Contract updated successfully", updated, null,
                                request.getRequestURI()));
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Delete Contract", description = "Deletes the contract with the given ID", parameters = {
                        @Parameter(name = "id", description = "Contract ID", required = true)
        }, responses = {
                        @ApiResponse(responseCode = "200", description = "Contract deleted successfully"),
                        @ApiResponse(responseCode = "404", description = "Contract not found")
        })
        public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id, HttpServletRequest request) {
                contractService.delete(id);
                return ResponseEntity.ok(new APIResponse<>(true, "Contract deleted successfully", null, null,
                                request.getRequestURI()));
        }

        @GetMapping("/{id}")
        @Operation(summary = "Get Contract by ID", description = "Retrieves a contract by its unique ID", parameters = {
                        @Parameter(name = "id", description = "Contract ID", required = true)
        }, responses = {
                        @ApiResponse(responseCode = "200", description = "Contract retrieved successfully", content = @Content(schema = @Schema(implementation = ContractDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Contract not found")
        })
        public ResponseEntity<APIResponse<ContractDTO>> getById(@PathVariable Integer id, HttpServletRequest request) {
                ContractDTO dto = contractService.getById(id);
                return ResponseEntity.ok(new APIResponse<>(true, "Contract retrieved successfully", dto, null,
                                request.getRequestURI()));
        }

        @GetMapping("/filter")
        @Operation(
                summary = "Filter Contracts",
                description = "Filters contracts by criteria such as employee, department, role, title, signing date, and date range",
                parameters = {
                        @Parameter(name = "page", description = "Page number (0-based)", example = "0"),
                        @Parameter(name = "size", description = "Page size", example = "10")
                },
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                description = "Contracts filtered successfully",
                                content = @Content(schema = @Schema(implementation = PageDTO.class))
                        )
                }
        )
        public ResponseEntity<APIResponse<PageDTO<ContractDTO>>> filter(
                @ModelAttribute ContractFilter filter,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size,
                HttpServletRequest request) {

                PageDTO<ContractDTO> results = contractService.filter(filter, page, size);
                return ResponseEntity.ok(
                        new APIResponse<>(true, "Contracts filtered successfully", results, null, request.getRequestURI())
                );
        }


        @PutMapping("/{id}/status")
        @Operation(summary = "Update Contract Status", description = "Set a new status on an existing contract by ID", parameters = {
                        @Parameter(name = "id", description = "Contract ID", required = true),
                        @Parameter(name = "status", description = "New status", required = true, schema = @Schema(implementation = ContractStatus.class))
        }, responses = {
                        @ApiResponse(responseCode = "200", description = "Status updated"),
                        @ApiResponse(responseCode = "404", description = "Contract not found")
        })
        public ResponseEntity<APIResponse<Void>> updateStatus(
                        @PathVariable Integer id,
                        @RequestParam ContractStatus status, HttpServletRequest request) {
                contractService.updateStatus(id, status);
                return ResponseEntity.ok(new APIResponse<>(true,
                                "Contract status updated to " + status + " is successfully", null, null, request.getRequestURI()));
        }

        @GetMapping("/{contractId}/pdf")
        @Operation(summary = "Generate PDF report for a specific contract")
        public ResponseEntity<byte[]> generateContractPDF(@PathVariable Integer contractId) {
                try {
                        byte[] pdfBytes = contractService.generateContractReport(contractId);

                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_PDF);
                        headers.setContentDispositionFormData("attachment",
                                "contract_" + contractId + ".pdf");
                        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

                        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(("Error generating PDF: " + e.getMessage()).getBytes());
                }
        }

        @GetMapping("/active/{employeeId}")
        @Operation(
                summary = "Get active contract by employee ID",
                description = "Returns the contract that is currently active for the specified employee.",
                parameters = @Parameter(name = "employeeId", description = "Employee ID", required = true),
                responses = {
                        @ApiResponse(responseCode = "200", description = "Active contract retrieved",
                                content = @Content(schema = @Schema(implementation = ContractDTO.class)))
                }
        )
        public ResponseEntity<APIResponse<ContractDTO>> getCurrentActiveContract(
                @PathVariable Integer employeeId,
                HttpServletRequest request) {
                ContractDTO result = contractService.getCurrentActiveContract(employeeId);
                return ResponseEntity.ok(new APIResponse<>(
                        true,
                        "Active contract retrieved successfully",
                        result,
                        null,
                        request.getRequestURI()
                ));
        }
}
