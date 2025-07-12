package com.project.hrm.common.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ReportExceptionHandler {

    @ExceptionHandler(ReportGenerationException.class)
    public ResponseEntity<Map<String, Object>> handleReportGenerationException(
            ReportGenerationException ex) {

        log.error("Report generation error: ", ex);

        Map<String, Object> response = new HashMap<>();
        response.put("error", "REPORT_GENERATION_ERROR");
        response.put("message", ex.getMessage());
        response.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(ReportDataException.class)
    public ResponseEntity<Map<String, Object>> handleReportDataException(
            ReportDataException ex) {

        log.error("Report data error: ", ex);

        Map<String, Object> response = new HashMap<>();
        response.put("error", "REPORT_DATA_ERROR");
        response.put("message", ex.getMessage());
        response.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        log.error("Unexpected error in report generation: ", ex);

        Map<String, Object> response = new HashMap<>();
        response.put("error", "INTERNAL_SERVER_ERROR");
        response.put("message", "An unexpected error occurred while generating the report");
        response.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

// Custom Exception Classes
class ReportGenerationException extends RuntimeException {
    public ReportGenerationException(String message) {
        super(message);
    }

    public ReportGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}

class ReportDataException extends RuntimeException {
    public ReportDataException(String message) {
        super(message);
    }

    public ReportDataException(String message, Throwable cause) {
        super(message, cause);
    }
}