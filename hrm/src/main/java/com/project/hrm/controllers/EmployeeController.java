package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.employeeDTO.EmployeeCreateDTO;
import com.project.hrm.dto.employeeDTO.EmployeeDTO;
import com.project.hrm.dto.employeeDTO.EmployeeFilter;
import com.project.hrm.dto.employeeDTO.EmployeeUpdateDTO;
import com.project.hrm.services.EmployeeService;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
@Tag(name = "Employee Controller", description = "Manage employee data")
public class EmployeeController {

        private final EmployeeService employeeService;

        @PostMapping
        @Operation(summary = "Create new employee", description = "Creates a new employee in the system", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Employee creation data", required = true, content = @Content(schema = @Schema(implementation = EmployeeCreateDTO.class))), responses = {
                        @ApiResponse(responseCode = "201", description = "Employee created successfully", content = @Content(schema = @Schema(implementation = EmployeeDTO.class)))
        })
        public ResponseEntity<APIResponse<EmployeeDTO>> create(@ModelAttribute EmployeeCreateDTO employeeCreateDTO,
                        HttpServletRequest request) {
                EmployeeDTO result = employeeService.create(employeeCreateDTO);
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new APIResponse<>(true, "Employee created successfully", result, null,
                                                request.getRequestURI()));
        }

        @PutMapping
        @Operation(summary = "Update existing employee", description = "Updates the information of an existing employee", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Employee update data", required = true, content = @Content(schema = @Schema(implementation = EmployeeUpdateDTO.class))), responses = {
                        @ApiResponse(responseCode = "200", description = "Employee updated successfully", content = @Content(schema = @Schema(implementation = EmployeeDTO.class)))
        })
        public ResponseEntity<APIResponse<EmployeeDTO>> update(@ModelAttribute EmployeeUpdateDTO employeeUpdateDTO,
                        HttpServletRequest request) {
                EmployeeDTO result = employeeService.update(employeeUpdateDTO);
                return ResponseEntity.ok(new APIResponse<>(true, "Employee updated successfully", result, null,
                                request.getRequestURI()));
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Delete employee by ID", description = "Deletes an employee from the system using their ID", responses = {
                        @ApiResponse(responseCode = "200", description = "Employee deleted successfully"),
                        @ApiResponse(responseCode = "404", description = "Employee not found")
        })
        public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id, HttpServletRequest request) {
                employeeService.delete(id);
                return ResponseEntity.ok(new APIResponse<>(true, "Employee deleted successfully", null, null,
                                request.getRequestURI()));
        }

        @GetMapping("/{id}")
        @Operation(summary = "Get employee by ID", description = "Retrieves the employee data using the given ID", responses = {
                        @ApiResponse(responseCode = "200", description = "Employee retrieved successfully", content = @Content(schema = @Schema(implementation = EmployeeDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Employee not found")
        })
        public ResponseEntity<APIResponse<EmployeeDTO>> getById(@PathVariable Integer id, HttpServletRequest request) {
                EmployeeDTO result = employeeService.getDTOById(id);
                return ResponseEntity.ok(new APIResponse<>(true, "Employee retrieved successfully", result, null,
                                request.getRequestURI()));
        }

        @GetMapping("/check-exists/{id}")
        @Operation(summary = "Check if employee exists by ID", description = "Checks whether an employee with the specified ID exists", responses = {
                        @ApiResponse(responseCode = "200", description = "Check successful", content = @Content(schema = @Schema(implementation = Boolean.class)))
        })
        public ResponseEntity<APIResponse<Boolean>> checkExists(@PathVariable Integer id, HttpServletRequest request) {
                Boolean exists = employeeService.checkExists(id);
                return ResponseEntity
                                .ok(new APIResponse<>(true, "Check completed", exists, null, request.getRequestURI()));
        }

        @PostMapping("/filter")
        @Operation(summary = "Filter employees", description = "Returns a list of employees based on the provided filter criteria", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Employee filter criteria", required = true, content = @Content(schema = @Schema(implementation = EmployeeFilter.class))), responses = {
                        @ApiResponse(responseCode = "200", description = "Employees filtered successfully", content = @Content(schema = @Schema(implementation = PageDTO.class)))
        })
        public ResponseEntity<APIResponse<PageDTO<EmployeeDTO>>> filter(
                        @RequestBody EmployeeFilter employeeFilter,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size, HttpServletRequest request) {

                PageDTO<EmployeeDTO> results = employeeService.filter(employeeFilter, page, size);
                return ResponseEntity.ok(new APIResponse<>(true, "Employees filtered successfully", results, null,
                                request.getRequestURI()));
        }

        @GetMapping("/filter-by-department")
        @Operation(
                summary = "Filter employees by department",
                description = "Retrieve a paginated list of employees who belong to the given department ID",
                parameters = {
                        @Parameter(name = "departmentId", description = "ID of the department to filter", required = true),
                        @Parameter(name = "page", description = "Page number (0-based)", example = "0"),
                        @Parameter(name = "size", description = "Page size", example = "10")
                },
                responses = {
                        @ApiResponse(responseCode = "200", description = "Successfully retrieved employee list", content = @Content(schema = @Schema(implementation = PageDTO.class)))
                }
        )
        public ResponseEntity<APIResponse<PageDTO<EmployeeDTO>>> filterByDepartmentId(
                @RequestParam Integer departmentId,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size,
                HttpServletRequest request) {

                PageDTO<EmployeeDTO> employees = employeeService.filterByDepartmentID(departmentId, page, size);

                return ResponseEntity.ok(new APIResponse<>(
                        true,
                        "Filter employees by department successfully",
                        employees,
                        null,
                        request.getRequestURI()
                ));
        }
}
