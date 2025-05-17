package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.departmentDTO.DepartmentCreateDTO;
import com.project.hrm.dto.departmentDTO.DepartmentDTO;
import com.project.hrm.dto.departmentDTO.DepartmentFilter;
import com.project.hrm.dto.departmentDTO.DepartmentUpdateDTO;
import com.project.hrm.services.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
@Tag(name = "Department Controller", description = "Department manager")
public class DepartmentController {
    private final DepartmentService departmentService;

    @Operation(
            summary = "Create department",
            description = "Create new department",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Department create information",
                    required = true,
                    content = @Content(schema = @Schema(implementation = DepartmentCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Create Department successfully",
                            content = @Content(schema = @Schema(implementation = DepartmentDTO.class))
                    )
            }
    )
    @PostMapping
    public ResponseEntity<APIResponse<DepartmentDTO>> create(@RequestBody DepartmentCreateDTO departmentCreateDTO){
        DepartmentDTO departmentDTO = departmentService.create(departmentCreateDTO);
        return ResponseEntity.ok(new APIResponse<>(true, "Create Department successfully", departmentDTO));
    }

    @PutMapping
    @Operation(
            summary = "Update department",
            description = "Update existing department information by its ID. Only non-null fields will be updated.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Department update information",
                    required = true,
                    content = @Content(schema = @Schema(implementation = DepartmentUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Update department successfully",
                            content = @Content(schema = @Schema(implementation = DepartmentDTO.class))
                    )
            }
    )
    public DepartmentDTO update(@RequestBody DepartmentUpdateDTO departmentUpdateDTO) {
        return departmentService.update(departmentUpdateDTO);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get department by ID",
            description = "Retrieve department details using its ID.",
            parameters = {
                    @Parameter(name = "id", description = "Department ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successfully retrieved department",
                            content = @Content(schema = @Schema(implementation = DepartmentDTO.class))
                    )
            }
    )
    public DepartmentDTO getById(@PathVariable Integer id) {
        return departmentService.getById(id);
    }

    @PostMapping("/filter")
    @Operation(
            summary = "Filter departments",
            description = "Filter departments by name, description, address, email, or phone with pagination.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Filter conditions",
                    required = true,
                    content = @Content(schema = @Schema(implementation = DepartmentFilter.class))
            ),
            parameters = {
                    @Parameter(name = "page", description = "Page number (0-based)", required = false, example = "0"),
                    @Parameter(name = "size", description = "Page size", required = false, example = "10")
            },
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Filtered department list",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = DepartmentDTO.class)))
                    )
            }
    )
    public List<DepartmentDTO> filter(@RequestBody DepartmentFilter departmentFilter,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return departmentService.filter(departmentFilter, page, size);
    }

    @PutMapping("/appointment-of-dean")
    @Operation(
            summary = "Appoint department dean",
            description = "Appoint a dean (head) for a department by specifying department ID and employee ID.",
            parameters = {
                    @Parameter(name = "departmentId", description = "ID of the department to update", required = true),
                    @Parameter(name = "employeeId", description = "ID of the employee to appoint as dean", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Dean appointed successfully",
                            content = @Content(schema = @Schema(implementation = DepartmentDTO.class))
                    )
            }
    )
    public DepartmentDTO appointmentOfDean(@RequestParam Integer departmentId,
                                           @RequestParam Integer employeeId) {
        return departmentService.appointmentOfDean(departmentId, employeeId);
    }
}
