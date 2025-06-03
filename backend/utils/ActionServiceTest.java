package com.example.sample_project.service; 
 
import com.example.sample_project.entity.Actions; 
import com.example.sample_project.repository.ActionRepository; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith; 
import org.mockito.InjectMocks; 
import org.mockito.Mock; 
import org.mockito.junit.jupiter.MockitoExtension; 
 
import java.time.LocalDateTime; 
import java.util.List; 
 
import static org.junit.jupiter.api.Assertions.*; 
import static org.mockito.Mockito.*; 
 
@ExtendWith(MockitoExtension.class)  // Enables Mockito support for JUnit 5 
class ActionServiceTest { 
 
    @Mock 
    private ActionRepository actionRepository;  // Mock repository for action retrieval 
 
    @InjectMocks 
    private ActionService actionService;  // Inject mock dependencies into ActionService 
 
    private Actions action1, action2;  // Sample action instances 
 
    @BeforeEach 
    void setUp() { 
        // Initialize test data before each test case 
        action1 = new Actions(); 
        action1.setActionId("A001"); 
        action1.setActionUserId("U123"); 
        action1.setActionEvent(Actions.ActionEventType.ADD_LOAN); 
        action1.setActionTimestamp(LocalDateTime.of(2025, 5, 10, 12, 30)); 
 
        action2 = new Actions(); 
        action2.setActionId("A002"); 
        action2.setActionUserId("U123"); 
        //action2.setActionEvent(Actions.ActionEventType.UPDATE_LOAN); 
        action2.setActionTimestamp(LocalDateTime.of(2025, 5, 11, 14, 0)); 
    } 
 
    //  Test getActionsSorted() - Retrieves sorted actions successfully 
    @Test 
    void testGetActionsSorted_Success() { 
        // Mock repository response returning actions sorted by timestamp (latest first) 
        when(actionRepository.findAllByActionUserIdOrderByActionTimestampDesc("U123")) 
                .thenReturn(List.of(action2, action1)); 
 
        // Call the service method 
        List<Actions> actionsList = actionService.getActionsSorted("U123"); 
 
        // Validate response 
        assertNotNull(actionsList); 
        assertFalse(actionsList.isEmpty()); 
        assertEquals(2, actionsList.size()); 
        assertEquals("A002", actionsList.get(0).getActionId());  // Latest action first 
        assertEquals("A001", actionsList.get(1).getActionId());  // Older action second 
 
        // Verify repository method call 
        verify(actionRepository, times(1)).findAllByActionUserIdOrderByActionTimestampDesc("U123"); 
    } 
 
    //  Test getActionsSorted() - Handle case where no actions exist 
    @Test 
    void testGetActionsSorted_Empty() { 
        // Mock repository response returning an empty list 
        when(actionRepository.findAllByActionUserIdOrderByActionTimestampDesc("U999")) 
                .thenReturn(List.of()); 
 
        // Call the service method 
        List<Actions> actionsList = actionService.getActionsSorted("U999"); 
 
        // Validate response 
        assertNotNull(actionsList); 
        assertTrue(actionsList.isEmpty()); 
 
        // Verify repository method call 
        verify(actionRepository, times(1)).findAllByActionUserIdOrderByActionTimestampDesc("U999"); 
    } 
}