package com.project.hrm.controllers;

import com.project.hrm.dto.APIResponse;
import com.project.hrm.services.FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
//@Tag(name = "File Controller", description = "File upload controller")
public class FileUploadController {
    private final FileService fileService;

    @PostMapping
    public ResponseEntity<APIResponse<String>> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
            String filePath = fileService.saveImage(file);

            return ResponseEntity.ok(new APIResponse<>(true, "Employee updated successfully", filePath, null,
                    request.getRequestURI()));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
