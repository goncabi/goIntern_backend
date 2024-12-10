package com.example.application.config;

import com.github.javaparser.quality.Nullable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Alle Endpoints sind zugelassen
                        .allowedOrigins("http://localhost:8080") // Frontend-Adresse
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Erlaubte Methoden
                        .allowedHeaders("*"); // Erlaubte headers
            }
        };
    }
}
