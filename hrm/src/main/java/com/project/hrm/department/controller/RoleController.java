package com.project.hrm.department.controller;

import com.project.hrm.common.response.APIResponse;
import com.project.hrm.department.dto.roleDTO.RoleCreateDTO;
import com.project.hrm.department.dto.roleDTO.RoleDTO;
import com.project.hrm.department.service.RoleService;
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
@RequestMapping("/role")
@RequiredArgsConstructor
@Tag(name = "Role Controller", description = "Manage roles and their assignments to departments")
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    @Operation(
            summary = "Create a new role",
            description = "Creates a new role and assigns it to a department.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Role creation data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RoleCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Role created successfully",
                            content = @Content(schema = @Schema(implementation = RoleDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<RoleDTO>> create(
            @RequestBody RoleCreateDTO roleCreateDTO,
            HttpServletRequest request) {
        RoleDTO roleDTO = roleService.create(roleCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new APIResponse<>(true, "Create role successfully", roleDTO, null, request.getRequestURI())
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get role by ID",
            description = "Retrieve role details using its ID.",
            parameters = @Parameter(name = "id", description = "Role ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved role",
                            content = @Content(schema = @Schema(implementation = RoleDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<RoleDTO>> getById(
            @PathVariable Integer id,
            HttpServletRequest request) {
        RoleDTO roleDTO = roleService.getDTOById(id);
        return ResponseEntity.ok(
                new APIResponse<>(true, "Get role successfully", roleDTO, null, request.getRequestURI())
        );
    }

    @GetMapping("/department/{departmentId}")
    @Operation(
            summary = "Get all roles by department ID",
            description = "Returns all roles assigned to a specific department.",
            parameters = @Parameter(name = "departmentId", description = "Department ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Roles retrieved successfully",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = RoleDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<RoleDTO>>> getAllByDepartmentId(
            @PathVariable Integer departmentId,
            HttpServletRequest request) {
        List<RoleDTO> roles = roleService.getAllByDepartmentId(departmentId);
        return ResponseEntity.ok(
                new APIResponse<>(true, "Get roles by department successfully", roles, null, request.getRequestURI())
        );
    }
}
