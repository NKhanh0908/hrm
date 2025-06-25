package com.project.hrm.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
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

    public CloudinaryService() {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("cloud_name", "dswwzexhq");
        valuesMap.put("api_key", "962968498882681");
        valuesMap.put("api_secret", "xbW1T2RbBhKK-qLOi8Ov4yF274o");
        cloudinary = new Cloudinary(valuesMap);
    }

    public Map<String, Object> upload(MultipartFile multipartFile) {
        File file = null;
        try {
            // Tạo thông tin metadata
            String originalFileName = multipartFile.getOriginalFilename();
            long fileSize = multipartFile.getSize();
            String customFileName = generateCustomFileName(originalFileName);

            log.info("Uploading photo to cloud. Original: {}, Size: {} bytes, Custom Name: {}",
                    originalFileName, fileSize, customFileName);

            // Chuyển đổi và upload
            file = convert(multipartFile, customFileName);
            String filePath = file.getAbsolutePath();

            Map<String, Object> uploadOptions = new HashMap<>();
            uploadOptions.put("public_id", customFileName); // Sử dụng tên tuỳ chỉnh

            Map<String, Object> result = cloudinary.uploader().upload(file, uploadOptions);

            // Bổ sung thông tin metadata
            result.put("originalFileName", originalFileName);
            result.put("fileSize", fileSize);
            result.put("customFileName", customFileName);
            result.put("localFilePath", filePath);

            // Xoá file tạm
            if (!Files.deleteIfExists(file.toPath())) {
                log.warn("Temporary file not found: {}", filePath);
            }
            return result;

        } catch (IOException e) {
            log.error("Upload failed: {}", e.getMessage());
            throw new RuntimeException("Upload failed", e);
        } finally {
            // Đảm bảo xoá file tạm nếu có lỗi
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
        String extension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        return "custom_" + UUID.randomUUID() + extension;
    }
}
