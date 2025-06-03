package com.example.sample_project.controller; 
 
import com.example.sample_project.entity.Loan; 
import com.example.sample_project.service.LoanService; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith; 
import org.mockito.InjectMocks; 
import org.mockito.Mock; 
import org.mockito.junit.jupiter.MockitoExtension; 
import org.springframework.http.ResponseEntity; 
 
import java.util.*; 
 
import static org.junit.jupiter.api.Assertions.*; 
import static org.mockito.Mockito.*; 
 
@ExtendWith(MockitoExtension.class)  // Enables Mockito support for JUnit 5 
class LoanControllerTest { 
 
    @Mock 
    private LoanService loanService;  // Mock service layer for loan operations 
 
    @InjectMocks 
    private LoanController loanController;  // Inject mocks into LoanController 
 
    private Loan loan;  // Test loan instance 
 
    @BeforeEach 
    void setUp() { 
        // Initialize test loan data before each test case 
        loan = new Loan(); 
        loan.setLoanId("123"); 
        loan.setLoanUserId("user123"); 
        loan.setDueDate(new Date()); 
        loan.setLoanAmountPrinciple(5000.0); 
        loan.setLoanStatus("Active"); 
    } 
 
    @Test 
    void testGetAllLoans_Success() { 
        // Simulate a case where loans exist 
        when(loanService.getAllLoans()).thenReturn(List.of(loan)); 
 
        // Call the controller method 
        ResponseEntity<?> response = loanController.getAllLoans(); 
 
        // Validate response 
        assertEquals(200, response.getStatusCodeValue()); 
        assertTrue(response.getBody() instanceof List); 
    } 
 
    @Test 
    void testGetAllLoans_NotFound() { 
        // Simulate a case where no loans are found 
        when(loanService.getAllLoans()).thenReturn(Collections.emptyList()); 
 
        // Call the controller method 
        ResponseEntity<?> response = loanController.getAllLoans(); 
 
        // Validate response 
        assertEquals(404, response.getStatusCodeValue()); 
    } 
 
    @Test 
    void testGetLoansByUserId_Success() { 
        // Mock service response with user-specific loans 
        when(loanService.getLoansByUserId("user123")).thenReturn(List.of(loan)); 
 
        // Call the controller method 
        ResponseEntity<List<Loan>> response = loanController.getLoansByUserId("user123"); 
 
        // Validate response 
        assertEquals(200, response.getStatusCodeValue()); 
        assertFalse(response.getBody().isEmpty()); 
    } 
 
    @Test 
    void testGetLoansByUserId_NotFound() { 
        // Mock service response with no loans for the user 
        when(loanService.getLoansByUserId("user456")).thenReturn(Collections.emptyList()); 
 
        // Call the controller method 
        ResponseEntity<List<Loan>> response = loanController.getLoansByUserId("user456"); 
 
        // Validate response 
        assertEquals(404, response.getStatusCodeValue()); 
    } 
 
    @Test 
    void testCreateLoan_Success() { 
        // Simulate loan creation 
        when(loanService.createLoan(any(Loan.class))).thenReturn(loan); 
 
        // Call the controller method 
        ResponseEntity<Loan> response = loanController.createLoan(loan); 
 
        // Validate response 
        assertEquals(200, response.getStatusCodeValue()); 
        assertNotNull(response.getBody()); 
    } 
 
    @Test 
    void testGetLoanByLoanId_Success() { 
        // Mock response for loan retrieval 
        when(loanService.getLoansByLoanId("123")).thenReturn(loan); 
 
        // Call the controller method 
        ResponseEntity<Loan> response = loanController.getLoanByLonId("123"); 
 
        // Validate response 
        assertEquals(200, response.getStatusCodeValue()); 
    } 
 
    @Test 
    void testUpdateLoan_Success() { 
        // Prepare update request with valid data 
        Map<String, Object> updates = new HashMap<>(); 
        updates.put("dueDate", "2025-12-01"); 
        updates.put("remainingPrincipal", 4000.0); 
        updates.put("loanStatus", "Closed"); 
 
        // Mock service response for update operation 
        doNothing().when(loanService).updateLoan(eq("123"), any(Date.class), anyDouble(), anyString()); 
 
        // Call the controller method 
        ResponseEntity<String> response = loanController.updateLoan("123", updates); 
 
        // Validate response 
        assertEquals(200, response.getStatusCodeValue()); 
        assertEquals("Loan details updated successfully!", response.getBody()); 
    } 
 
    @Test 
    void testUpdateLoan_MissingFields() { 
        // Prepare update request missing required fields 
        Map<String, Object> updates = new HashMap<>(); 
        updates.put("remainingPrincipal", 4000.0); 
 
        // Call the controller method 
        ResponseEntity<String> response = loanController.updateLoan("123", updates); 
 
        // Validate response 
        assertEquals(400, response.getStatusCodeValue()); 
        assertEquals("Missing required fields: dueDate or remainingPrincipal", response.getBody()); 
    } 
}