package com.example.sample_project.controller; 
 
import com.example.sample_project.entity.Users; 
import com.example.sample_project.service.UserService; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*; 
 
import java.util.Optional; 
 
@RestController 
@RequestMapping("/api/users") 
public class UserController { 
 
    @Autowired 
    private UserService userService; 
 
    /** 
     * Retrieves user details by user ID. 
     * If the user is found, returns a 200 OK response with user details. 
     * If the user is not found, returns a 404 Not Found response. 
     * 
     * @param userId The unique identifier of the user. 
     * @return The user's details or a 404 error if the user does not exist. 
     */ 
    @GetMapping("/{userId}") 
    public ResponseEntity<Users> getUserById(@PathVariable String userId) { 
        Optional<Users> user = userService.getUserById(userId); 
        return user.map(ResponseEntity::ok) 
                .orElseGet(() -> ResponseEntity.notFound().build()); 
    } 
 
    /** 
     * Updates an existing user's details based on their user ID. 
     * If the update is successful, returns the updated user details with a 200 OK response. 
     * If the user ID is not found, returns a 404 Not Found response. 
     * 
     * @param userId      The unique identifier of the user to be updated. 
     * @param updatedUser The updated user details received from the request body. 
     * @return The updated user object or a 404 error if the update fails. 
     */ 
    @PutMapping("/{userId}") 
    public ResponseEntity<Users> updateUser(@PathVariable String userId, @RequestBody Users updatedUser) { 
        try { 
            Users user = userService.updateUser(userId, updatedUser); 
            return ResponseEntity.ok(user); 
        } catch (RuntimeException e) { 
            return ResponseEntity.notFound().build(); 
        } 
    } 
 
    /** 
     * Deletes the user from the User database 
     * 
     * @param userId 
     */ 
    @DeleteMapping("/{userId}") 
    public void deleteUser(@PathVariable String userId) { 
        userService.deleteUser(userId); 
    } 
}