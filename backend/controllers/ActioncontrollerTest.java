package com.example.sample_project.controller; 
 
import com.example.sample_project.entity.Actions; 
import com.example.sample_project.service.ActionService; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith; 
import org.mockito.InjectMocks; 
import org.mockito.Mock; 
import org.mockito.junit.jupiter.MockitoExtension; 
import org.springframework.http.ResponseEntity; 
 
import java.time.LocalDateTime; 
import java.util.Collections; 
import java.util.List; 
import java.util.Map; 
 
import static org.junit.jupiter.api.Assertions.*; 
import static org.mockito.Mockito.*; 
 
@ExtendWith(MockitoExtension.class)  // Enables Mockito support for JUnit 5 
class ActionControllerTest { 
 
    @Mock 
    private ActionService actionService;  // Mock dependency for ActionService 
 
    @InjectMocks 
    private ActionController actionController;  // Controller instance with mocked dependencies 
 
    private final Actions action = new Actions();  // Sample action instance 
 
    @BeforeEach 
    void setUp() { 
        // Initialize action entity with sample data 
        action.setActionId("action123"); 
        action.setActionUserId("user123"); 
        action.setActionLoanId("loan123"); 
        action.setActionEvent(Actions.ActionEventType.ADD_LOAN); 
        action.setActionTimestamp(LocalDateTime.now()); 
    } 
 
    @Test 
    void testGetActionsSorted_Success() { 
        // Mock the service response: Returning a list with one action 
        when(actionService.getActionsSorted("user123")).thenReturn(List.of(action)); 
 
        // Create request payload for the controller method 
        Map<String, String> requestBody = Map.of("userId", "user123"); 
 
        // Call the controller method 
        List<Actions> actionsList = actionController.getActionsSorted(requestBody); 
 
        // Verify response is not null and contains expected data 
        assertNotNull(actionsList); 
        assertFalse(actionsList.isEmpty()); 
        assertEquals(1, actionsList.size()); 
        assertEquals("action123", actionsList.get(0).getActionId()); 
    } 
 
    @Test 
    void testGetActionsSorted_Empty() { 
        // Mock the service response: Returning an empty list when no actions are found 
        when(actionService.getActionsSorted("user456")).thenReturn(Collections.emptyList()); 
 
        // Create request payload for the controller method 
        Map<String, String> requestBody = Map.of("userId", "user456"); 
 
        // Call the controller method 
        List<Actions> actionsList = actionController.getActionsSorted(requestBody); 
 
        // Verify response is not null but contains an empty list 
        assertNotNull(actionsList); 
        assertTrue(actionsList.isEmpty()); 
    } 
}