package com.example.sample_project.security; 
 
import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration; 
import org.springframework.security.authentication.AuthenticationManager; 
import org.springframework.security.authentication.ProviderManager; 
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; 
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity; 
import org.springframework.security.core.userdetails.User; 
import com.example.sample_project.service.CustomUserDetailsService; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.security.web.SecurityFilterChain; 
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; 
import org.springframework.web.cors.CorsConfiguration; 
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; 
import org.springframework.web.filter.CorsFilter; 
 
import java.util.List; 
 
@Configuration 
public class SecurityConfig { 
 
    private final CustomUserDetailsService customUserDetailsService; 
    private final com.example.sample_project.security.JwtAuthenticationFilter jwtAuthenticationFilter; 
 
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) { 
        this.customUserDetailsService = customUserDetailsService; 
        this.jwtAuthenticationFilter = jwtAuthenticationFilter; 
    } 
 
    @Bean 
    public PasswordEncoder passwordEncoder() { 
        return new BCryptPasswordEncoder(); 
    } 
 
    @Bean 
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception { 
        return authConfig.getAuthenticationManager(); 
    } 
 
    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 
        http 
                .csrf(csrf -> csrf.disable()) // ✅ Fully disable CSRF (just for testing 
                .authorizeHttpRequests(auth -> auth 
                        .requestMatchers("/auth/**").permitAll() // Allow public access to login and signup endpoints 
                        .anyRequest().authenticated() // Restrict all other endpoints to authenticated users 
 
                ) 
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);// ✅ Allow all requests 
        return http.build(); 
    } 
 
    @Bean 
    public CorsFilter corsFilter() { 
        CorsConfiguration configuration = new CorsConfiguration(); 
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // Specify frontend origin 
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")); // Specify allowed methods 
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Specify allowed headers 
        configuration.setAllowCredentials(true); // Allow credentials (e.g., cookies) 
 
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); 
        source.registerCorsConfiguration("/**", configuration); // Apply CORS globally 
        return new CorsFilter(source); 
    } 
 
}