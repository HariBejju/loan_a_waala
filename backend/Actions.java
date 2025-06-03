package com.example.sample_project.entity; 
 
import com.fasterxml.jackson.annotation.JsonFormat; 
import jakarta.persistence.*; 
import org.hibernate.annotations.CreationTimestamp; 
 
import java.time.LocalDateTime; 
 
@Entity 
@Table(name = "ACTIONS") 
public class Actions { 
 
    @Id 
//    @GeneratedValue(strategy = GenerationType.UUID) 
    @Column(name = "action_id", nullable = false, unique = true, length = 128) 
    private String actionId; 
 
    @Column(name = "action_user_id", nullable = false) 
    private String actionUserId; 
 
    @CreationTimestamp 
    @Temporal(TemporalType.TIMESTAMP) 
    @Column(name = "action_timestamp", nullable = false) 
    private LocalDateTime actionTimestamp; 
 
    @Enumerated(EnumType.STRING) 
    @Column(name = "action_event", nullable = false) // unique = false 
    private ActionEventType actionEvent; 
 
    @Column(name = "action_loan_id", length = 6) // unique = false, nullable = true 
    private String actionLoanId; 
 
    public enum ActionEventType { 
        LOGIN, 
        NOTIFICATION, 
        PAYMENT_UPDATE, 
        ADD_LOAN 
    } 
 
    public Actions() { 
    } 
 
    public Actions(String actionId, String actionUserId, LocalDateTime actionTimestamp, ActionEventType actionEvent, String actionLoanId) { 
        this.actionId = actionId; 
        this.actionUserId = actionUserId; 
        this.actionTimestamp = actionTimestamp; 
        this.actionEvent = actionEvent; 
        this.actionLoanId = actionLoanId; 
    } 
 
    public String getActionId() { 
        return actionId; 
    } 
 
    public void setActionId(String actionId) { 
        this.actionId = actionId; 
    } 
 
    public String getActionUserId() { 
        return actionUserId; 
    } 
 
    public void setActionUserId(String actionUserId) { 
        this.actionUserId = actionUserId; 
    } 
 
    public LocalDateTime getActionTimestamp() { 
        return actionTimestamp; 
    } 
 
    public void setActionTimestamp(LocalDateTime actionTimestamp) { 
        this.actionTimestamp = actionTimestamp; 
    } 
 
    public ActionEventType getActionEvent() { 
        return actionEvent; 
    } 
 
    public void setActionEvent(ActionEventType actionEvent) { 
        this.actionEvent = actionEvent; 
    } 
 
    public String getActionLoanId() { 
        return actionLoanId; 
    } 
 
    public void setActionLoanId(String actionLoanId) { 
        this.actionLoanId = actionLoanId; 
    } 
}