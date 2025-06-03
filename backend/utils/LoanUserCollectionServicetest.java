package com.example.sample_project.service; 
 
import com.example.sample_project.entity.LoanUserCollection; 
import com.example.sample_project.repository.LoanUserCollectionRepository; 
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
class LoanUserCollectionServiceTest { 
 
    @Mock 
    private LoanUserCollectionRepository loanUserCollectionRepository;  // Mock repository for loan user collection data 
 
    @InjectMocks 
    private LoanUserCollectionService loanUserCollectionService;  // Inject mocks into LoanUserCollectionService 
 
    private LoanUserCollection loan;  // Sample loan instance for testing 
 
    @BeforeEach 
    void setUp() { 
        // Initialize test loan data before each test case 
        loan = new LoanUserCollection(); 
        loan.setLoanId("L123"); 
        loan.setUserPanCard("PAN12345"); 
        loan.setLoanDisplayStatus("Active"); 
    } 
 
    //  Test getLoansByUserPanCard() - Retrieves loans successfully 
    @Test 
    void testGetLoansByUserPanCard_Success() { 
        // Mock repository response returning a list with one loan 
        when(loanUserCollectionRepository.findAllByUserPanCard("PAN12345")).thenReturn(List.of(loan)); 
 
        // Call the service method 
        List<LoanUserCollection> loans = loanUserCollectionService.getLoansByUserPanCard("PAN12345"); 
 
        // Validate response 
        assertNotNull(loans); 
        assertFalse(loans.isEmpty()); 
        assertEquals(1, loans.size()); 
        assertEquals("L123", loans.get(0).getLoanId()); 
 
        // Verify repository method call 
        verify(loanUserCollectionRepository, times(1)).findAllByUserPanCard("PAN12345"); 
    } 
 
    //  Test getLoansByUserPanCard() - Handle case where no loans exist 
    @Test 
    void testGetLoansByUserPanCard_NoLoansFound() { 
        // Mock repository response returning an empty list 
        when(loanUserCollectionRepository.findAllByUserPanCard("PAN67890")).thenReturn(List.of()); 
 
        // Call the service method 
        List<LoanUserCollection> loans = loanUserCollectionService.getLoansByUserPanCard("PAN67890"); 
 
        // Validate response 
        assertNotNull(loans); 
        assertTrue(loans.isEmpty()); 
 
        // Verify repository method call 
        verify(loanUserCollectionRepository, times(1)).findAllByUserPanCard("PAN67890"); 
    } 
 
    //  Test updateLoanDisplayStatus() - Successfully updates loan display status 
    @Test 
    void testUpdateLoanDisplayStatus_Success() { 
        // Mock repository response returning the loan instance 
        when(loanUserCollectionRepository.findById("L123")).thenReturn(Optional.of(loan)); 
 
        // Call the service method 
        loanUserCollectionService.updateLoanDisplayStatus("L123", "Closed"); 
 
        // Verify loan display status update 
        assertEquals("Closed", loan.getLoanDisplayStatus()); 
 
        // Verify repository save method call 
        verify(loanUserCollectionRepository, times(1)).save(loan); 
    } 
 
    //  Test updateLoanDisplayStatus() - Handle loan not found scenario 
    @Test 
    void testUpdateLoanDisplayStatus_LoanNotFound() { 
        // Mock repository response returning empty Optional for missing loan ID 
        when(loanUserCollectionRepository.findById("L999")).thenReturn(Optional.empty()); 
 
        // Expect RuntimeException when trying to update a non-existing loan 
        Exception exception = assertThrows(RuntimeException.class, () -> 
                loanUserCollectionService.updateLoanDisplayStatus("L999", "Closed")); 
 
        // Validate exception message 
        assertEquals("Loan not found", exception.getMessage()); 
 
        // Verify repository method call 
        verify(loanUserCollectionRepository, times(1)).findById("L999"); 
        verify(loanUserCollectionRepository, never()).save(any(LoanUserCollection.class));  // Ensure save was not called 
    } 
}