package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.employeeDTO.EmployeeCreateDTO;
import com.project.hrm.dto.employeeDTO.EmployeeDTO;
import com.project.hrm.dto.employeeDTO.EmployeeFilter;
import com.project.hrm.dto.employeeDTO.EmployeeUpdateDTO;
import com.project.hrm.services.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
@Tag(name = "Employee Controller", description = "Manage employee data")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @Operation(
            summary = "Create new employee",
            description = "Creates a new employee in the system",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Employee creation data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EmployeeCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Employee created successfully",
                            content = @Content(schema = @Schema(implementation = EmployeeDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<EmployeeDTO>> create(@RequestBody EmployeeCreateDTO employeeCreateDTO) {
        EmployeeDTO result = employeeService.create(employeeCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(true, "Employee created successfully", result));
    }

    @PutMapping
    @Operation(
            summary = "Update existing employee",
            description = "Updates the information of an existing employee",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Employee update data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EmployeeUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee updated successfully",
                            content = @Content(schema = @Schema(implementation = EmployeeDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<EmployeeDTO>> update(@RequestBody EmployeeUpdateDTO employeeUpdateDTO) {
        EmployeeDTO result = employeeService.update(employeeUpdateDTO);
        return ResponseEntity.ok(new APIResponse<>(true, "Employee updated successfully", result));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete employee by ID",
            description = "Deletes an employee from the system using their ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Employee not found")
            }
    )
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id) {
        employeeService.delete(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Employee deleted successfully", null));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get employee by ID",
            description = "Retrieves the employee data using the given ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee retrieved successfully",
                            content = @Content(schema = @Schema(implementation = EmployeeDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Employee not found")
            }
    )
    public ResponseEntity<APIResponse<EmployeeDTO>> getById(@PathVariable Integer id) {
        EmployeeDTO result = employeeService.getDTOById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Employee retrieved successfully", result));
    }

    @GetMapping("/check-exists/{id}")
    @Operation(
            summary = "Check if employee exists by ID",
            description = "Checks whether an employee with the specified ID exists",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Check successful",
                            content = @Content(schema = @Schema(implementation = Boolean.class)))
            }
    )
    public ResponseEntity<APIResponse<Boolean>> checkExists(@PathVariable Integer id) {
        Boolean exists = employeeService.checkExists(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Check completed", exists));
    }

    @PostMapping("/filter")
    @Operation(
            summary = "Filter employees",
            description = "Returns a list of employees based on the provided filter criteria",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Employee filter criteria",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EmployeeFilter.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employees filtered successfully",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = EmployeeDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<EmployeeDTO>>> filter(
            @RequestBody EmployeeFilter employeeFilter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<EmployeeDTO> results = employeeService.filter(employeeFilter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Employees filtered successfully", results));
    }
}
