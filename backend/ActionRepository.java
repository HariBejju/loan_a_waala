package com.example.sample_project.repository; 
 
import com.example.sample_project.entity.Actions; 
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 
 
import java.util.List; 
 
/** 
* Repository interface for managing action records in the database. 
* Extends JpaRepository to provide CRUD operations on the Actions entity. 
*/ 
@Repository  // Marks this interface as a Spring-managed repository 
public interface ActionRepository extends JpaRepository<Actions, String> { 
 
    /** 
     * Retrieves all actions performed by a specific user, ordered by timestamp in descending order. 
     * Ensures that the latest actions appear first. 
     * 
     * @param actionUserId The ID of the user whose actions are being retrieved. 
     * @return A list of actions sorted by timestamp. 
     * 
     * Note: If 'OrderBy' is not used, the records may appear in a random order. 
     */ 
    List<Actions> findAllByActionUserIdOrderByActionTimestampDesc(String actionUserId); 
}