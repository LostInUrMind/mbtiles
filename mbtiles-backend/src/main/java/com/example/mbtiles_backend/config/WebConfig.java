package com.example.mbtiles_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Cho phép từ cả localhost và 127.0.0.1
        registry.addMapping("/tiles/**")
                .allowedOrigins("http://localhost:5500", "http://127.0.0.1:5500")
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Các phương thức bạn sử dụng
                .allowedHeaders("*")  // Cho phép tất cả các headers
                .allowCredentials(true)  // Nếu bạn cần gửi cookies hoặc auth headers
                .maxAge(3600);  // Thời gian cache cho phép CORS request
    }
}