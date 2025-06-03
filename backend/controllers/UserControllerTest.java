package com.example.sample_project.controller; 
 
import com.example.sample_project.entity.Users; 
import com.example.sample_project.service.UserService; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith; 
import org.mockito.InjectMocks; 
import org.mockito.Mock; 
import org.mockito.junit.jupiter.MockitoExtension; 
import org.springframework.http.ResponseEntity; 
 
import java.util.Optional; 
 
import static org.junit.jupiter.api.Assertions.*; 
import static org.mockito.Mockito.*; 
 
@ExtendWith(MockitoExtension.class)  // Enables Mockito support for JUnit 5 
class UserControllerTest { 
 
    @Mock 
    private UserService userService;  // Mock service layer for user-related operations 
 
    @InjectMocks 
    private UserController userController;  // Inject mocks into UserController 
 
    private Users user;  // Sample user instance for testing 
 
    @BeforeEach 
    void setUp() { 
        // Initialize test user data before each test case 
        user = new Users(); 
        user.setUserId("123"); 
        user.setEmail("test@example.com"); 
        user.setFirstName("John"); 
        user.setLastName("Doe"); 
    } 
 
    //  Test @GetMapping("/{userId}") - Retrieve user successfully 
    @Test 
    void testGetUserById_UserExists() { 
        // Mock repository response returning user details for the given ID 
        when(userService.getUserById("123")).thenReturn(Optional.of(user)); 
 
        // Call the controller method 
        ResponseEntity<Users> response = userController.getUserById("123"); 
 
        // Validate response 
        assertEquals(200, response.getStatusCodeValue()); 
        assertNotNull(response.getBody()); 
        assertEquals("123", response.getBody().getUserId()); 
    } 
 
    //  Test @GetMapping("/{userId}") - Handle user not found scenario 
    @Test 
    void testGetUserById_UserNotFound() { 
        // Mock repository response returning empty Optional for missing user ID 
        when(userService.getUserById("999")).thenReturn(Optional.empty()); 
 
        // Call the controller method 
        ResponseEntity<Users> response = userController.getUserById("999"); 
 
        // Validate response 
        assertEquals(404, response.getStatusCodeValue()); 
        assertNull(response.getBody()); 
    } 
 
    //  Test @PutMapping("/{userId}") - Update user details successfully 
    @Test 
    void testUpdateUser_Success() { 
        // Mock updated user data 
        Users updatedUser = new Users(); 
        updatedUser.setUserId("123"); 
        updatedUser.setEmail("updated@example.com"); 
        updatedUser.setFirstName("Jane"); 
        updatedUser.setLastName("Smith"); 
 
        // Mock repository response with updated user 
        when(userService.updateUser("123", updatedUser)).thenReturn(updatedUser); 
 
        // Call the controller method 
        ResponseEntity<Users> response = userController.updateUser("123", updatedUser); 
 
        // Validate response 
        assertEquals(200, response.getStatusCodeValue()); 
        assertNotNull(response.getBody()); 
        assertEquals("Jane", response.getBody().getFirstName()); 
    } 
 
    //  Test @PutMapping("/{userId}") - Handle user update failure (user not found) 
    @Test 
    void testUpdateUser_NotFound() { 
        // Mock repository throwing an exception when updating a non-existing user 
        Users updatedUser = new Users(); 
        when(userService.updateUser("999", updatedUser)).thenThrow(new RuntimeException()); 
 
        // Call the controller method 
        ResponseEntity<Users> response = userController.updateUser("999", updatedUser); 
 
        // Validate response 
        assertEquals(404, response.getStatusCodeValue()); 
    } 
 
    //  Test @DeleteMapping("/{userId}") - Delete user successfully 
    @Test 
    void testDeleteUser_Success() { 
        // Mock service response where delete operation executes without issue 
        doNothing().when(userService).deleteUser("123"); 
 
        // Call the controller method 
        userController.deleteUser("123"); 
 
        // Verify delete operation was called once 
        verify(userService, times(1)).deleteUser("123"); 
    } 
}