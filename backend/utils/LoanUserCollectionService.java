package com.example.sample_project.service; 
 
import com.example.sample_project.entity.LoanUserCollection; 
import com.example.sample_project.repository.LoanUserCollectionRepository; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
 
import java.util.List; 
/** 
* Service class for handling loan-related operations on LoanUserCollection. 
* Provides functionality to retrieve and update loan display statuses. 
*/ 
@Service 
public class LoanUserCollectionService { 
    @Autowired 
    private LoanUserCollectionRepository loanUserCollectionRepository; 
    /** 
     * Retrieves all loans associated with a specific user's PAN card. 
     * 
     * @param userPanCard The PAN card ID used to identify the user's loans. 
     * @return A list of loans linked to the provided PAN card. 
     */ 
    public List<LoanUserCollection> getLoansByUserPanCard(String userPanCard){ 
        return loanUserCollectionRepository.findAllByUserPanCard(userPanCard); 
    } 
    /** 
     * Updates the display status of a loan. 
     * Throws an exception if the loan is not found. 
     * 
     * @param loanId The unique identifier of the loan. 
     * @param loanDisplayStatus The new display status for the loan. 
     */ 
 
    public void updateLoanDisplayStatus(String loanId, String loanDisplayStatus) { 
        LoanUserCollection loan = loanUserCollectionRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan not found")); 
        loan.setLoanDisplayStatus(loanDisplayStatus); 
        loanUserCollectionRepository.save(loan); 
    } 
}