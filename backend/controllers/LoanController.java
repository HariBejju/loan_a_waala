package com.example.sample_project.controller; 
 
import com.example.sample_project.entity.Loan; 
import com.example.sample_project.service.LoanService; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*; 
import org.springframework.web.bind.annotation.CrossOrigin; 
 
import java.text.SimpleDateFormat; 
import java.util.Date; 
import java.util.List; 
import java.util.Map; 
 
@CrossOrigin(origins = "http://localhost:5173") 
@RestController 
@RequestMapping("/api/loans") 
public class LoanController { 
 
    @Autowired 
    private LoanService loanService; 
 
    /** 
     * Retrieves all loans from the database. 
     * Returns a 404 response if no loans are found. 
     */ 
    @GetMapping 
    public ResponseEntity<?> getAllLoans() { 
        try { 
            List<Loan> loans = loanService.getAllLoans(); 
 
            if (loans.isEmpty()) { 
                return ResponseEntity.status(404) 
                        .body(Map.of("message", "No loans found"));  // 
            } 
 
            return ResponseEntity.ok(loans); 
        } catch (Exception e) { 
            return ResponseEntity.status(500) 
                    .body(Map.of("error", "Error fetching loans", "details", e.getMessage()));  // 
        } 
    } 
 
 
    /** 
     * Retrieves loans associated with a specific user ID. 
     * Returns a 404 response if no loans are found for the user. 
     * 
     * @param loanUserId The ID of the user whose loans are requested. 
     * @return List of loans for the given user. 
     */ 
 
    @GetMapping("/{loanUserId}") 
    public ResponseEntity<List<Loan>> getLoansByUserId(@PathVariable String loanUserId) { 
        List<Loan> loans = loanService.getLoansByUserId(loanUserId); 
        return loans.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(loans); 
    } 
 
    /** 
     * Creates a new loan and saves it to the database. 
     * 
     * @param loan The loan details sent in the request body. 
     * @return The created loan object. 
     */ 
 
    @CrossOrigin(origins = "http://localhost:5173") 
    @PostMapping 
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) { 
        Loan createdLoan = loanService.createLoan(loan); 
        return ResponseEntity.ok(createdLoan); 
    } 
 
    /** 
     * Retrieves loan details based on a loan ID. 
     * 
     * @param loanId The ID of the loan to retrieve. 
     * @return The loan details. 
     */ 
    @GetMapping("/loan-details/{loanId}") 
    public ResponseEntity<Loan> getLoanByLonId(@PathVariable String loanId) { 
        Loan loanDetails = loanService.getLoansByLoanId(loanId); 
        return ResponseEntity.ok(loanDetails); 
    } 
 
 
    /** 
     * Handles loan update requests via PATCH mapping. 
     * Validates required fields and converts input data to appropriate types before updating loan details. 
     * If validation fails, returns a bad request response with error details. 
     * If loan is successfully updated, returns a success message. 
     * If an error occurs during update, returns a not found response with the error message. 
     */ 
    @PatchMapping("/{loanId}") 
    public ResponseEntity<String> updateLoan(@PathVariable String loanId, @RequestBody Map<String, Object> updates) { 
        try { 
            if (!updates.containsKey("dueDate") || !updates.containsKey("remainingPrincipal")) { 
                return ResponseEntity.badRequest().body("Missing required fields: dueDate or remainingPrincipal"); 
            } 
 
            // Convert dueDate from String to Date 
            String dueDateStr = (String) updates.get("dueDate"); 
            Date dueDate; 
            try { 
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
                dueDate = formatter.parse(dueDateStr); 
            } catch (Exception e) { 
                return ResponseEntity.badRequest().body("Invalid date format. Use yyyy-MM-dd"); 
            } 
 
            // Convert remainingPrincipal from Object to Double 
            Double remainingPrincipal = Double.parseDouble(updates.get("remainingPrincipal").toString()); 
 
            String loanStatus = updates.get("loanStatus").toString(); 
 
            // Call service method to update loan details 
            loanService.updateLoan(loanId, dueDate, remainingPrincipal, loanStatus); 
            return ResponseEntity.ok("Loan details updated successfully!"); 
 
        } catch (RuntimeException e) { 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage()); 
        } 
    } 
 
}