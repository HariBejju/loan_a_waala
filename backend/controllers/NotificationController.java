package com.example.sample_project.controller; 
 
import com.example.sample_project.entity.Notifications; 
import com.example.sample_project.repository.NotificationRepository; 
import com.example.sample_project.scheduler.NotificationScheduler; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*; 
 
import java.util.List; 
 
@CrossOrigin(origins = "http://localhost:5173") 
@RestController 
@RequestMapping("/api/notifications") 
public class NotificationController { 
 
    @Autowired 
    private NotificationRepository notificationRepository; 
 
    /** 
     * Retrieves notifications for a specific user, sorted in descending order of timestamp. 
     * Returns an empty list if no notifications are found. 
     * 
     * @param userId The ID of the user whose notifications are requested. 
     * @return A list of notifications for the specified user. 
     */ 
    @GetMapping("/{userId}") 
    public ResponseEntity<List<Notifications>> getUserNotifications(@PathVariable String userId) { 
        List<Notifications> notifications = notificationRepository.findByNotificationUserIdOrderByNotificationTimestampDesc(userId); 
        return ResponseEntity.ok(notifications); 
    } 
 
    /** 
     * Retrieves the count of notifications for a specific user. 
     * 
     * @param userId The ID of the user whose notification count is requested. 
     * @return The number of notifications for the specified user. 
     */ 
 
    @GetMapping("/count/{userId}") 
    public long getCountByUser(@PathVariable String userId) { 
        return notificationRepository.countByNotificationUserId(userId); 
    } 
 
    /** 
     * Deletes a specific notification from the database based on its ID. 
     * Handles exceptions in case the deletion fails. 
     * 
     * @param notificationId The ID of the notification to be deleted. 
     * @return A success or failure message based on the operation outcome. 
     */ 
    @DeleteMapping("/{notificationId}") 
    public String dismissNotification(@PathVariable String notificationId) { 
        try { 
            notificationRepository.deleteById(notificationId); 
            return "Notification dismissed successfully"; 
        } catch (Exception e) { 
            return "Error dismissing notification"; 
        } 
    } 
}