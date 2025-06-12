package com.project.hrm.services;

import com.project.hrm.dto.imageEmployeeDTO.ImageEmployeeDTO;
import com.project.hrm.entities.Employees;
import com.project.hrm.entities.ImageEmployee;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Server
public interface ImageEmployeeService {
    String saveImage(MultipartFile image);

    void deleteImage(String url) throws IOException;
}
