package com.example.sample_project.config; 
 
import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration; 
import org.springframework.web.servlet.config.annotation.CorsRegistry; 
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; 
 
@Configuration 
public class WebConfig implements WebMvcConfigurer { 
 
    @Bean 
    /** 
     * Configures CORS (Cross-Origin Resource Sharing) to allow requests from the frontend. 
     * This setup permits requests from "http://localhost:5173" with various HTTP methods and headers. 
     */ 
    public WebMvcConfigurer corsConfigurer() { 
        return new WebMvcConfigurer() { 
            @Override 
            public void addCorsMappings(CorsRegistry registry) { 
                registry.addMapping("/**") 
                        .allowedOrigins("http://localhost:5173") 
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") 
                        .allowedHeaders("*") 
                        .allowCredentials(true); 
            } 
        }; 
    } 
}