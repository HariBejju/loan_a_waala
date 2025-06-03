package com.example.sample_project.dto; 
 
public class RegisterRequest { 
    private String token; 
    private String userId; 
    private String firstName; 
    private String lastName; 
    private String email; 
    private String phoneNumber; 
    private String alternatePhoneNumber; 
    private String alternateEmail; 
    private String dateOfBirth; 
    private String panCard; 
    private String aadharCard; 
    private String residentialAddress; 
    private String permanentAddress; 
    private String city; 
    private String state; 
    private String pincode; 
    private String passwordHash; 
 
    // Default Constructor 
    public RegisterRequest() { 
    } 
 
    // Parameterized Constructor 
    public RegisterRequest(String userId, String firstName, String lastName, String email, 
                           String phoneNumber, String alternatePhoneNumber, String alternateEmail, String dateOfBirth, 
                           String panCard, String aadharCard, String residentialAddress, String permanentAddress, 
                           String city, String state, String pincode, String passwordHash, String token) { 
        this.userId = userId; 
        this.firstName = firstName; 
        this.lastName = lastName; 
        this.email = email; 
        this.phoneNumber = phoneNumber; 
        this.alternatePhoneNumber = alternatePhoneNumber; 
        this.alternateEmail = alternateEmail; 
        this.dateOfBirth = dateOfBirth; 
        this.panCard = panCard; 
        this.aadharCard = aadharCard; 
        this.residentialAddress = residentialAddress; 
        this.permanentAddress = permanentAddress; 
        this.city = city; 
        this.state = state; 
        this.pincode = pincode; 
        this.passwordHash = passwordHash; 
        this.token = token; 
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
 
    public String getToken() { 
        return token; 
    } 
 
    public void setToken(String token) { 
        this.token = token; 
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
 
    public String getPasswordHash() { 
        return passwordHash; 
    } 
 
    public void setPasswordHash(String passwordHash) { 
        this.passwordHash = passwordHash; 
    } 
}