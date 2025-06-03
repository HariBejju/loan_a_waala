package com.example.sample_project.service; 
 
import com.example.sample_project.entity.Users; 
import com.example.sample_project.repository.UserRepository; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith; 
import org.mockito.InjectMocks; 
import org.mockito.Mock; 
import org.mockito.junit.jupiter.MockitoExtension; 
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.core.userdetails.UsernameNotFoundException; 
 
import java.util.Optional; 
 
import static org.junit.jupiter.api.Assertions.*; 
import static org.mockito.Mockito.*; 
 
@ExtendWith(MockitoExtension.class)  // Enables Mockito support for JUnit 5 
class CustomUserDetailsServiceTest { 
 
    @Mock 
    private UserRepository userRepository;  // Mock repository for retrieving user details 
 
    @InjectMocks 
    private CustomUserDetailsService customUserDetailsService;  // Inject mocks into CustomUserDetailsService 
 
    private Users user;  // Sample user instance for testing 
 
    @BeforeEach 
    void setUp() { 
        // Initialize test user data before each test case 
        user = new Users(); 
        user.setUserId("123"); 
        user.setEmail("test@example.com"); 
        user.setPasswordHash("hashedPassword123"); 
    } 
 
    //  Test loadUserByUsername() - Successfully retrieves user details 
    @Test 
    void testLoadUserByUsername_UserExists() { 
        // Mock repository response returning the user for a valid email 
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user)); 
 
        // Call the service method 
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("test@example.com"); 
 
        // Validate response 
        assertNotNull(userDetails); 
        assertEquals("test@example.com", userDetails.getUsername()); 
        assertEquals("hashedPassword123", userDetails.getPassword()); 
 
        // Verify repository method call 
        verify(userRepository, times(1)).findByEmail("test@example.com"); 
    } 
 
    //  Test loadUserByUsername() - Handle user not found scenario 
    @Test 
    void testLoadUserByUsername_UserNotFound() { 
        // Mock repository response returning empty Optional for missing user email 
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty()); 
 
        // Expect UsernameNotFoundException when trying to load an unknown user 
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> 
                customUserDetailsService.loadUserByUsername("unknown@example.com")); 
 
        // Validate exception message 
        assertEquals("User not found: unknown@example.com", exception.getMessage()); 
 
        // Verify repository method call 
        verify(userRepository, times(1)).findByEmail("unknown@example.com"); 
    } 
}