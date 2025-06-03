package com.example.sample_project.action; 
 
import com.example.sample_project.entity.Actions; 
import com.example.sample_project.repository.ActionRepository; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Component; 
 
import java.time.LocalDateTime; 
import java.util.UUID; 
 
/** 
* The ActionRecorder class is responsible for recording user actions into the database. 
* Each recorded action includes a unique ID, user ID, loan ID (if applicable), event type, and timestamp. 
* This class uses ActionRepository to persist the action records. 
*/ 
@Component  // Marks this class as a Spring-managed component for dependency injection 
public class ActionRecorder { 
 
    @Autowired 
    private ActionRepository actionRepository;  // Injects ActionRepository for saving action records 
 
    /** 
     * Records a user action and persists it in the database. 
     * 
     * @param userId    The ID of the user performing the action. 
     * @param loanId    The associated loan ID (if applicable). 
     * @param eventType The type of action event (e.g., LOGIN, ADD_LOAN, PAYMENT_UPDATE). 
     */ 
    public void recordAction(String userId, String loanId, Actions.ActionEventType eventType) { 
        // Create a new Actions object to store user activity details 
        Actions actions = new Actions(); 
        actions.setActionId(UUID.randomUUID().toString());  // Generate a unique ID for the action 
        actions.setActionUserId(userId);  // Associate action with the user 
        actions.setActionLoanId(loanId);  // Associate action with the loan (if applicable) 
        actions.setActionEvent(eventType);  // Set the action event type 
        actions.setActionTimestamp(LocalDateTime.now());  // Capture the current timestamp 
 
        // Persist the action record in the database using ActionRepository 
        actionRepository.save(actions); 
    } 
}