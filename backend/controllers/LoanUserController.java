package com.example.sample_project.controller; 
 
import com.example.sample_project.entity.LoanUserCollection; 
import com.example.sample_project.service.LoanUserCollectionService; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*; 
 
import java.util.List; 
import java.util.Map; 
 
@CrossOrigin(origins = "http://localhost:5173") 
@RestController 
@RequestMapping("/api/loanUser") 
public class LoanUserController { 
    @Autowired 
    private LoanUserCollectionService loanUserCollectionService; 
 
    /** 
     * Retrieves loan details associated with a user's PAN card. 
     * If no loans exist for the given PAN card, returns a 404 status. 
     * 
     * @param user_pan_card The PAN card ID of the user. 
     * @return List of loans associated with the given PAN card. 
     */ 
    @GetMapping("/{user_pan_card}") 
    public ResponseEntity<List<LoanUserCollection>> getLoanUserCollectionByPanID(@PathVariable("user_pan_card") String user_pan_card) { 
        System.out.println(" Fetching Loan for Pan Card: " + user_pan_card); 
        List<LoanUserCollection> loans = loanUserCollectionService.getLoansByUserPanCard(user_pan_card); 
        return loans.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(loans); 
    } 
 
    /** 
     * Updates the display status of a specific loan. 
     * Ensures the required 'loan_display_status' field is present before processing the update. 
     * 
     * @param loanId  The ID of the loan to be updated. 
     * @param updates A map containing the updated 'loan_display_status'. 
     * @return A success message or an error response if the update fails. 
     */ 
    @PatchMapping("/{loanId}") 
    public ResponseEntity<String> updateLoanDisplayStatus(@PathVariable String loanId, @RequestBody Map<String, Object> updates) { 
        try { 
            if (!updates.containsKey("loan_display_status")) { 
                return ResponseEntity.badRequest().body("Missing required field: loan_display_status"); 
            } 
 
            String loanDisplayStatus = updates.get("loan_display_status").toString(); 
 
            // Call service method to update loan display status 
            loanUserCollectionService.updateLoanDisplayStatus(loanId, loanDisplayStatus); 
 
            return ResponseEntity.ok("Loan display status updated successfully!"); 
 
        } catch (RuntimeException e) { 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage()); 
        } 
    } 
 
}