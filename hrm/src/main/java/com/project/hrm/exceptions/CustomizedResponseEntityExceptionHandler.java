package com.project.hrm.exceptions;

import com.project.hrm.dto.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CustomizedResponseEntityExceptionHandler {

        @ExceptionHandler(BadRequestException.class)
        public final ResponseEntity<APIResponse<Object>> handleBadRequestException(BadRequestException ex,
                                                                                   WebRequest request) {
                APIResponse<Object> response = new APIResponse<>(
                                false,
                                ex.getMessage(),
                                null,
                                Collections.singletonList(ex.getMessage()),
                                request.getDescription(false));
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public final ResponseEntity<APIResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex,
                        WebRequest request) {
                List<String> errors = ex.getBindingResult().getAllErrors().stream()
                                .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
                                .collect(Collectors.toList());

                APIResponse<Object> response = new APIResponse<>(
                                false,
                                "Validation failed",
                                null,
                                errors,
                                request.getDescription(false));

                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(CustomException.class)
        public final ResponseEntity<APIResponse<Object>> handleCustomException(CustomException cx, WebRequest request) {
                // Chọn danh sách lỗi phù hợp: có ID hay không dựa trên additionalDetail
                List<String> errorMessages = (cx.getAdditionalDetail() != null)
                                ? cx.getErrorMessagesWithId()
                                : cx.getErrorMessages();

                APIResponse<Object> response = new APIResponse<>(
                                false,
                                cx.getMessage(),
                                null,
                                errorMessages,
                                request.getDescription(false));

                // Trả về ResponseEntity với mã trạng thái từ CustomException
                return new ResponseEntity<>(response, cx.getStatusCode());
        }

        @ExceptionHandler(Exception.class)
        public final ResponseEntity<APIResponse<Object>> handleAllException(Exception e, WebRequest request) {
                APIResponse<Object> response = new APIResponse<>(
                                false,
                                "An unexpected error occurred",
                                null,
                                Collections.singletonList(e.getMessage()),
                                request.getDescription(false));
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(AuthenticationException.class)
        public final ResponseEntity<APIResponse<Object>> handleAuthenticationException(AuthenticationException ex,
                        WebRequest request) {
                APIResponse<Object> response = new APIResponse<>(
                                false,
                                "Authentication failed",
                                null,
                                Collections.singletonList("Unauthorized access"),
                                request.getDescription(false));
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        // Handle 403 Forbidden
        @ExceptionHandler(AccessDeniedException.class)
        public final ResponseEntity<APIResponse<Object>> handleAccessDeniedException(AccessDeniedException ex,
                        WebRequest request) {
                APIResponse<Object> response = new APIResponse<>(
                                false,
                                "Access denied",
                                null,
                                Collections.singletonList("You do not have permission to access this resource"),
                                request.getDescription(false));
                return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

}
