package com.example.sample_project.entity; 
 
import com.fasterxml.jackson.annotation.JsonFormat; 
import jakarta.persistence.*; 
import org.hibernate.annotations.CreationTimestamp; 
 
import java.time.LocalDateTime; 
 
@Entity 
@Table(name = "NOTIFICATIONS", indexes = { 
        @Index(name = "idx_user_id", columnList = "notificationUserId") // search becomes easier as the number of notifications can be a lot especially if not dismissed 
}) 
public class Notifications { 
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID) 
    @Column(name = "notification_id") 
    private String notificationId; 
 
    @Column(name = "notification_user_id") 
    private String notificationUserId; 
 
    @Column(name = "notification_message") 
    private String notificationMessage; 
 
    //    @CreationTimestamp 
    @Column(name = "notification_timestamp", nullable = false) 
    @Temporal(TemporalType.TIMESTAMP) 
    private LocalDateTime notificationTimestamp; 
 
    public String getNotificationId() { 
        return notificationId; 
    } 
 
    public void setNotificationId(String notificationId) { 
        this.notificationId = notificationId; 
    } 
 
    public String getNotificationUserId() { 
        return notificationUserId; 
    } 
 
    public void setNotificationUserId(String notificationUserId) { 
        this.notificationUserId = notificationUserId; 
    } 
 
    public String getNotificationMessage() { 
        return notificationMessage; 
    } 
 
    public void setNotificationMessage(String notificationMessage) { 
        this.notificationMessage = notificationMessage; 
    } 
 
    public LocalDateTime getNotificationTimestamp() { 
        return notificationTimestamp; 
    } 
 
    public void setNotificationTimestamp(LocalDateTime notificationTimestamp) { 
        this.notificationTimestamp = notificationTimestamp; 
    } 
}