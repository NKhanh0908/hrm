package com.project.hrm.employee.controller;

import com.project.hrm.common.response.APIResponse;
import com.project.hrm.common.response.PageDTO;
import com.project.hrm.employee.dto.attendanceDTO.*;
import com.project.hrm.employee.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendances")
@RequiredArgsConstructor
@Tag(name = "Attendance Controller", description = "Manage attendance records")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    @Operation(
            summary = "Create Attendance",
            description = "Create a new attendance record",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Attendance creation data",
                    content = @Content(schema = @Schema(implementation = AttendanceCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Attendance created successfully",
                            content = @Content(schema = @Schema(implementation = AttendanceDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<AttendanceDTO>> create(@RequestBody @Valid AttendanceCreateDTO dto,
                                                             HttpServletRequest request) {
        AttendanceDTO result = attendanceService.create(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Create attendance successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update Attendance",
            description = "Update an existing attendance record",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Attendance update data",
                    content = @Content(schema = @Schema(implementation = AttendanceUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Attendance updated successfully",
                            content = @Content(schema = @Schema(implementation = AttendanceDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<AttendanceDTO>> update(@RequestBody @Valid AttendanceUpdateDTO dto,
                                                             HttpServletRequest request) {
        AttendanceDTO result = attendanceService.update(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Update attendance successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Attendance by ID",
            parameters = @Parameter(name = "id", description = "Attendance ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Attendance found",
                            content = @Content(schema = @Schema(implementation = AttendanceDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<AttendanceDTO>> getById(@PathVariable Integer id,
                                                              HttpServletRequest request) {
        AttendanceDTO result = attendanceService.getById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Get attendance successfully", result, null, request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Attendance by ID",
            parameters = @Parameter(name = "id", description = "Attendance ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Attendance deleted successfully")
            }
    )
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id,
                                                    HttpServletRequest request) {
        attendanceService.delete(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Delete attendance successfully", null, null, request.getRequestURI()));
    }

    @PostMapping("/filter")
    @Operation(
            summary = "Filter Attendances",
            description = "Filter attendance records by attributes",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Filter criteria",
                    content = @Content(schema = @Schema(implementation = AttendanceFilter.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filtered attendances",
                            content = @Content(schema = @Schema(implementation = PageDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PageDTO<AttendanceDTO>>> filter(@RequestBody AttendanceFilter filter,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size,
                                                                      HttpServletRequest request) {
        PageDTO<AttendanceDTO> result = attendanceService.filter(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter attendance successfully", result, null, request.getRequestURI()));
    }

    @PostMapping("/filter-range")
    @Operation(
            summary = "Filter Attendances with Range",
            description = "Filter attendances by check-in/out time or date range",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Range filter criteria",
                    content = @Content(schema = @Schema(implementation = AttendanceFilterWithRange.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filtered attendances with range",
                            content = @Content(schema = @Schema(implementation = PageDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<PageDTO<AttendanceDTO>>> filterRange(@RequestBody AttendanceFilterWithRange filterWithRange,
                                                                           @RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size,
                                                                           HttpServletRequest request) {
        PageDTO<AttendanceDTO> result = attendanceService.filterWithRange(filterWithRange, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter attendance with range successfully", result, null, request.getRequestURI()));
    }

    @PostMapping("/check-in")
    @Operation(
            summary = "Check In",
            description = "Create attendance record when employee clicks check-in",
            parameters = @Parameter(name = "employeeId", description = "Employee ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Checked in successfully",
                            content = @Content(schema = @Schema(implementation = AttendanceDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<AttendanceDTO>> checkIn(@RequestParam Integer employeeId,
                                                              HttpServletRequest request) {
        AttendanceDTO result = attendanceService.createWhenClickCheckIn(employeeId);
        return ResponseEntity.ok(new APIResponse<>(true, "Check-in successfully", result, null, request.getRequestURI()));
    }

    @PostMapping("/check-out")
    @Operation(
            summary = "Check Out",
            description = "Update attendance record when employee clicks check-out",
            parameters = @Parameter(name = "employeeId", description = "Employee ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Checked out successfully",
                            content = @Content(schema = @Schema(implementation = AttendanceDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<AttendanceDTO>> checkOut(@RequestParam Integer employeeId,
                                                               HttpServletRequest request) {
        AttendanceDTO result = attendanceService.setAttendanceWhenClickCheckOut(employeeId);
        return ResponseEntity.ok(new APIResponse<>(true, "Check-out successfully", result, null, request.getRequestURI()));
    }
}
