package com.example.sample_project.scheduler; 
 
import com.example.sample_project.action.ActionRecorder; 
import com.example.sample_project.entity.Actions; 
import com.example.sample_project.entity.Loan; 
import com.example.sample_project.entity.Notifications; 
import com.example.sample_project.repository.LoanRepository; 
import com.example.sample_project.repository.NotificationRepository; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.scheduling.annotation.Scheduled; 
import org.springframework.stereotype.Service; 
 
import java.time.LocalDate; 
import java.time.temporal.ChronoUnit; 
import java.util.List; 
import java.sql.Date; 
 
/** 
* Scheduled service for checking loan due dates and generating notifications. 
* Runs periodically to evaluate loan status and notify users. 
*/ 
@Service 
public class NotificationScheduler { 
 
    @Autowired 
    private LoanRepository loanRepository; 
    @Autowired 
    private NotificationRepository notificationRepository; 
    @Autowired 
    private ActionRecorder actionRecorder; 
 
    /** 
     * Scheduled task that checks loan due dates and generates notifications. 
     * Runs every 60 seconds for testing, but can be configured to run at specific times using cron expressions. 
     */ 
 
//    @Scheduled(fixedRate = 60000) // For demonstration purposes 
    @Scheduled(cron = "0 0 8,12,16,20 * * *") // Every day at 8:00 AM, 12:00 PM, 4:00 PM and 8:00 PM 
    public void checkLoanDueDates() { 
        System.out.println("Scheduler Activated"); 
        LocalDate today = LocalDate.now(); 
        List<Loan> openLoans = loanRepository.findByLoanStatus("Open"); 
 
        for (Loan loan : openLoans) { 
            Date loanDueDate = (Date) loan.getDueDate(); 
 
            LocalDate dueDate = loanDueDate.toLocalDate(); 
 
            long daysUntilDue = ChronoUnit.DAYS.between(today, dueDate); // Calculate days difference 
 
            String message = null; 
 
            if (daysUntilDue < 0) { 
                message = "Loan ID " + loan.getLoanId() + " is overdue!"; 
            } else if (daysUntilDue == 0) { 
                message = "Loan ID " + loan.getLoanId() + " is due today!"; 
            } else if (daysUntilDue <= 3) { 
                message = "Loan ID " + loan.getLoanId() + " is due in " + daysUntilDue + " day(s)."; 
            } 
 
            if (message != null) { 
                Notifications notification = new Notifications(); 
                notification.setNotificationUserId(loan.getLoanUserId()); 
                notification.setNotificationMessage(message); 
                notification.setNotificationTimestamp(java.time.LocalDateTime.now()); 
                notificationRepository.save(notification); 
                actionRecorder.recordAction(loan.getLoanUserId(), loan.getLoanId(), Actions.ActionEventType.NOTIFICATION); 
            } 
        } 
    } 
}