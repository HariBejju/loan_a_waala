package com.example.sample_project.security; 
 
import com.example.sample_project.service.CustomUserDetailsService; 
import jakarta.servlet.FilterChain; 
import jakarta.servlet.ServletException; 
import jakarta.servlet.http.HttpServletRequest; 
import jakarta.servlet.http.HttpServletResponse; 
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; 
import org.springframework.security.core.context.SecurityContextHolder; 
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource; 
import org.springframework.stereotype.Component; 
import org.springframework.web.filter.OncePerRequestFilter; 
 
import java.io.IOException; 
 
/** 
* JWT Authentication Filter for processing authentication requests. 
* Ensures that each request is authenticated using a JWT token. 
*/ 
@Component 
public class JwtAuthenticationFilter extends OncePerRequestFilter { 
    private final JwtUtil jwtUtil; 
    private final CustomUserDetailsService customUserDetailsService; 
 
    /** 
     * Constructor for initializing JWT authentication utilities. 
     * 
     * @param jwtUtil                  Utility class for handling JWT operations. 
     * @param customUserDetailsService Service to load user details from the database. 
     */ 
    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) { 
        this.jwtUtil = jwtUtil; 
        this.customUserDetailsService = customUserDetailsService; 
    } 
 
    /** 
     * Intercepts and processes authentication requests. 
     * Extracts and validates JWT token from incoming requests. 
     * 
     * @param request     The HTTP request containing headers and authentication info. 
     * @param response    The HTTP response to be modified. 
     * @param filterChain The filter chain to continue processing the request. 
     * @throws ServletException If a servlet error occurs. 
     * @throws IOException      If an I/O error occurs. 
     */ 
    @Override 
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException { 
        String token = request.getHeader("Authorization"); 
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) { 
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173"); // Allow frontend origin 
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS,PATCH"); // Allow methods 
            response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type"); // Allow headers 
            response.setHeader("Access-Control-Allow-Credentials", "true"); // Allow credentials 
 
            response.setStatus(HttpServletResponse.SC_OK); // Return 200 OK for preflight requests 
            return; 
        } else { 
            System.out.println("do Internal Filter is not working"); 
        } 
        if (token != null && token.startsWith("Bearer")) { 
            token = token.substring(7); 
            String userEmail = jwtUtil.extractEmail(token); 
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) { 
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail); 
                if (jwtUtil.isTokenValid(token)) { 
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); 
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); 
                    SecurityContextHolder.getContext().setAuthentication(authToken); 
                } 
            } 
 
        } 
        filterChain.doFilter(request, response); 
    } 
}