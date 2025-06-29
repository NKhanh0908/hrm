package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.dto.PageDTO;
import com.project.hrm.dto.dayOffDTO.*;
import com.project.hrm.services.DayOffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/day-offs")
@RequiredArgsConstructor
@Tag(name = "DayOff Controller", description = "Quản lý đơn nghỉ phép của nhân viên")
public class DayOffController {

    private final DayOffService dayOffService;

    @PostMapping
    @Operation(summary = "Tạo đơn nghỉ phép mới",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dữ liệu tạo đơn nghỉ phép",
                    required = true,
                    content = @Content(schema = @Schema(implementation = DayOffCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tạo đơn nghỉ thành công", content = @Content(schema = @Schema(implementation = DayOffDTO.class)))
            })
    public ResponseEntity<APIResponse<DayOffDTO>> create(@RequestBody DayOffCreateDTO dto, HttpServletRequest request) {
        DayOffDTO result = dayOffService.create(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Create DayOff successfully", result, null, request.getRequestURI()));
    }

    @PutMapping
    @Operation(summary = "Cập nhật đơn nghỉ phép",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dữ liệu cập nhật",
                    required = true,
                    content = @Content(schema = @Schema(implementation = DayOffUpdateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cập nhật đơn nghỉ thành công", content = @Content(schema = @Schema(implementation = DayOffDTO.class)))
            })
    public ResponseEntity<APIResponse<DayOffDTO>> update(@RequestBody DayOffUpdateDTO dto, HttpServletRequest request) {
        DayOffDTO result = dayOffService.update(dto);
        return ResponseEntity.ok(new APIResponse<>(true, "Update DayOff successfully", result, null, request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa đơn nghỉ phép theo ID",
            parameters = @Parameter(name = "id", description = "ID đơn nghỉ", required = true),
            responses = @ApiResponse(responseCode = "200", description = "Xóa thành công"))
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable Integer id, HttpServletRequest request) {
        dayOffService.delete(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Delete DayOff successfully", null, null, request.getRequestURI()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin đơn nghỉ theo ID",
            parameters = @Parameter(name = "id", description = "ID đơn nghỉ", required = true),
            responses = @ApiResponse(responseCode = "200", description = "Tìm thấy đơn nghỉ", content = @Content(schema = @Schema(implementation = DayOffDTO.class))))
    public ResponseEntity<APIResponse<DayOffDTO>> getById(@PathVariable Integer id, HttpServletRequest request) {
        DayOffDTO result = dayOffService.getById(id);
        return ResponseEntity.ok(new APIResponse<>(true, "Get DayOff successfully", result, null, request.getRequestURI()));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Lấy danh sách đơn nghỉ theo mã nhân viên",
            parameters = @Parameter(name = "employeeId", description = "Mã nhân viên", required = true),
            responses = @ApiResponse(responseCode = "200", description = "Danh sách đơn nghỉ", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DayOffDTO.class)))))
    public ResponseEntity<APIResponse<List<DayOffDTO>>> getByEmployeeId(@PathVariable Integer employeeId, HttpServletRequest request) {
        List<DayOffDTO> list = dayOffService.getDayOffsByEmployeeId(employeeId);
        return ResponseEntity.ok(new APIResponse<>(true, "Get DayOffs by employeeId successfully", list, null, request.getRequestURI()));
    }

    @PostMapping("/filter")
    @Operation(summary = "Lọc đơn nghỉ theo điều kiện cơ bản",
            responses = @ApiResponse(responseCode = "200", description = "Danh sách đơn nghỉ", content = @Content(schema = @Schema(implementation = PageDTO.class))))
    public ResponseEntity<APIResponse<PageDTO<DayOffDTO>>> filter(@RequestBody DayOffFilter filter,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size,
                                                               HttpServletRequest request) {
        PageDTO<DayOffDTO> list = dayOffService.filter(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter DayOffs successfully", list, null, request.getRequestURI()));
    }

    @GetMapping("/filter-dynamic")
    @Operation(summary = "Lọc đơn nghỉ nâng cao theo thời gian và trạng thái",
            responses = @ApiResponse(responseCode = "200", description = "Danh sách đơn nghỉ", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DayOffDTO.class)))))
    public ResponseEntity<APIResponse<List<DayOffDTO>>> filterDynamic(
            @RequestBody DayOffFilterDynamic filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request
    ) {
        List<DayOffDTO> list = dayOffService.filterDynamic(filter, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Dynamic Filter DayOffs successfully", list, null, request.getRequestURI()));
    }
}
