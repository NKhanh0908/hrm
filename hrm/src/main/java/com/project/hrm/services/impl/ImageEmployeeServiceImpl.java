package com.project.hrm.services.impl;

import com.project.hrm.dto.imageEmployeeDTO.ImageEmployeeDTO;
import com.project.hrm.entities.Employees;
import com.project.hrm.entities.ImageEmployee;
import com.project.hrm.exceptions.CustomException;
import com.project.hrm.exceptions.Error;
import com.project.hrm.repositories.ImageEmployeeRepository;
import com.project.hrm.services.ImageEmployeeService;
import com.project.hrm.utils.CloudinaryService;
import com.project.hrm.utils.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class ImageEmployeeServiceImpl implements ImageEmployeeService {
    private final CloudinaryService cloudinaryService;

    private final ImageEmployeeRepository imageEmployeeRepository;

    @Override
    public String saveImage(MultipartFile image) {
        checkImage(image);

        Map<String, Object> resultMap = cloudinaryService.upload(image);

        return (String) resultMap.get("url");
    }

    @Override
    public void deleteImage(String url) throws IOException {
        String publicId = cloudinaryService.getPublicId(url); // Trả về "abc123"
        Map result = cloudinaryService.delete(publicId);
    }

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
}
