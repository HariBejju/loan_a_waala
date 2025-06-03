package com.example.sample_project.repository; 
 
import com.example.sample_project.entity.LoanUserCollection; 
import org.springframework.data.jpa.repository.JpaRepository; 
 
import java.util.List; 
 
/** 
* Repository interface for managing loan user collection records in the database. 
* Extends JpaRepository to provide CRUD operations on LoanUserCollection entity. 
*/ 
public interface LoanUserCollectionRepository extends JpaRepository<LoanUserCollection, String> { 
 
    /** 
     * Retrieves all loan records associated with a specific user's PAN card. 
     * Allows filtering loans based on the user's PAN card for identity verification and tracking. 
     * 
     * @param userPanCard The PAN card number used to identify the user. 
     * @return A list of LoanUserCollection records linked to the provided PAN card. 
     */ 
    List<LoanUserCollection> findAllByUserPanCard(String userPanCard); 
}