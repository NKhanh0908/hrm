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

@Slf4j
@Service
public class CloudinaryService {
    public Cloudinary cloudinary;

    @Value("${cloudinary.name}")
    private String CLOUD_NAME;

    @Value("${cloudinary.api-key}")
    private String CLOUD_API_KEY;

    @Value("${cloudinary.api-secret}")
    private String CLOUD_API_SECRET;

    public CloudinaryService() {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("cloud_name", CLOUD_NAME);
        valuesMap.put("api_key", CLOUD_API_KEY);
        valuesMap.put("api_secret", CLOUD_API_SECRET);
        cloudinary = new Cloudinary(valuesMap);
    }

    public Map upload(MultipartFile multipartFile) {
        try {
            log.info("Uploading photo to cloud: {}", multipartFile.getOriginalFilename());
            File file = convert(multipartFile);
            Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            if (!Files.deleteIfExists(file.toPath())) {
                log.error("Unable to upload file by Delete If Exists: {}", file.getAbsolutePath());
                throw new IOException("Unable to upload temporary file: " + file.getAbsolutePath());
            }
            return result;
        } catch (IOException e) {
            log.error("Unable to upload file: {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    public Map delete(String id) throws IOException {
        try {
            log.info("Deleting photo from cloud: {}", id);
            return cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        } catch (IOException e) {
            log.error("Unable to delete file: {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    public String getPublicId(String url) {
        String[] parts = url.split("/");
        String publicIdWithExtension = parts[parts.length - 1];
        String publicId = publicIdWithExtension.split("\\.")[0];
        return publicId;
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        try {
            File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(multipartFile.getBytes());
            fo.close();
            return file;
        } catch (IOException e) {
            log.error("Unable to convert file: {}", e.getMessage());
            throw new RuntimeException();
        }
    }
}
