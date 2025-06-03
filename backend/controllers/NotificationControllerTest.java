package com.example.sample_project.controller; 
 
import com.example.sample_project.entity.Notifications; 
import com.example.sample_project.repository.NotificationRepository; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith; 
import org.mockito.InjectMocks; 
import org.mockito.Mock; 
import org.mockito.junit.jupiter.MockitoExtension; 
import org.springframework.http.ResponseEntity; 
 
import java.time.LocalDateTime; 
import java.util.List; 
 
import static org.junit.jupiter.api.Assertions.*; 
import static org.mockito.Mockito.*; 
 
@ExtendWith(MockitoExtension.class)  // Enables Mockito support for JUnit 5 
class NotificationControllerTest { 
 
    @Mock 
    private NotificationRepository notificationRepository;  // Mock repository for notification data 
 
    @InjectMocks 
    private NotificationController notificationController;  // Inject mocks into NotificationController 
 
    private final Notifications notification = new Notifications();  // Test notification instance 
 
    @BeforeEach 
    void setUp() { 
        // Initialize test notification data before each test case 
        notification.setNotificationId("notification123"); 
        notification.setNotificationUserId("user123"); 
        notification.setNotificationMessage("This is a test notification"); 
        notification.setNotificationTimestamp(LocalDateTime.now()); 
    } 
 
    //  Test @GetMapping("/{userId}") - Retrieve user notifications successfully 
    @Test 
    void testGetUserNotifications_Success() { 
        // Mock repository response with a list containing one notification 
        when(notificationRepository.findByNotificationUserIdOrderByNotificationTimestampDesc("user123")) 
                .thenReturn(List.of(notification)); 
 
        // Call the controller method 
        ResponseEntity<List<Notifications>> response = notificationController.getUserNotifications("user123"); 
 
        // Validate response 
        assertEquals(200, response.getStatusCodeValue()); 
        assertNotNull(response.getBody()); 
        assertFalse(response.getBody().isEmpty()); 
        assertEquals(1, response.getBody().size()); 
        assertEquals("notification123", response.getBody().get(0).getNotificationId()); 
    } 
 
    //  Test @GetMapping("/{userId}") - Handle case where user has no notifications 
    @Test 
    void testGetUserNotifications_Empty() { 
        // Mock repository response with an empty list 
        when(notificationRepository.findByNotificationUserIdOrderByNotificationTimestampDesc("user456")) 
                .thenReturn(List.of()); 
 
        // Call the controller method 
        ResponseEntity<List<Notifications>> response = notificationController.getUserNotifications("user456"); 
 
        // Validate response 
        assertEquals(200, response.getStatusCodeValue());  // Still returns 200, but empty list 
        assertNotNull(response.getBody()); 
        assertTrue(response.getBody().isEmpty()); 
    } 
 
    //  Test @GetMapping("/count/{userId}") - Retrieve notification count successfully 
    @Test 
    void testGetCountByUser() { 
        // Mock repository response with a count of 5 notifications 
        when(notificationRepository.countByNotificationUserId("user123")).thenReturn(5L); 
 
        // Call the controller method 
        long count = notificationController.getCountByUser("user123"); 
 
        // Validate response 
        assertEquals(5L, count); 
    } 
 
    //  Test @DeleteMapping("/{notificationId}") - Successfully dismiss notification 
    @Test 
    void testDismissNotification_Success() { 
        // Mock repository response where delete operation executes without issue 
        doNothing().when(notificationRepository).deleteById("notification123"); 
 
        // Call the controller method 
        String response = notificationController.dismissNotification("notification123"); 
 
        // Validate response 
        assertEquals("Notification dismissed successfully", response); 
    } 
 
    //  Test @DeleteMapping("/{notificationId}") - Handle error case while dismissing notification 
    @Test 
    void testDismissNotification_Error() { 
        // Mock repository response where delete operation throws an exception 
        doThrow(new RuntimeException("Error")).when(notificationRepository).deleteById("notification123"); 
 
        // Call the controller method 
        String response = notificationController.dismissNotification("notification123"); 
 
        // Validate response 
        assertEquals("Error dismissing notification", response); 
    } 
}