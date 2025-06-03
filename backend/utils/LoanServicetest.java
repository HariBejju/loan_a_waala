package com.example.sample_project.service; 
 
import com.example.sample_project.action.ActionRecorder; 
import com.example.sample_project.entity.Actions; 
import com.example.sample_project.entity.Loan; 
import com.example.sample_project.repository.LoanRepository; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith; 
import org.mockito.InjectMocks; 
import org.mockito.Mock; 
import org.mockito.junit.jupiter.MockitoExtension; 
 
import java.util.Date; 
import java.util.List; 
 
import static org.junit.jupiter.api.Assertions.*; 
import static org.mockito.Mockito.*; 
 
@ExtendWith(MockitoExtension.class)  // Enables Mockito support for JUnit 5 
class LoanServiceTest { 
 
    @Mock 
    private LoanRepository loanRepository;  // Mock repository for loan data 
 
    @Mock 
    private ActionRecorder actionRecorder;  // Mock ActionRecorder for logging actions 
 
    @InjectMocks 
    private LoanService loanService;  // Inject mocks into LoanService 
 
    private Loan loan;  // Sample loan instance for testing 
 
    @BeforeEach 
    void setUp() { 
        // Initialize test loan data before each test case 
        loan = new Loan(); 
        loan.setLoanId("L123"); 
        loan.setLoanUserId("U456"); 
        loan.setDueDate(new Date()); 
        loan.setLoanAmountPrinciple(5000.0); 
        loan.setLoanStatus("Active"); 
    } 
 
    //  Test getAllLoans() - Retrieves all loans successfully 
    @Test 
    void testGetAllLoans_Success() { 
        // Mock repository response returning a list with one loan 
        when(loanRepository.findAll()).thenReturn(List.of(loan)); 
 
        // Call the service method 
        List<Loan> loans = loanService.getAllLoans(); 
 
        // Validate response 
        assertNotNull(loans); 
        assertFalse(loans.isEmpty()); 
        assertEquals(1, loans.size()); 
        assertEquals("L123", loans.get(0).getLoanId()); 
 
        // Verify repository method call 
        verify(loanRepository, times(1)).findAll(); 
    } 
 
    //  Test getLoansByUserId() - Retrieves loans by user ID 
    @Test 
    void testGetLoansByUserId_Success() { 
        // Mock repository response with loans linked to a user 
        when(loanRepository.findAllByLoanUserId("U456")).thenReturn(List.of(loan)); 
 
        // Call the service method 
        List<Loan> loans = loanService.getLoansByUserId("U456"); 
 
        // Validate response 
        assertNotNull(loans); 
        assertFalse(loans.isEmpty()); 
        assertEquals(1, loans.size()); 
        assertEquals("U456", loans.get(0).getLoanUserId()); 
 
        // Verify repository method call 
        verify(loanRepository, times(1)).findAllByLoanUserId("U456"); 
    } 
 
    // Test createLoan() - Successfully creates and records loan 
    @Test 
    void testCreateLoan_Success() { 
        // Mock repository response returning saved loan 
        when(loanRepository.save(loan)).thenReturn(loan); 
 
        // Call the service method 
        Loan savedLoan = loanService.createLoan(loan); 
 
        // Validate response 
        assertNotNull(savedLoan); 
        assertEquals("L123", savedLoan.getLoanId()); 
 
        // Verify repository save method and action recording 
        verify(loanRepository, times(1)).save(loan); 
        verify(actionRecorder, times(1)).recordAction("U456", "L123", Actions.ActionEventType.ADD_LOAN); 
    } 
 
    //  Test updateLoan() - Successfully updates loan details 
    @Test 
    void testUpdateLoan_Success() { 
        // Mock repository method call for updating loan details 
        doNothing().when(loanRepository).updateLoanDetails(eq("L123"), any(Date.class), anyDouble(), anyString()); 
 
        // Call the service method 
        loanService.updateLoan("L123", new Date(), 4000.0, "Closed"); 
 
        // Verify repository update method call 
        verify(loanRepository, times(1)).updateLoanDetails(eq("L123"), any(Date.class), anyDouble(), anyString()); 
    } 
 
    // Test deleteLoan() - Successfully deletes a loan 
    @Test 
    void testDeleteLoan_Success() { 
        // Mock repository method call for deleting loan 
        doNothing().when(loanRepository).deleteById("L123"); 
 
        // Call the service method 
        loanService.deleteLoan("L123"); 
 
        // Verify repository delete method call 
        verify(loanRepository, times(1)).deleteById("L123"); 
    } 
 
    //  Test getLoansByLoanId() - Retrieves loan by loan ID 
    @Test 
    void testGetLoansByLoanId_Success() { 
        // Mock repository response returning loan for given ID 
        when(loanRepository.findByLoanId("L123")).thenReturn(loan); 
 
        // Call the service method 
        Loan retrievedLoan = loanService.getLoansByLoanId("L123"); 
 
        // Validate response 
        assertNotNull(retrievedLoan); 
        assertEquals("L123", retrievedLoan.getLoanId()); 
 
        // Verify repository method call 
        verify(loanRepository, times(1)).findByLoanId("L123"); 
    } 
}