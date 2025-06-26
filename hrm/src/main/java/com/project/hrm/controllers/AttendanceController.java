package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.attendanceDTO.*;
import com.project.hrm.services.AttendanceService;
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
@RequestMapping("/attendances")
@RequiredArgsConstructor
@Tag(name = "Attendance Controller", description = "Manage employee attendance records")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    @Operation(
            summary = "Create Attendance",
            description = "Create a new attendance record",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Attendance creation data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AttendanceCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Attendance created successfully", content = @Content(schema = @Schema(implementation = AttendanceDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<AttendanceDTO>> create(@RequestBody AttendanceCreateDTO dto,
                                                             HttpServletRequest request) {
        AttendanceDTO result = attendanceService.create(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Create attendance successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(
            summary = "Update Attendance",
            description = "Update an existing attendance record",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Attendance update data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AttendanceUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Attendance updated successfully", content = @Content(schema = @Schema(implementation = AttendanceDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<AttendanceDTO>> update(@RequestBody AttendanceUpdateDTO dto,
                                                             HttpServletRequest request) {
        AttendanceDTO result = attendanceService.update(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Update attendance successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Attendance by ID",
            parameters = @Parameter(name = "id", description = "Attendance ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Attendance found", content = @Content(schema = @Schema(implementation = AttendanceDTO.class)))
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
            description = "Filter attendance records by exact values",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Exact filter criteria",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AttendanceFilter.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filtered attendances", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AttendanceDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<AttendanceDTO>>> filter(@RequestBody AttendanceFilter filter,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size,
                                                                   HttpServletRequest request) {
        List<AttendanceDTO> list = attendanceService.filter(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter attendances successfully", list, null, request.getRequestURI()));
    }

    @PostMapping("/filter-range")
    @Operation(
            summary = "Filter Attendances with Range",
            description = "Filter attendance records by date/time ranges",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Range filter criteria",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AttendanceFilterWithRange.class))
            ),
            parameters = {
                    @Parameter(name = "page", description = "Page number"),
                    @Parameter(name = "size", description = "Page size")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filtered attendances with range", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AttendanceDTO.class))))
            }
    )
    public ResponseEntity<APIResponse<List<AttendanceDTO>>> filterRange(@RequestBody AttendanceFilterWithRange filterWithRange,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size,
                                                                        HttpServletRequest request) {
        List<AttendanceDTO> list = attendanceService.filterWithRange(filterWithRange, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter attendances with range successfully", list, null, request.getRequestURI()));
    }

    @PostMapping("/check-in/{employeeId}")
    @Operation(
            summary = "Employee Check-in",
            description = "Create a check-in attendance record for the specified employee",
            parameters = @Parameter(name = "employeeId", description = "Employee ID performing check-in", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Checked in successfully", content = @Content(schema = @Schema(implementation = AttendanceDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<AttendanceDTO>> checkIn(@PathVariable Integer employeeId,
                                                              HttpServletRequest request) {
        AttendanceDTO result = attendanceService.createWhenClickCheckIn(employeeId);
        return ResponseEntity.ok(new APIResponse<>(true, "Check-in successful", result, null, request.getRequestURI()));
    }

    @PostMapping("/check-out/{employeeId}")
    @Operation(
            summary = "Employee Check-out",
            description = "Set check-out time and calculate working hours for the employee",
            parameters = @Parameter(name = "employeeId", description = "Employee ID performing check-out", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Checked out successfully", content = @Content(schema = @Schema(implementation = AttendanceDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<AttendanceDTO>> checkOut(@PathVariable Integer employeeId,
                                                               HttpServletRequest request) {
        AttendanceDTO result = attendanceService.setAttendanceWhenClickCheckOut(employeeId);
        return ResponseEntity.ok(new APIResponse<>(true, "Check-out successful", result, null, request.getRequestURI()));
    }

    @GetMapping("/unchecked-out/{employeeId}")
    @Operation(
            summary = "Check if Employee has Unchecked-out Attendance Today",
            description = "Check whether the employee has already checked in today without checking out",
            parameters = @Parameter(name = "employeeId", description = "Employee ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status retrieved", content = @Content(schema = @Schema(implementation = Boolean.class)))
            }
    )
    public ResponseEntity<APIResponse<Boolean>> hasUncheckedOutToday(@PathVariable Integer employeeId,
                                                                     HttpServletRequest request) {
        boolean hasUnfinished = attendanceService.hasUncheckedOutAttendanceOnDate(java.time.LocalDateTime.now());
        return ResponseEntity.ok(new APIResponse<>(true, "Checked attendance status successfully", hasUnfinished, null, request.getRequestURI()));
    }
}
