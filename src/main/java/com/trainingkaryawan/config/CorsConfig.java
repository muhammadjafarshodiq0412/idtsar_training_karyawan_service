package com.trainingkaryawan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.List;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:8089"));
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*"); // Allow all origins
        config.addAllowedHeader("*"); // Allow all headers
        config.addAllowedMethod("*"); // Allow all HTTP methods
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
