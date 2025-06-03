package com.example.sample_project.controller; 
 
import com.example.sample_project.action.ActionRecorder; 
import com.example.sample_project.dto.RegisterRequest; 
import com.example.sample_project.entity.Actions; 
import com.example.sample_project.entity.Users; 
import com.example.sample_project.repository.UserRepository; 
import com.example.sample_project.security.JwtUtil; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity; 
import org.springframework.security.authentication.AuthenticationManager; 
import org.springframework.security.authentication.BadCredentialsException; 
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; 
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.web.bind.annotation.*; 
 
import java.util.Optional; 
 
@CrossOrigin(origins = "http://localhost:5173") 
@RestController 
@RequestMapping("/auth") 
public class AuthController { 
 
    private final AuthenticationManager authenticationManager; 
    private final UserRepository userRepository; 
    private final JwtUtil jwtUtil; 
    private final PasswordEncoder passwordEncoder; 
    @Autowired 
    private ActionRecorder actionRecorder; 
 
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) { 
        this.authenticationManager = authenticationManager; 
        this.jwtUtil = jwtUtil; 
        this.userRepository = userRepository; 
        this.passwordEncoder = passwordEncoder; 
    } 
 
    /** 
     * Registers a new user by checking if the email or username already exists. 
     * If unique, saves the user with encrypted password. 
     **/ 
    @PostMapping("/register") 
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) { 
 
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) { 
            return ResponseEntity.badRequest().body("User Already Exists"); 
        } 
        if (userRepository.findByUserId(registerRequest.getUserId()).isPresent()) { 
            return ResponseEntity.badRequest().body("Username Already Exists"); 
        } 
 
        // Creating new user with all fields 
        Users newUser = new Users(); 
        newUser.setUserId(registerRequest.getUserId()); 
        newUser.setFirstName(registerRequest.getFirstName()); 
        newUser.setLastName(registerRequest.getLastName()); 
        newUser.setEmail(registerRequest.getEmail()); 
        newUser.setPhoneNumber(registerRequest.getPhoneNumber()); 
        newUser.setAlternatePhoneNumber(registerRequest.getAlternatePhoneNumber()); 
        newUser.setAlternateEmail(registerRequest.getAlternateEmail()); 
        newUser.setDateOfBirth(registerRequest.getDateOfBirth()); 
        newUser.setPanCard(registerRequest.getPanCard()); 
        newUser.setAadharCard(registerRequest.getAadharCard()); 
        newUser.setResidentialAddress(registerRequest.getResidentialAddress()); 
        newUser.setPermanentAddress(registerRequest.getPermanentAddress()); 
        newUser.setCity(registerRequest.getCity()); 
        newUser.setState(registerRequest.getState()); 
        newUser.setPincode(registerRequest.getPincode()); 
 
        // Encrypt password before saving 
        newUser.setPasswordHash(passwordEncoder.encode(registerRequest.getPasswordHash())); 
 
        userRepository.save(newUser); 
        return ResponseEntity.ok("User Registered Successfully!"); 
    } 
 
    /** 
     * Authenticates a user login request using email and password. 
     * Generates a JWT token upon successful login. 
     */ 
    @PostMapping("/login") 
    public ResponseEntity<?> login(@RequestBody Users loginRequest) { 
        try { 
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPasswordHash())); 
        } catch (BadCredentialsException e) { 
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials"); 
        } catch (Exception e) { 
            System.out.println("Exception during login: " + e); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login Failed"); 
        } 
 
        Optional<Users> user = userRepository.findByEmail(loginRequest.getEmail()); 
        if (user.isPresent()) { 
            String token = jwtUtil.generateToken(loginRequest.getEmail()); 
 
            // Return complete user details 
            RegisterRequest registerResponse = new RegisterRequest( 
                    user.get().getUserId(), 
                    user.get().getFirstName(), 
                    user.get().getLastName(), 
                    user.get().getEmail(), 
                    user.get().getPhoneNumber(), 
                    user.get().getAlternatePhoneNumber(), 
                    user.get().getAlternateEmail(), 
                    user.get().getDateOfBirth(), 
                    user.get().getPanCard(), 
                    user.get().getAadharCard(), 
                    user.get().getResidentialAddress(), 
                    user.get().getPermanentAddress(), 
                    user.get().getCity(), 
                    user.get().getState(), 
                    user.get().getPincode(), 
                    user.get().getPasswordHash(), 
                    token 
            ); 
            actionRecorder.recordAction(user.get().getUserId(), null, Actions.ActionEventType.LOGIN); 
            return ResponseEntity.ok(registerResponse); 
        } else { 
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User Not Found"); 
        } 
    } 
}