package com.project.hrm.services;

import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Server
public interface ImageEmployeeService {
    String saveImage(MultipartFile image);

    void deleteImage(String url) throws IOException;
}
