package com.example.sample_project.repository; 
 
import org.springframework.data.jpa.repository.JpaRepository; 
import com.example.sample_project.entity.Repayment; 
import org.springframework.data.jpa.repository.Query; 
import org.springframework.data.repository.query.Param; 
 
import java.util.List; 
 
/** 
* Repository interface for managing repayment records in the database. 
* Provides CRUD operations for the Repayment entity and additional queries for retrieving repayment details. 
*/ 
public interface RepaymentRepository extends JpaRepository<Repayment, String> { 
 
    /** 
     * Retrieves all repayment records associated with a specific loan ID. 
     * Helps in tracking repayment history for a particular loan. 
     * 
     * @param repaymentLoanId The loan ID linked to the repayments. 
     * @return A list of repayment records related to the specified loan ID. 
     */ 
    List<Repayment> findByRepaymentLoanId(String repaymentLoanId); 
 
    /** 
     * Retrieves the latest repayment record for a specific loan ID. 
     * The records are ordered by repayment ID in descending order to get the latest repayment first. 
     * 
     * @param repaymentLoanId The loan ID used to fetch the latest repayment record. 
     * @return A list containing the latest repayment entry. 
     * <p> 
     * Note: The query ensures that the repayments are fetched in descending order by repayment ID. 
     */ 
    @Query("SELECT r FROM Repayment r WHERE r.repaymentLoanId = :loanId ORDER BY r.repaymentId DESC") 
    List<Repayment> findLatestRepaymentByLoanId(@Param("loanId") String repaymentLoanId); 
}