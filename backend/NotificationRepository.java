package com.example.sample_project.repository; 
 
import com.example.sample_project.entity.Notifications; 
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 
 
import java.util.List; 
 
/** 
* Repository interface for managing notification records in the database. 
* Extends JpaRepository to provide CRUD operations on the Notifications entity. 
*/ 
@Repository  // Marks this interface as a Spring-managed repository 
public interface NotificationRepository extends JpaRepository<Notifications, String> { 
 
    /** 
     * Retrieves all notifications for a specific user, ordered by timestamp in descending order. 
     * Ensures the latest notifications appear first. 
     * 
     * @param userId The ID of the user whose notifications are being retrieved. 
     * @return A list of notifications sorted by timestamp. 
     */ 
    List<Notifications> findByNotificationUserIdOrderByNotificationTimestampDesc(String userId); 
 
    /** 
     * Deletes a notification based on its unique identifier. 
     * Helps remove outdated or read notifications from the database. 
     * 
     * @param notificationId The unique identifier of the notification to delete. 
     */ 
    void deleteById(String notificationId); 
 
    /** 
     * Counts the total number of notifications associated with a specific user. 
     * Useful for displaying the number of unread or pending notifications. 
     * 
     * @param userId The ID of the user whose notifications are being counted. 
     * @return The total count of notifications linked to the user. 
     */ 
    long countByNotificationUserId(String userId); 
}