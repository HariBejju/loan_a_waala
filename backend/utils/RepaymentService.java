package com.example.sample_project.service; 
 
import com.example.sample_project.action.ActionRecorder; 
import com.example.sample_project.entity.Actions; 
import com.example.sample_project.entity.Loan; 
import com.example.sample_project.entity.Repayment; 
import com.example.sample_project.repository.LoanRepository; 
import com.example.sample_project.repository.RepaymentRepository; 
import jakarta.transaction.Transactional; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
 
import java.util.List; 
import java.util.Optional; 
/** 
* Service class for managing repayment-related operations. 
* Handles retrieval, creation, updating, and deletion of repayment records. 
*/ 
@Service 
public class RepaymentService { 
    @Autowired 
    private RepaymentRepository repaymentRepository; 
    @Autowired 
    private LoanRepository loanRepository; 
    @Autowired 
    private ActionRecorder actionRecorder; 
    /** 
     * Retrieves all repayment records from the database. 
     * 
     * @return A list of all repayment entries. 
     */ 
    public List<Repayment> getAllRepayments() { 
        return repaymentRepository.findAll(); 
    } 
    /** 
     * Retrieves a repayment record by its unique repayment ID. 
     * 
     * @param repaymentId The ID of the repayment to retrieve. 
     * @return The repayment details corresponding to the given ID, or null if not found. 
     */ 
    public Repayment getRepaymentById(String repaymentId) { 
        return repaymentRepository.findById(repaymentId).orElse(null); 
    } 
    /** 
     * Retrieves all repayment records associated with a given loan ID. 
     * 
     * @param repaymentLoanId The loan ID to fetch repayment records for. 
     * @return A list of repayments linked to the specified loan ID. 
     */ 
    public List<Repayment> getRepaymentsByLoanId(String repaymentLoanId) { 
        return repaymentRepository.findByRepaymentLoanId(repaymentLoanId); 
    } 
    /** 
     * Creates and saves a new repayment record. 
     * Triggers an action event for tracking payment updates. 
     * 
     * @param repayment The repayment object received in the request body. 
     * @return The saved repayment entry. 
     */ 
    @Transactional 
    public Repayment saveRepayment(Repayment repayment) { 
        Loan loan = loanRepository.findByLoanId(repayment.getRepaymentLoanId()); 
 
        if (loan != null) { 
            actionRecorder.recordAction(loan.getLoanUserId(), loan.getLoanId(), Actions.ActionEventType.PAYMENT_UPDATE); 
        } else { 
            throw new IllegalArgumentException("Loan not found"); 
        } 
        return repaymentRepository.save(repayment); 
    } 
 
    /** 
     * Deletes a repayment record from the database based on its ID. 
     * 
     * @param repaymentId The ID of the repayment to delete. 
     */ 
    public void deleteRepayment(String repaymentId) { 
        repaymentRepository.deleteById(repaymentId); 
    } 
    /** 
     * Retrieves the latest repayment entry for a specific loan ID. 
     * Returns the first repayment in the list if available. 
     * 
     * @param loanId The loan ID to fetch the latest repayment. 
     * @return An Optional containing the latest repayment entry, or empty if none exist. 
     */ 
    public Optional<Repayment> getLatestRepaymentByLoanId(String loanId) { 
        List<Repayment> repayments = repaymentRepository.findLatestRepaymentByLoanId(loanId); 
        return repayments.stream().findFirst(); // ✅ Returns Optional 
    } 
 
 
}