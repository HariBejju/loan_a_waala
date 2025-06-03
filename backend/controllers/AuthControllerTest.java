package com.example.sample_project.controller; 
 
import com.example.sample_project.action.ActionRecorder; 
import com.example.sample_project.dto.RegisterRequest; 
import com.example.sample_project.entity.Actions; 
import com.example.sample_project.entity.Users; 
import com.example.sample_project.repository.UserRepository; 
import com.example.sample_project.security.JwtUtil; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith; 
import org.mockito.InjectMocks; 
import org.mockito.Mock; 
import org.mockito.MockitoAnnotations; 
import org.mockito.junit.jupiter.MockitoExtension; 
import org.springframework.http.ResponseEntity; 
import org.springframework.security.authentication.AuthenticationManager; 
import org.springframework.security.crypto.password.PasswordEncoder; 
 
import java.util.Optional; 
 
import static org.junit.jupiter.api.Assertions.*; 
import static org.mockito.Mockito.*; 
 
@ExtendWith(MockitoExtension.class)  // Enables Mockito support for JUnit 5 
class AuthControllerTest { 
 
    @Mock 
    private AuthenticationManager authenticationManager;  // Mock authentication manager 
 
    @Mock 
    private UserRepository userRepository;  // Mock repository for user data 
 
    @Mock 
    private JwtUtil jwtUtil;  // Mock JWT utility for generating tokens 
 
    @Mock 
    private PasswordEncoder passwordEncoder;  // Mock password encoder for hashing passwords 
 
    @InjectMocks 
    private AuthController authController;  // Inject mocks into AuthController 
 
    private RegisterRequest registerRequest;  // Test register request data 
    private Users user;  // Test user data 
 
    @Mock 
    private ActionRecorder actionRecorder;  // Mock the dependency 
 
 
    @BeforeEach 
    void setUp() { 
        // Initialize test objects before each test case 
        registerRequest = new RegisterRequest(); 
        registerRequest.setUserId("user123"); 
        registerRequest.setFirstName("John"); 
        registerRequest.setLastName("Doe"); 
        registerRequest.setEmail("john@example.com"); 
        registerRequest.setPasswordHash("password123"); 
 
        user = new Users(); 
        user.setUserId("user123"); 
        user.setEmail("john@example.com"); 
        user.setPasswordHash("encodedPassword");  // Simulated hashed password 
    } 
 
    @Test 
    void testRegisterUser_Success() { 
        // Simulate user not existing in the database 
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty()); 
        when(userRepository.findByUserId(registerRequest.getUserId())).thenReturn(Optional.empty()); 
        when(passwordEncoder.encode(registerRequest.getPasswordHash())).thenReturn("hashedPassword"); 
 
        // Execute register method 
        ResponseEntity<String> response = authController.register(registerRequest); 
 
        // Print the response details in console 
        System.out.println("Response Status: " + response.getStatusCodeValue()); 
        System.out.println("Response Body: " + response.getBody()); 
 
        // Verify response 
        assertEquals(200, response.getStatusCodeValue()); 
        assertEquals("User Registered Successfully!", response.getBody()); 
 
        // Ensure user repository save method is called once 
        verify(userRepository, times(1)).save(any(Users.class)); 
    } 
 
    @Test 
    void testRegisterUser_UserAlreadyExists() { 
        // Simulate a case where a user with the email already exists 
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.of(user)); 
 
        // Execute register method 
        ResponseEntity<String> response = authController.register(registerRequest); 
 
        // Print output to console 
        System.out.println("Test: User Already Exists"); 
        System.out.println("Response Status: " + response.getStatusCodeValue()); 
        System.out.println("Response Body: " + response.getBody()); 
 
        // Verify response 
        assertEquals(400, response.getStatusCodeValue()); 
        assertEquals("User Already Exists", response.getBody()); 
    } 
 
    @Test 
    void testLoginUser_Success() { 
        // Mock repository response with a valid user 
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user)); 
        when(jwtUtil.generateToken(user.getEmail())).thenReturn("mockedToken"); 
 
        // Prevent NullPointerException by mocking action recording 
 
        doNothing().when(actionRecorder).recordAction(anyString(), any(), any(Actions.ActionEventType.class)); 
 
        // Execute login method 
        ResponseEntity<?> response = authController.login(user); 
 
        // Verify response 
        assertEquals(200, response.getStatusCodeValue()); 
    } 
 
 
    @Test 
    void testLoginUser_InvalidCredentials() { 
        // Simulate invalid credentials where the user is not found 
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty()); 
 
        // Execute login method 
        ResponseEntity<?> response = authController.login(user); 
 
        // Print output to console 
        System.out.println("Test: Login User Invalid Credentials"); 
        System.out.println("Response Status: " + response.getStatusCodeValue()); 
        System.out.println("Response Body: " + response.getBody()); 
 
        // Verify response 
        assertEquals(401, response.getStatusCodeValue()); 
        assertEquals("User Not Found", response.getBody()); 
    } 
}