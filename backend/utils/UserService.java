package com.example.sample_project.service; 
 
import com.example.sample_project.entity.Users; 
import com.example.sample_project.repository.UserRepository; 
import jakarta.transaction.Transactional; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
 
import java.util.Optional; 
/** 
* Service class for managing user-related operations. 
* Provides functionality to retrieve and update user details. 
*/ 
@Service 
public class UserService { 
 
    @Autowired 
    private UserRepository userRepository; 
 
    /** 
     * Retrieves user details based on the provided user ID. 
     * 
     * @param userId The unique identifier of the user. 
     * @return An Optional containing user details if found, otherwise empty. 
     */ 
    public Optional<Users> getUserById(String userId) { 
        return userRepository.findByUserId(userId); 
    } 
    /** 
     * Updates user information based on the given user ID. 
     * Ensures transactional safety when modifying user data. 
     * 
     * @param userId The unique identifier of the user. 
     * @param updatedUser The updated user details received from the request body. 
     * @return The updated user entity after modifications. 
     * @throws RuntimeException if the user ID does not exist in the database. 
     */ 
    @Transactional 
    public Users updateUser(String userId, Users updatedUser) { 
        return userRepository.findByUserId(userId) 
                .map(user -> { 
                    // Update basic details 
                    user.setFirstName(updatedUser.getFirstName()); 
                    user.setLastName(updatedUser.getLastName()); 
                    user.setEmail(updatedUser.getEmail()); 
                    user.setPhoneNumber(updatedUser.getPhoneNumber()); 
                    user.setAlternatePhoneNumber(updatedUser.getAlternatePhoneNumber()); 
                    user.setAlternateEmail(updatedUser.getAlternateEmail()); 
                    user.setDateOfBirth(updatedUser.getDateOfBirth()); 
 
                    // Update identity details 
                    user.setPanCard(updatedUser.getPanCard()); 
                    user.setAadharCard(updatedUser.getAadharCard()); 
 
                    // Update address details 
                    user.setCity(updatedUser.getCity()); 
                    user.setState(updatedUser.getState()); 
                    user.setPincode(updatedUser.getPincode()); 
                    user.setResidentialAddress(updatedUser.getResidentialAddress()); 
                    user.setPermanentAddress(updatedUser.getPermanentAddress()); 
 
                    // Update profile picture 
//                    user.setProfilePicUrl(updatedUser.getProfilePicUrl()); 
 
                    // Update password hash (if necessary) 
                    if (updatedUser.getPasswordHash() != null && !updatedUser.getPasswordHash().isEmpty()) { 
                        user.setPasswordHash(updatedUser.getPasswordHash()); 
                    } 
 
                    return userRepository.save(user); 
                }) 
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId)); 
    } 
 
    public void deleteUser(String userId) { 
        userRepository.deleteById(userId); 
    } 
 
 
 
} 
application.properties 
spring.application.name=sample_project 
spring.datasource.url=jdbc:mysql://localhost:3306/LOAN_A_WALA 
spring.datasource.username=root 
spring.datasource.password=root@39 
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver 
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect 
spring.jpa.hibernate.ddl-auto=update  
hibernate.hbm2ddl.auto=update  
spring.jpa.properties.hibernate.format_sql=true 
spring.jpa.show-sql=true  
server.port=8090