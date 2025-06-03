package com.example.sample_project.service; 
 
import com.example.sample_project.action.ActionRecorder; 
import com.example.sample_project.entity.Actions; 
import com.example.sample_project.entity.Loan; 
import com.example.sample_project.entity.Repayment; 
import com.example.sample_project.repository.LoanRepository; 
import com.example.sample_project.repository.RepaymentRepository; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith; 
import org.mockito.InjectMocks; 
import org.mockito.Mock; 
import org.mockito.junit.jupiter.MockitoExtension; 
 
import java.util.List; 
import java.util.Optional; 
 
import static org.junit.jupiter.api.Assertions.*; 
import static org.mockito.Mockito.*; 
 
@ExtendWith(MockitoExtension.class)  // Enables Mockito support for JUnit 5 
class RepaymentServiceTest { 
 
    @Mock 
    private RepaymentRepository repaymentRepository;  // Mock repository for repayment data 
 
    @Mock 
    private LoanRepository loanRepository;  // Mock repository for loan data 
 
    @Mock 
    private ActionRecorder actionRecorder;  // Mock ActionRecorder for logging actions 
 
    @InjectMocks 
    private RepaymentService repaymentService;  // Inject mocks into RepaymentService 
 
    private Repayment repayment;  // Sample repayment instance 
    private Loan loan;  // Sample loan instance 
 
    @BeforeEach 
    void setUp() { 
        // Initialize test loan and repayment data before each test case 
        loan = new Loan(); 
        loan.setLoanId("L123"); 
        loan.setLoanUserId("U456"); 
 
        repayment = new Repayment(); 
        repayment.setRepaymentId("R789"); 
        repayment.setRepaymentLoanId("L123"); 
        //repayment.setRepaymentAmount(500.0); 
    } 
 
    // Test getAllRepayments() - Retrieves all repayments successfully 
    @Test 
    void testGetAllRepayments_Success() { 
        // Mock repository response returning a list with one repayment 
        when(repaymentRepository.findAll()).thenReturn(List.of(repayment)); 
 
        // Call the service method 
        List<Repayment> repayments = repaymentService.getAllRepayments(); 
 
        // Validate response 
        assertNotNull(repayments); 
        assertFalse(repayments.isEmpty()); 
        assertEquals(1, repayments.size()); 
        assertEquals("R789", repayments.get(0).getRepaymentId()); 
 
        // Verify repository method call 
        verify(repaymentRepository, times(1)).findAll(); 
    } 
 
    //  Test getRepaymentById() - Retrieves repayment by ID successfully 
    @Test 
    void testGetRepaymentById_Success() { 
        // Mock repository response returning repayment for given ID 
        when(repaymentRepository.findById("R789")).thenReturn(Optional.of(repayment)); 
 
        // Call the service method 
        Repayment retrievedRepayment = repaymentService.getRepaymentById("R789"); 
 
        // Validate response 
        assertNotNull(retrievedRepayment); 
        assertEquals("R789", retrievedRepayment.getRepaymentId()); 
 
        // Verify repository method call 
        verify(repaymentRepository, times(1)).findById("R789"); 
    } 
 
    //  Test getRepaymentById() - Handle repayment not found scenario 
    @Test 
    void testGetRepaymentById_NotFound() { 
        // Mock repository response returning empty Optional for missing repayment ID 
        when(repaymentRepository.findById("R999")).thenReturn(Optional.empty()); 
 
        // Call the service method 
        Repayment retrievedRepayment = repaymentService.getRepaymentById("R999"); 
 
        // Validate response 
        assertNull(retrievedRepayment); 
 
        // Verify repository method call 
        verify(repaymentRepository, times(1)).findById("R999"); 
    } 
 
    //  Test getRepaymentsByLoanId() - Retrieves repayments by loan ID successfully 
    @Test 
    void testGetRepaymentsByLoanId_Success() { 
        // Mock repository response returning repayments linked to loan ID 
        when(repaymentRepository.findByRepaymentLoanId("L123")).thenReturn(List.of(repayment)); 
 
        // Call the service method 
        List<Repayment> repayments = repaymentService.getRepaymentsByLoanId("L123"); 
 
        // Validate response 
        assertNotNull(repayments); 
        assertFalse(repayments.isEmpty()); 
        assertEquals(1, repayments.size()); 
        assertEquals("L123", repayments.get(0).getRepaymentLoanId()); 
 
        // Verify repository method call 
        verify(repaymentRepository, times(1)).findByRepaymentLoanId("L123"); 
    } 
 
    // Test saveRepayment() - Successfully saves repayment and records action 
    @Test 
    void testSaveRepayment_Success() { 
        // Mock repository response returning saved repayment 
        when(loanRepository.findByLoanId("L123")).thenReturn(loan); 
        when(repaymentRepository.save(repayment)).thenReturn(repayment); 
 
        // Call the service method 
        Repayment savedRepayment = repaymentService.saveRepayment(repayment); 
 
        // Validate response 
        assertNotNull(savedRepayment); 
        assertEquals("R789", savedRepayment.getRepaymentId()); 
 
        // Verify repository save method and action recording 
        verify(repaymentRepository, times(1)).save(repayment); 
        verify(actionRecorder, times(1)).recordAction("U456", "L123", Actions.ActionEventType.PAYMENT_UPDATE); 
    } 
 
    // Test saveRepayment() - Handle loan not found scenario 
    @Test 
    void testSaveRepayment_LoanNotFound() { 
        // Mock repository response returning null to simulate loan not found 
        when(loanRepository.findByLoanId(repayment.getRepaymentLoanId())).thenReturn(null); 
 
        // Expect IllegalArgumentException when trying to save repayment without an existing loan 
        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
                repaymentService.saveRepayment(repayment)); 
 
        // Validate exception message 
        assertEquals("Loan not found", exception.getMessage()); 
 
        // Verify repository method call 
        verify(loanRepository, times(1)).findByLoanId(repayment.getRepaymentLoanId()); 
        verify(repaymentRepository, never()).save(any(Repayment.class));  // Ensure save was not called 
    } 
 
 
    // Test deleteRepayment() - Successfully deletes a repayment 
    @Test 
    void testDeleteRepayment_Success() { 
        // Mock repository method call for deleting repayment 
        doNothing().when(repaymentRepository).deleteById("R789"); 
 
        // Call the service method 
        repaymentService.deleteRepayment("R789"); 
 
        // Verify repository delete method call 
        verify(repaymentRepository, times(1)).deleteById("R789"); 
    } 
 
    // Test getLatestRepaymentByLoanId() - Retrieves latest repayment successfully 
    @Test 
    void testGetLatestRepaymentByLoanId_Success() { 
        // Mock repository response returning a list of repayments 
        when(repaymentRepository.findLatestRepaymentByLoanId("L123")).thenReturn(List.of(repayment)); 
 
        // Call the service method 
        Optional<Repayment> latestRepayment = repaymentService.getLatestRepaymentByLoanId("L123"); 
 
        // Validate response 
        assertTrue(latestRepayment.isPresent()); 
        assertEquals("R789", latestRepayment.get().getRepaymentId()); 
 
        // Verify repository method call 
        verify(repaymentRepository, times(1)).findLatestRepaymentByLoanId("L123"); 
    } 
}