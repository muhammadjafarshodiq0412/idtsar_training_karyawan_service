package com.trainingkaryawan.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This Configuration for enable static resource on spring mvc
 * Reason : because we want access directory.file.upload using this /show-file/
 * condition : we want preview file just hit api [your-ip]:8088/v1/idstar/show-file/[file-name]
 * example : http://localhost:8088/v1/idstar/show-file/Soal-ke-1.pdf
 */
@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    @Value("${directory.file.upload}")
    private String directory;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/show-file/**")
                .addResourceLocations("file:".concat(directory));
    }
}
