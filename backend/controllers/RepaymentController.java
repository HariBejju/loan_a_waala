package com.example.sample_project.controller; 
 
import com.example.sample_project.entity.Repayment; 
import com.example.sample_project.service.RepaymentService; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*; 
import org.springframework.web.bind.annotation.CrossOrigin; 
 
import java.util.List; 
import java.util.Map; 
import java.util.Optional; 
 
@CrossOrigin(origins = "http://localhost:5173") 
@RestController 
@RequestMapping("/repayments") 
public class RepaymentController { 
 
    @Autowired 
    private RepaymentService repaymentService; 
 
    /** 
     * Retrieves all repayment records from the database. 
     * 
     * @return A list of all repayment entries. 
     */ 
    @GetMapping 
    public List<Repayment> getAllRepayments() { 
        return repaymentService.getAllRepayments(); 
    } 
 
    @GetMapping("/{repaymentId}") 
    public Repayment getRepaymentById(@PathVariable String repaymentId) { 
        return repaymentService.getRepaymentById(repaymentId); 
    } 
 
    /** 
     * Creates and saves a new repayment record. 
     * 
     * @param repayment The repayment object received in the request body. 
     * @return The saved repayment entry. 
     */ 
    @PostMapping 
    public Repayment saveRepayment(@RequestBody Repayment repayment) { 
        return repaymentService.saveRepayment(repayment); 
    } 
 
    /** 
     * Retrieves all repayment records associated with a given loan ID. 
     * Returns a 404 status if no repayments are found. 
     * 
     * @param repaymentLoanId The loan ID to fetch repayment records for. 
     * @return A list of repayments or a 404 response if none exist. 
     */ 
    @GetMapping("/loan/{repaymentLoanId}") 
    public ResponseEntity<List<Repayment>> getRepaymentsByLoanId(@PathVariable String repaymentLoanId) { 
        List<Repayment> repayments = repaymentService.getRepaymentsByLoanId(repaymentLoanId); 
        return repayments.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(repayments); 
    } 
 
    /** 
     * Deletes a repayment record from the database based on its ID. 
     * 
     * @param repaymentId The ID of the repayment to delete. 
     */ 
    @DeleteMapping("/{repaymentId}") 
    public void deleteRepayment(@PathVariable String repaymentId) { 
        repaymentService.deleteRepayment(repaymentId); 
    } 
 
    /** 
     * Retrieves the latest repayment entry for a specific loan ID. 
     * Ensures that a valid repayment exists before returning data. 
     * 
     * @param loanId The loan ID to fetch the latest repayment. 
     * @return The latest repayment entry or a 404 response if none exist. 
     */ 
 
    @GetMapping("/latest/{loanId}") 
    public ResponseEntity<Repayment> getLatestRepaymentByLoanId(@PathVariable String loanId) { 
        Optional<Repayment> latestRepayments = repaymentService.getLatestRepaymentByLoanId(loanId); 
 
        // Ensure the list is not empty before accessing its first element 
        return latestRepayments.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); 
    } 
 
 
}