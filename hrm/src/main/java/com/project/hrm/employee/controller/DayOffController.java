package com.project.hrm.employee.controller;

import com.project.hrm.common.response.APIResponse;
import com.project.hrm.common.response.PageDTO;
import com.project.hrm.employee.dto.dayOffDTO.*;
import com.project.hrm.payroll.entities.PayPeriods;
import com.project.hrm.employee.service.DayOffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/day-offs")
@RequiredArgsConstructor
@Tag(name = "DayOff Controller", description = "Manage employee day off requests")
public class DayOffController {

    private final DayOffService dayOffService;

    @PostMapping
    @Operation(summary = "Create Day Off", responses = {
            @ApiResponse(responseCode = "200", description = "Created successfully", content = @Content(schema = @Schema(implementation = DayOffDTO.class)))
    })
    public ResponseEntity<APIResponse<DayOffDTO>> create(@RequestBody @Valid DayOffCreateDTO dto,
                                                         HttpServletRequest request) {
        DayOffDTO result = dayOffService.create(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Create day off successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(summary = "Update Day Off", responses = {
            @ApiResponse(responseCode = "200", description = "Updated successfully", content = @Content(schema = @Schema(implementation = DayOffDTO.class)))
    })
    public ResponseEntity<APIResponse<DayOffDTO>> update(@RequestBody @Valid DayOffUpdateDTO dto,
                                                         HttpServletRequest request) {
        DayOffDTO result = dayOffService.update(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Update day off successfully", result, null, request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Day Off by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Deleted successfully")
    })
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id,
                                                    HttpServletRequest request) {
        dayOffService.delete(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Delete day off successfully", null, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Day Off by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Found", content = @Content(schema = @Schema(implementation = DayOffDTO.class)))
    })
    public ResponseEntity<APIResponse<DayOffDTO>> getById(@PathVariable Integer id,
                                                          HttpServletRequest request) {
        DayOffDTO result = dayOffService.getById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Get day off successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get Day Offs by Employee ID", responses = {
            @ApiResponse(responseCode = "200", description = "Found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DayOffDTO.class))))
    })
    public ResponseEntity<APIResponse<List<DayOffDTO>>> getByEmployeeId(@PathVariable Integer employeeId,
                                                                        HttpServletRequest request) {
        List<DayOffDTO> result = dayOffService.getDayOffsByEmployeeId(employeeId);
        return ResponseEntity.ok(new APIResponse<>(true, "Get day offs by employee successfully", result, null, request.getRequestURI()));
    }

    @PostMapping("/filter")
    @Operation(summary = "Filter Day Offs", responses = {
            @ApiResponse(responseCode = "200", description = "Filtered", content = @Content(schema = @Schema(implementation = PageDTO.class)))
    })
    public ResponseEntity<APIResponse<PageDTO<DayOffDTO>>> filter(@RequestBody DayOffFilter filter,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size,
                                                                  HttpServletRequest request) {
        PageDTO<DayOffDTO> result = dayOffService.filter(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter day offs successfully", result, null, request.getRequestURI()));
    }

    @PostMapping("/filter-dynamic")
    @Operation(summary = "Dynamic Filter Day Offs", responses = {
            @ApiResponse(responseCode = "200", description = "Filtered", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DayOffDTO.class))))
    })
    public ResponseEntity<APIResponse<List<DayOffDTO>>> filterDynamic(@RequestBody DayOffFilterDynamic filter,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size,
                                                                      HttpServletRequest request) {
        List<DayOffDTO> result = dayOffService.filterDynamic(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter day offs dynamically successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/count")
    @Operation(summary = "Count Day Offs in Date Range for Employee")
    public ResponseEntity<APIResponse<Integer>> countDayOffByEmployeeId(@RequestParam Integer employeeId,
                                                                        @RequestParam LocalDateTime start,
                                                                        @RequestParam LocalDateTime end,
                                                                        HttpServletRequest request) {
        int count = dayOffService.countDayOffByEmployeeId(employeeId, start, end);
        return ResponseEntity.ok(new APIResponse<>(true, "Counted successfully", count, null, request.getRequestURI()));
    }

    @GetMapping("/count-status")
    @Operation(summary = "Count Pending Day Offs in Date Range for Employee")
    public ResponseEntity<APIResponse<Integer>> countDayOffByEmployeeIdStatus(@RequestParam Integer employeeId,
                                                                              @RequestParam LocalDateTime start,
                                                                              @RequestParam LocalDateTime end,
                                                                              HttpServletRequest request) {
        int count = dayOffService.countDayOffByEmployeeIdStatus(employeeId, start, end);
        return ResponseEntity.ok(new APIResponse<>(true, "Counted pending day offs successfully", count, null, request.getRequestURI()));
    }

    @PostMapping("/batch-count")
    @Operation(summary = "Get Day Off Count for Multiple Employees in Pay Period")
    public ResponseEntity<APIResponse<Map<Integer, Integer>>> getBatchDayOffCount(@RequestBody List<Integer> employeeIds,
                                                                                  @RequestParam LocalDateTime start,
                                                                                  @RequestParam LocalDateTime end,
                                                                                  HttpServletRequest request) {
        PayPeriods period = new PayPeriods();
        period.setStartDate(start);
        period.setEndDate(end);
        Map<Integer, Integer> result = dayOffService.getBatchDayOffCount(employeeIds, period);
        return ResponseEntity.ok(new APIResponse<>(true, "Get batch day off count successfully", result, null, request.getRequestURI()));
    }

    @PostMapping("/batch-not-accept-count")
    @Operation(summary = "Get Not Accepted Day Off Count for Multiple Employees in Pay Period")
    public ResponseEntity<APIResponse<Map<Integer, Integer>>> getBatchDayOffNotAcceptCount(@RequestBody List<Integer> employeeIds,
                                                                                           @RequestParam LocalDateTime start,
                                                                                           @RequestParam LocalDateTime end,
                                                                                           HttpServletRequest request) {
        PayPeriods period = new PayPeriods();
        period.setStartDate(start);
        period.setEndDate(end);
        Map<Integer, Integer> result = dayOffService.getBatchDayOffNotAcceptCount(employeeIds, period);
        return ResponseEntity.ok(new APIResponse<>(true, "Get batch not accepted day off count successfully", result, null, request.getRequestURI()));
    }
}
