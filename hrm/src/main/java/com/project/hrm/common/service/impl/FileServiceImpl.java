package com.project.hrm.common.service.impl;

import com.project.hrm.common.exceptions.CustomException;
import com.project.hrm.common.exceptions.Error;
import com.project.hrm.common.service.FileService;
import com.project.hrm.common.utils.CloudinaryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class FileServiceImpl implements FileService {
    private final CloudinaryService cloudinaryService;

    /**
     * Uploads an image to Cloudinary after validating file type and size.
     *
     * @param image the multipart file containing the image to be uploaded
     * @return the public URL of the uploaded image
     * @throws CustomException if the image fails validation
     */
    @Override
    public String saveImage(MultipartFile image) {
        checkImage(image);

        Map<String, Object> resultMap = cloudinaryService.upload(image);

        return (String) resultMap.get("url");
    }

    /**
     * Deletes an image from Cloudinary, given its public URL.
     *
     * @param url the public URL of the image to delete
     * @throws IOException if Cloudinary deletion fails
     */
    @Override
    public void deleteImage(String url) throws IOException {
        String publicId = cloudinaryService.getPublicId(url);
        Map result = cloudinaryService.delete(publicId);
    }

    /**
     * Validates the file type and size of the uploaded image.
     *
     * Accepted MIME types: {@code image/png}, {@code image/jpeg}, {@code image/jpg}.<br>
     * Maximum size: 10&nbsp;MB.
     *
     * @param multipartFile the file to validate
     * @throws CustomException if the file type or size is invalid
     */
    private void checkImage(MultipartFile multipartFile){
        List<Error> errors = new ArrayList<>();

        String fileType = multipartFile.getContentType();
        List<String> allowedTypes = List.of("image/png", "image/jpeg", "image/jpg");
        if (fileType == null || !allowedTypes.contains(fileType)) {
            errors.add(Error.INVALID_FILE_TYPE);
        }

        long maxFileSize = 10 * 1024 * 1024; // 5MB
        if (multipartFile.getSize() > maxFileSize) {
            errors.add(Error.FILE_SIZE_EXCEEDED);
        }

        if (!errors.isEmpty()) {
            throw new CustomException(errors);
        }
    }

    @Override
    public Map<String, Object> saveFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be null or empty");
        }

        Map<String, Object> uploadResult = cloudinaryService.upload(file);
        if (uploadResult == null || uploadResult.isEmpty()) {
            throw new RuntimeException("Failed to upload file to Cloudinary");
        }
        log.info("File uploaded successfully: {}", uploadResult);
        return uploadResult;
    }
}
