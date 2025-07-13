package com.project.hrm.notification.controller;

import com.project.hrm.common.response.APIResponse;

import com.project.hrm.notification.dto.NotificationCreateDTO;
import com.project.hrm.notification.dto.NotificationDTO;
import com.project.hrm.notification.dto.NotificationFilterDTO;
import com.project.hrm.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification Controller", description = "Manage user notifications in the HRM system")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping
    @Operation(
            summary = "Create a new notification",
            description = "Send a notification to a user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = NotificationCreateDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Notification created successfully",
                            content = @Content(schema = @Schema(implementation = NotificationCreateDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<NotificationDTO>> create(@RequestBody NotificationCreateDTO notificationCreateDTO, HttpServletRequest request) {
        NotificationDTO result = notificationService.create(notificationCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(true, "Notification created successfully", result, null, request.getRequestURI()));
    }

    @GetMapping
    @Operation(
            summary = "Get all notifications",
            description = "Retrieve all notifications for the authenticated user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = NotificationFilterDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filtered successfully",
                            content = @Content(schema = @Schema(implementation = NotificationDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No notifications found",
                            content = @Content(schema = @Schema(implementation = NotificationDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<List<NotificationDTO>>> getAllNotifications(
            @RequestBody NotificationFilterDTO filterDTO,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request
    ) {
        List<NotificationDTO> result = notificationService.filter(filterDTO, page, size);
        return ResponseEntity.ok(new APIResponse<>(true, "Filter completed", result, null, request.getRequestURI()));
    }

    @GetMapping("/{notificationId}")
    @Operation(
            summary = "Get notification by ID",
            description = "Retrieve a specific notification by its ID",
            parameters = {
                    @Parameter(name = "notificationId", description = "ID of the notification to retrieve", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Notification retrieved successfully",
                            content = @Content(schema = @Schema(implementation = NotificationDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Notification not found",
                            content = @Content(schema = @Schema(implementation = NotificationDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<NotificationDTO>> getById(@RequestParam Integer notificationId, HttpServletRequest request) {
        NotificationDTO result = notificationService.getDTOById(notificationId);
        return ResponseEntity.ok(new APIResponse<>(true, "Notification retrieved successfully", result, null, request.getRequestURI()));
    }

    @PatchMapping("/{notificationId}/read")
    @Operation(
            summary = "Mark notification as read",
            description = "Mark a specific notification as read",
            parameters = {
                    @Parameter(name = "notificationId", description = "ID of the notification to mark as read", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Notification marked as read successfully",
                            content = @Content(schema = @Schema(implementation = NotificationDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Notification not found",
                            content = @Content(schema = @Schema(implementation = NotificationDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<NotificationDTO>> markAsRead(@RequestParam Integer notificationId, HttpServletRequest request) {
        NotificationDTO result = notificationService.markAsRead(notificationId);
        return ResponseEntity.ok(new APIResponse<>(true, "Notification marked as read successfully",result, null, request.getRequestURI()));
    }

    @PatchMapping("/read-all/{recipientId}")
    @Operation(
            summary = "Mark all notifications as read",
            description = "Mark all notifications for a specific recipient as read",
            parameters = {
                    @Parameter(name = "recipientId", description = "ID of the recipient whose notifications will be marked as read", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "All notifications marked as read successfully"),
                    @ApiResponse(responseCode = "404", description = "Recipient not found")
            }
    )
    public ResponseEntity<APIResponse<List<NotificationDTO>>> markAllAsRead(@RequestParam Integer recipientId, HttpServletRequest request) {
        List<NotificationDTO> result = notificationService.markAllAsRead(recipientId);
        return ResponseEntity.ok(new APIResponse<>(true, "All notifications marked as read successfully", result, null, request.getRequestURI()));
    }

    @DeleteMapping("/{notificationId}")
    @Operation(
            summary = "Delete a notification",
            description = "Delete a specific notification by its ID",
            parameters = {
                    @Parameter(name = "notificationId", description = "ID of the notification to delete", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Notification deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Notification not found")
            }
    )
    public ResponseEntity<APIResponse<Void>> delete(@RequestParam Integer notificationId, HttpServletRequest request) {
        notificationService.delete(notificationId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new APIResponse<>(true, "Notification deleted successfully", null, null, request.getRequestURI()));
    }

    @GetMapping("/notifications-current-employee")
    @Operation(
            summary = "Get notifications for current employee",
            description = "Retrieve all notifications for the currently authenticated employee",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Notifications retrieved successfully",
                            content = @Content(schema = @Schema(implementation = NotificationDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No notifications found for the current employee",
                            content = @Content(schema = @Schema(implementation = NotificationDTO.class)))
            }
    )
    public ResponseEntity<APIResponse<List<NotificationDTO>>> getNotificationsForCurrentEmployee(HttpServletRequest request) {
        List<NotificationDTO> result = notificationService.getNotificationsForCurrentEmployee();
        return ResponseEntity.ok(new APIResponse<>(true, "Notifications retrieved successfully", result, null, request.getRequestURI()));
    }

    @MessageMapping("/send-notification")
    @SendTo("/topic/notifications")
    public NotificationDTO sendNotification(NotificationCreateDTO notificationCreateDTO) {
        return notificationService.create(notificationCreateDTO);
    }
}
