package com.project.internship.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI configureOpenApi() {
        return new OpenAPI().info(new Info().title("Social Events")
                .description("Social events organized by company for their employees."));
    }
}
