package com.example.sample_project.service; 
 
import com.example.sample_project.action.ActionRecorder; 
import com.example.sample_project.entity.Actions; 
import com.example.sample_project.entity.Loan; 
 
import com.example.sample_project.repository.LoanRepository; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
 
import java.util.Date; 
import java.util.List; 
 
/** 
* Service class for handling loan-related operations. 
* Connects with the LoanRepository to manage loan records. 
*/ 
@Service 
public class LoanService { 
 
    @Autowired 
    private LoanRepository loanRepository; 
 
    @Autowired 
    private ActionRecorder actionRecorder; 
 
    /** 
     * Retrieves all loans from the database. 
     * This method should typically not be accessed directly. 
     * 
     * @return A list of all loans. 
     */ 
    public List<Loan> getAllLoans() { 
        return loanRepository.findAll(); 
    } // This should not be accessed at all... 
    /** 
     * Retrieves loans associated with a specific user ID. 
     * Logs the number of loans found. 
     * 
     * @param loanUserId The ID of the user requesting loans. 
     * @return A list of loans linked to the user. 
     */ 
    public List<Loan> getLoansByUserId(String loanUserId) { 
        List<Loan> loans = loanRepository.findAllByLoanUserId(loanUserId); 
        return loans; 
    } 
 
 
 
    /** 
     * Creates and saves a new loan record in the database. 
     * Logged as an action event for tracking purposes. 
     * 
     * @param loan The loan entity to be created. 
     * @return The saved loan object. 
     */ 
    public Loan createLoan(Loan loan) { 
        actionRecorder.recordAction(loan.getLoanUserId(), loan.getLoanId(), Actions.ActionEventType.ADD_LOAN); 
        return loanRepository.save(loan); 
    } 
    /** 
     * Updates an existing loan's due date, remaining principal, and loan status. 
     * 
     * @param loanId The unique identifier of the loan to be updated. 
     * @param dueDate The new due date for the loan. 
     * @param remainingPrincipal The remaining principal amount. 
     * @param loanStatus The updated loan status. 
     */ 
 
    public void updateLoan(String loanId, Date dueDate, Double remainingPrincipal,String loanStatus) { 
        loanRepository.updateLoanDetails(loanId, dueDate, remainingPrincipal,loanStatus); 
    } 
 
    /** 
     * Deletes a loan from the database. 
     * @param loanId The ID of the loan to delete. 
     */ 
    public void deleteLoan(String loanId) { 
        loanRepository.deleteById(loanId); 
    } 
    /** 
     * Retrieves a loan by its unique loan ID. 
     * Logs the retrieval operation. 
     * @param loanId The ID of the loan to fetch. 
     * @return The loan entity corresponding to the loan ID. 
     */ 
    public Loan getLoansByLoanId(String loanId) { 
        Loan loanData = loanRepository.findByLoanId(loanId); 
        return loanData; 
    } 
 
 
}