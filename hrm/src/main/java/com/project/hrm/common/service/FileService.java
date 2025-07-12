package com.project.hrm.common.service;

import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Server
public interface FileService {
    String saveImage(MultipartFile image);
    Map<String, Object> saveFile(MultipartFile file);
    void deleteImage(String url) throws IOException;
}
