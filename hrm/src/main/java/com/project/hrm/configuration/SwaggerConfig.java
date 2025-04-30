package com.project.hrm.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Human resource Management System API Documentation")
                        .version("1.0")
                        .description("API Documentation for HRM Project")
                );
    }
}
