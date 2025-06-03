package com.example.sample_project.service; 
 
import com.example.sample_project.entity.Users; 
import com.example.sample_project.repository.UserRepository; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith; 
import org.mockito.InjectMocks; 
import org.mockito.Mock; 
import org.mockito.junit.jupiter.MockitoExtension; 
 
import java.util.Optional; 
 
import static org.junit.jupiter.api.Assertions.*; 
import static org.mockito.Mockito.*; 
 
@ExtendWith(MockitoExtension.class)  // Enables Mockito support for JUnit 5 
class UserServiceTest { 
 
    @Mock 
    private UserRepository userRepository;  // Mock repository for user-related operations 
 
    @InjectMocks 
    private UserService userService;  // Inject mocks into UserService 
 
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
 
    //  Test getUserById() - Successfully retrieves user by ID 
    @Test 
    void testGetUserById_UserExists() { 
        // Mock repository response returning user details for the given ID 
        when(userRepository.findByUserId("123")).thenReturn(Optional.of(user)); 
 
        // Call the service method 
        Optional<Users> retrievedUser = userService.getUserById("123"); 
 
        // Validate response 
        assertTrue(retrievedUser.isPresent()); 
        assertEquals("123", retrievedUser.get().getUserId()); 
 
        // Verify repository method call 
        verify(userRepository, times(1)).findByUserId("123"); 
    } 
 
    //  Test getUserById() - Handle case where user is not found 
    @Test 
    void testGetUserById_UserNotFound() { 
        // Mock repository response returning an empty Optional 
        when(userRepository.findByUserId("999")).thenReturn(Optional.empty()); 
 
        // Call the service method 
        Optional<Users> retrievedUser = userService.getUserById("999"); 
 
        // Validate response 
        assertFalse(retrievedUser.isPresent()); 
 
        // Verify repository method call 
        verify(userRepository, times(1)).findByUserId("999"); 
    } 
 
    //  Test updateUser() - Successfully updates user details 
    @Test 
    void testUpdateUser_Success() { 
        // Mock updated user data 
        Users updatedUser = new Users(); 
        updatedUser.setUserId("123"); 
        updatedUser.setEmail("updated@example.com"); 
        updatedUser.setFirstName("Jane"); 
        updatedUser.setLastName("Smith"); 
 
        // Mock repository response with updated user 
        when(userRepository.findByUserId("123")).thenReturn(Optional.of(user)); 
        when(userRepository.save(any(Users.class))).thenReturn(updatedUser); 
 
        // Call the service method 
        Users result = userService.updateUser("123", updatedUser); 
 
        // Validate response 
        assertNotNull(result); 
        assertEquals("Jane", result.getFirstName()); 
 
        // Verify repository method calls 
        verify(userRepository, times(1)).findByUserId("123"); 
        verify(userRepository, times(1)).save(any(Users.class)); 
    } 
 
    //  Test updateUser() - Handle case where user is not found 
    @Test 
    void testUpdateUser_UserNotFound() { 
        // Mock repository response returning empty Optional 
        when(userRepository.findByUserId("999")).thenReturn(Optional.empty()); 
 
        // Expect RuntimeException when trying to update a non-existing user 
        Exception exception = assertThrows(RuntimeException.class, () -> 
                userService.updateUser("999", user)); 
 
        // Validate exception message 
        assertEquals("User not found with id: 999", exception.getMessage()); 
 
        // Verify repository method call 
        verify(userRepository, times(1)).findByUserId("999"); 
        verify(userRepository, never()).save(any(Users.class));  // Ensure save was not called 
    } 
 
    //  Test deleteUser() - Successfully deletes a user 
    @Test 
    void testDeleteUser_Success() { 
        // Mock repository method call for deleting user 
        doNothing().when(userRepository).deleteById("123"); 
 
        // Call the service method 
        userService.deleteUser("123"); 
 
        // Verify repository delete method call 
        verify(userRepository, times(1)).deleteById("123"); 
    } 
} 
 
 
 
 
pom.xml 
<?xml version="1.0" encoding="UTF-8"?> 
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd"> 
    <modelVersion>4.0.0</modelVersion> 
    <parent> 
       <groupId>org.springframework.boot</groupId> 
       <artifactId>spring-boot-starter-parent</artifactId> 
       <version>3.4.4</version> 
       <relativePath/> <!-- lookup parent from repository --> 
    </parent> 
    <groupId>com.example</groupId> 
    <artifactId>sample_project</artifactId> 
    <version>0.0.1-SNAPSHOT</version> 
    <name>sample_project</name> 
    <description>Demo project for Spring Boot</description> 
    <url/> 
    <licenses> 
       <license/> 
    </licenses> 
    <developers> 
       <developer/> 
    </developers> 
    <scm> 
       <connection/> 
       <developerConnection/> 
       <tag/> 
       <url/> 
    </scm> 
    <properties> 
       <java.version>17</java.version> 
    </properties> 
    <dependencies> 
       <dependency> 
          <groupId>org.springframework.boot</groupId> 
          <artifactId>spring-boot-starter-data-jpa</artifactId> 
       </dependency> 
       <dependency> 
          <groupId>org.springframework.boot</groupId> 
          <artifactId>spring-boot-starter-web</artifactId> 
       </dependency> 
 
       <dependency> 
          <groupId>com.mysql</groupId> 
          <artifactId>mysql-connector-j</artifactId> 
          <scope>runtime</scope> 
       </dependency> 
       <dependency> 
          <groupId>org.springframework.boot</groupId> 
          <artifactId>spring-boot-starter-test</artifactId> 
          <scope>test</scope> 
       </dependency> 
       <dependency> 
          <groupId>org.springframework.boot</groupId> 
          <artifactId>spring-boot-starter-security</artifactId> 
       </dependency> 
       <dependency> 
          <groupId>io.jsonwebtoken</groupId> 
          <artifactId>jjwt-api</artifactId> 
          <version>0.11.5</version> 
       </dependency> 
       <dependency> 
          <groupId>io.jsonwebtoken</groupId> 
          <artifactId>jjwt-impl</artifactId> 
          <version>0.11.5</version> 
       </dependency> 
       <dependency> 
          <groupId>io.jsonwebtoken</groupId> 
          <artifactId>jjwt-jackson</artifactId> 
          <version>0.11.5</version> 
       </dependency> 
    </dependencies> 
 
    <build> 
       <plugins> 
          <plugin> 
             <groupId>org.springframework.boot</groupId> 
             <artifactId>spring-boot-maven-plugin</artifactId> 
          </plugin> 
       </plugins> 
    </build> 
 
</project>