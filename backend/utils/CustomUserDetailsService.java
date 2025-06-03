package com.example.sample_project.service; 
 
import com.example.sample_project.entity.Users; 
import com.example.sample_project.repository.UserRepository; 
import org.springframework.security.core.userdetails.User; 
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.core.userdetails.UsernameNotFoundException; 
import org.springframework.stereotype.Service; 
 
import java.util.Collections; 
import java.util.Optional; 
/** 
* Custom implementation of Spring Security's UserDetailsService. 
* Used for loading user details during authentication. 
*/ 
@Service 
public class CustomUserDetailsService implements UserDetailsService { 
 
    private final UserRepository userRepository; 
    /** 
     * Constructor-based dependency injection for UserRepository. 
     * 
     * @param userRepository Repository for fetching user details. 
     */ 
    public CustomUserDetailsService(UserRepository userRepository) { 
        this.userRepository = userRepository; 
    } 
    /** 
     * Loads user details based on email for authentication. 
     * Throws an exception if the user is not found. 
     * 
     * @param email The email of the user attempting to authenticate. 
     * @return UserDetails object containing user authentication information. 
     * @throws UsernameNotFoundException if the user does not exist. 
     */ 
    @Override 
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { 
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found: " + email)); 
        return new User(user.getEmail(), user.getPasswordHash(), Collections.emptyList()); 
    } 
}