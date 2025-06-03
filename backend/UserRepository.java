package com.example.sample_project.repository; 
 
import org.springframework.data.jpa.repository.JpaRepository; 
import com.example.sample_project.entity.Users; 
 
import java.util.Optional; 
 
/** 
* Repository interface for managing user records in the database. 
* Extends JpaRepository to provide built-in CRUD operations for the Users entity. 
*/ 
public interface UserRepository extends JpaRepository<Users, String> { 
 
    /** 
     * Retrieves a user based on their email address. 
     * Useful for authentication and account lookup operations. 
     * 
     * @param email The email of the user. 
     * @return An Optional containing user details if found, otherwise empty. 
     */ 
    Optional<Users> findByEmail(String email); 
 
    /** 
     * Retrieves a user based on their unique user ID. 
     * Helps in fetching user information for account management and updates. 
     * 
     * @param userId The unique identifier of the user. 
     * @return An Optional containing user details if found, otherwise empty. 
     */ 
    Optional<Users> findByUserId(String userId); 
}