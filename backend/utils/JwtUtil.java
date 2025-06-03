package com.example.sample_project.security; 
 
import com.example.sample_project.entity.Users; 
import com.example.sample_project.repository.UserRepository; 
import io.jsonwebtoken.Jwts; 
import io.jsonwebtoken.SignatureAlgorithm; 
import io.jsonwebtoken.security.Keys; 
import org.springframework.stereotype.Component; 
 
import javax.crypto.SecretKey; 
import java.util.Date; 
import java.util.Optional; 
 
/** 
* Utility class for handling JWT (JSON Web Token) operations, including token generation, 
* validation, and email extraction. 
*/ 
@Component 
public class JwtUtil { 
    //secret key 
    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); 
 
    // expiration time 
    private final int jwtExpirationMs = 86400000; 
 
    private UserRepository userRepository; 
 
    public JwtUtil(UserRepository userRepository) { 
        this.userRepository = userRepository; 
    } 
 
    /** 
     * Generates a JWT token for a given email. 
     * The token contains user details and expires after a set duration. 
     * 
     * @param email The email of the user for whom the token is generated. 
     * @return The generated JWT token as a string. 
     * @throws RuntimeException if the user is not found in the database. 
     */ 
    public String generateToken(String email) { 
        Optional<Users> userOptional = userRepository.findByEmail(email); 
        if (userOptional.isEmpty()) { 
            throw new RuntimeException("User not found for email: " + email); 
        } 
 
        Users user = userOptional.get(); 
 
        return Jwts.builder() 
                .setSubject(email) 
                .claim("firstName", user.getFirstName()) 
                .claim("lastName", user.getLastName()) 
                .setIssuedAt(new Date()) 
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) 
                .signWith(secretKey) 
                .compact(); 
    } 
 
    /** 
     * Extracts the email from a given JWT token. 
     * 
     * @param token The JWT token from which to extract the email. 
     * @return The email address stored in the token. 
     */ 
    public String extractEmail(String token) { 
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject(); 
    } 
 
    /** 
     * Validates whether a given JWT token is authentic and unaltered. 
     * 
     * @param token The JWT token to validate. 
     * @return True if the token is valid, false otherwise. 
     */ 
    public boolean isTokenValid(String token) { 
        try { 
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token); 
            return true; 
        } catch (Exception e) { 
            return false; 
        } 
    } 
 
    /** 
     * Retrieves the secret key used for signing tokens. 
     * 
     * @return The secret key. 
     */ 
    public static SecretKey getSecretKey() { 
        return secretKey; 
    } 
}