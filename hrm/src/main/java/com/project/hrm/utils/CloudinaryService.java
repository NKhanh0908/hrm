package com.project.hrm.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class CloudinaryService {
    public Cloudinary cloudinary;

    public CloudinaryService(
            @Value("${cloudinary.name}") String cloudName,
            @Value("${cloudinary.api-key}") String apiKey,
            @Value("${cloudinary.api-secret}") String apiSecret) {

        this.cloudinary = new Cloudinary(Map.of(
                "cloud_name", cloudName,
                "api_key",    apiKey,
                "api_secret", apiSecret
        ));
    }

    public Map<String, Object> upload(MultipartFile multipartFile) {
        File file = null;
        try {
            String originalFileName = multipartFile.getOriginalFilename();
            Long fileSize = multipartFile.getSize();
            String customFileName = generateCustomFileName(originalFileName);

            log.info("Uploading photo to cloud. Original: {}, Size: {} bytes, Custom Name: {}",
                    originalFileName, fileSize, customFileName);

            file = convert(multipartFile, customFileName);
            String filePath = file.getAbsolutePath();

            String contentType = multipartFile.getContentType();
            boolean isImage = contentType != null && contentType.startsWith("image");

            Map<String, Object> uploadOptions = new HashMap<>();
            uploadOptions.put("public_id", customFileName);
            uploadOptions.put("resource_type", isImage ? "image" : "raw");

            Map<String, Object> result = cloudinary.uploader().upload(file, uploadOptions);

            result.put("originalFileName", originalFileName);
            result.put("fileSize", fileSize);
            result.put("customFileName", customFileName);
            result.put("localFilePath", filePath);

            if (!Files.deleteIfExists(file.toPath())) {
                log.warn("Temporary file not found: {}", filePath);
            }
            return result;

        } catch (IOException e) {
            log.error("Upload failed: {}", e.getMessage());
            throw new RuntimeException("Upload failed", e);
        } finally {
            if (file != null && file.exists()) {
                try {
                    Files.deleteIfExists(file.toPath());
                } catch (IOException ex) {
                    log.error("Cleanup failed: {}", ex.getMessage());
                }
            }
        }
    }

    public Map delete(String id) throws IOException {
        try {
            log.info("Deleting photo from cloud: {}", id);
            return cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        } catch (IOException e) {
            log.error("Delete failed: {}", e.getMessage());
            throw new RuntimeException("Delete failed", e);
        }
    }

    public String getPublicId(String url) {
        String[] parts = url.split("/");
        String publicIdWithExtension = parts[parts.length - 1];
        return publicIdWithExtension.split("\\.")[0];
    }

    private File convert(MultipartFile multipartFile, String fileName) throws IOException {
        FileOutputStream fos = null;
        try {
            File file = new File(fileName);
            fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            return file;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    log.error("Stream close error: {}", e.getMessage());
                }
            }
        }
    }

    private String generateCustomFileName(String originalFileName) {
        return "custom_" + UUID.randomUUID() ;
    }
}
