package com.example.sample_project.entity; 
 
import jakarta.persistence.*; 
 
@Entity 
@Table(name = "USERS") 
public class Users { 
 
    @Id 
    @Column(name = "user_id", length = 20) 
    private String userId; 
 
    @Column(name = "user_first_name", length = 20, nullable = false) 
    private String firstName; 
 
    @Column(name = "user_last_name", length = 20, nullable = false) 
    private String lastName; 
 
    @Column(name = "user_email", length = 50, nullable = false, unique = true) 
    private String email; 
 
    @Column(name = "user_phone_number", length = 10, nullable = false) 
    private String phoneNumber; 
 
    @Column(name = "user_alt_phone", length = 10) 
    private String alternatePhoneNumber; 
 
    @Column(name = "user_alt_email", length = 50) 
    private String alternateEmail; 
 
    @Column(name = "user_dob", nullable = false) 
    private String dateOfBirth; 
 
    @Column(name = "user_pan_card", length = 10, unique = true, nullable = false) 
    private String panCard; 
 
    @Column(name = "user_aadhar_card", length = 12, unique = true, nullable = false) 
    private String aadharCard; 
 
    @Column(name = "user_city", length = 20, nullable = false) 
    private String city; 
 
    @Column(name = "user_state", length = 20, nullable = false) 
    private String state; 
 
    @Column(name = "user_pincode", length = 6, nullable = false) 
    private String pincode; 
 
    @Column(name = "user_residential_address", length = 255, nullable = false) 
    private String residentialAddress; 
 
    @Column(name = "user_permanent_address", length = 255, nullable = false) 
    private String permanentAddress; 
 
 
    @Column(name = "user_password_hash", length = 256, nullable = false) 
    private String passwordHash; 
 
    // Default Constructor (Required by JPA) 
    public Users() { 
    } 
 
    // Getters and Setters 
    public String getUserId() { 
        return userId; 
    } 
 
    public void setUserId(String userId) { 
        this.userId = userId; 
    } 
 
    public String getFirstName() { 
        return firstName; 
    } 
 
    public void setFirstName(String firstName) { 
        this.firstName = firstName; 
    } 
 
    public String getLastName() { 
        return lastName; 
    } 
 
    public void setLastName(String lastName) { 
        this.lastName = lastName; 
    } 
 
    public String getEmail() { 
        return email; 
    } 
 
    public void setEmail(String email) { 
        this.email = email; 
    } 
 
    public String getPhoneNumber() { 
        return phoneNumber; 
    } 
 
    public void setPhoneNumber(String phoneNumber) { 
        this.phoneNumber = phoneNumber; 
    } 
 
    public String getAlternatePhoneNumber() { 
        return alternatePhoneNumber; 
    } 
 
    public void setAlternatePhoneNumber(String alternatePhoneNumber) { 
        this.alternatePhoneNumber = alternatePhoneNumber; 
    } 
 
    public String getAlternateEmail() { 
        return alternateEmail; 
    } 
 
    public void setAlternateEmail(String alternateEmail) { 
        this.alternateEmail = alternateEmail; 
    } 
 
    public String getDateOfBirth() { 
        return dateOfBirth; 
    } 
 
    public void setDateOfBirth(String dateOfBirth) { 
        this.dateOfBirth = dateOfBirth; 
    } 
 
    public String getPanCard() { 
        return panCard; 
    } 
 
    public void setPanCard(String panCard) { 
        this.panCard = panCard; 
    } 
 
    public String getAadharCard() { 
        return aadharCard; 
    } 
 
    public void setAadharCard(String aadharCard) { 
        this.aadharCard = aadharCard; 
    } 
 
    public String getCity() { 
        return city; 
    } 
 
    public void setCity(String city) { 
        this.city = city; 
    } 
 
    public String getState() { 
        return state; 
    } 
 
    public void setState(String state) { 
        this.state = state; 
    } 
 
    public String getPincode() { 
        return pincode; 
    } 
 
    public void setPincode(String pincode) { 
        this.pincode = pincode; 
    } 
 
    public String getResidentialAddress() { 
        return residentialAddress; 
    } 
 
    public void setResidentialAddress(String residentialAddress) { 
        this.residentialAddress = residentialAddress; 
    } 
 
    public String getPermanentAddress() { 
        return permanentAddress; 
    } 
 
    public void setPermanentAddress(String permanentAddress) { 
        this.permanentAddress = permanentAddress; 
    } 
 
 
    public String getPasswordHash() { 
        return passwordHash; 
    } 
 
    public void setPasswordHash(String passwordHash) { 
        this.passwordHash = passwordHash; 
    } 
}