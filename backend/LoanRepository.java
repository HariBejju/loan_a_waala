package com.example.sample_project.repository; 
 
import com.example.sample_project.entity.Loan; 
import jakarta.transaction.Transactional; 
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.data.jpa.repository.Modifying; 
import org.springframework.data.jpa.repository.Query; 
import org.springframework.data.repository.query.Param; 
import org.springframework.stereotype.Repository; 
 
import java.util.Date; 
import java.util.List; 
 
/** 
* Repository interface for Loan entity. 
* Provides methods to interact with the database for loan-related operations. 
* Extends JpaRepository to leverage built-in CRUD functionalities. 
*/ 
@Repository  // Marks this interface as a Spring Data repository 
public interface LoanRepository extends JpaRepository<Loan, String> { 
 
    /** 
     * Retrieves all loans associated with a specific user. 
     * 
     * @param loanUserId The ID of the user requesting loans. 
     * @return A list of loans linked to the user. 
     */ 
    List<Loan> findAllByLoanUserId(String loanUserId); 
 
    /** 
     * Retrieves a loan based on its unique loan ID. 
     * 
     * @param loanId The unique identifier of the loan. 
     * @return The loan entity corresponding to the loan ID. 
     */ 
    Loan findByLoanId(String loanId); 
 
    /** 
     * Retrieves loans based on their status. 
     * 
     * @param openLoans The loan status used for filtering loans. 
     * @return A list of loans matching the specified status. 
     */ 
    List<Loan> findByLoanStatus(String openLoans); 
 
    /** 
     * Updates a loan's due date, remaining principal, and loan status. 
     * Uses @Transactional to ensure the operation completes successfully. 
     * Uses @Modifying to indicate an update operation within @Query. 
     * 
     * @param loanId             The unique identifier of the loan. 
     * @param dueDate            The new due date for the loan. 
     * @param remainingPrincipal The remaining principal amount. 
     * @param loanStatus         The updated loan status. 
     */ 
    @Transactional 
    @Modifying 
    @Query("UPDATE Loan l SET l.dueDate = :dueDate, l.loanAmountPending = :remainingPrincipal, l.loanStatus = :loanStatus WHERE l.loanId = :loanId") 
    void updateLoanDetails( 
            @Param("loanId") String loanId, 
            @Param("dueDate") Date dueDate, 
            @Param("remainingPrincipal") Double remainingPrincipal, 
            @Param("loanStatus") String loanStatus 
    ); 
}