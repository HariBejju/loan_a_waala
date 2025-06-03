package com.example.sample_project.controller; 
 
import com.example.sample_project.entity.LoanUserCollection; 
import com.example.sample_project.service.LoanUserCollectionService; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith; 
import org.mockito.InjectMocks; 
import org.mockito.Mock; 
import org.mockito.junit.jupiter.MockitoExtension; 
import org.springframework.http.MediaType; 
import org.springframework.test.web.servlet.MockMvc; 
import org.springframework.test.web.servlet.setup.MockMvcBuilders; 
 
import java.util.List; 
 
import static org.mockito.Mockito.*; 
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; 
 
@ExtendWith(MockitoExtension.class)  // Enables Mockito support for JUnit 5 
class LoanUserControllerTest { 
 
    private MockMvc mockMvc;  // MockMvc to simulate HTTP requests to the controller 
 
    @Mock 
    private LoanUserCollectionService loanUserCollectionService;  // Mock service layer for loan user collection operations 
 
    @InjectMocks 
    private LoanUserController loanUserController;  // Inject mock dependencies into LoanUserController 
 
    @BeforeEach 
    void setup() { 
        // Initialize MockMvc before each test case 
        mockMvc = MockMvcBuilders.standaloneSetup(loanUserController).build(); 
    } 
 
    //  Test @GetMapping("/{user_pan_card}") - Get Loan by PAN Card 
    @Test 
    void testGetLoanUserCollectionByPanID() throws Exception { 
        // Creating LoanUserCollection object with test data 
        LoanUserCollection loan = new LoanUserCollection(); 
        loan.setLoanId("L0161"); 
        loan.setLoanName("Education Loan"); 
 
        // Mock service response to return a loan for given PAN card 
        when(loanUserCollectionService.getLoansByUserPanCard("SS1234TH")).thenReturn(List.of(loan)); 
 
        // Perform GET request and validate response 
        mockMvc.perform(get("/api/loanUser/SS1234TH")) 
                .andExpect(status().isOk())  // Expect HTTP 200 OK 
                .andExpect(jsonPath("$[0].loanId").value("L0161"))  // Validate loan ID in response 
                .andExpect(jsonPath("$[0].loanName").value("Education Loan"));  // Validate loan name 
 
        // Verify service method was called exactly once 
        verify(loanUserCollectionService, times(1)).getLoansByUserPanCard("SS1234TH"); 
    } 
 
    //  Test @PatchMapping("/{loanId}") - Update Loan Display Status 
    @Test 
    void testUpdateLoanDisplayStatus() throws Exception { 
        // Mock service to do nothing when updating display status 
        doNothing().when(loanUserCollectionService).updateLoanDisplayStatus("L0161", "Yes"); 
 
        // Perform PATCH request to update display status 
        mockMvc.perform(patch("/api/loanUser/L0161") 
                        .contentType(MediaType.APPLICATION_JSON)  // Define content type 
                        .content("{\"loan_display_status\": \"Yes\"}"))  // Send JSON request body 
                .andExpect(status().isOk())  // Expect HTTP 200 OK 
                .andExpect(content().string("Loan display status updated successfully!"));  // Validate response body 
 
        // Verify service method was called exactly once 
        verify(loanUserCollectionService, times(1)).updateLoanDisplayStatus("L0161", "Yes"); 
    } 
 
    //  Test @PatchMapping - Error Case (Missing "loan_display_status") 
    @Test 
    void testUpdateLoanDisplayStatus_MissingField() throws Exception { 
        // Perform PATCH request with incorrect JSON structure 
        mockMvc.perform(patch("/api/loanUser/L0161") 
                        .contentType(MediaType.APPLICATION_JSON) 
                        .content("{\"wrong_field\": \"Yes\"}"))  // Missing required field 
                .andExpect(status().isBadRequest())  // Expect HTTP 400 Bad Request 
                .andExpect(content().string("Missing required field: loan_display_status"));  // Validate error message 
    } 
 
    //  Test @GetMapping - No Loan Found 
    @Test 
    void testGetLoanUserCollectionByPanID_NoLoansFound() throws Exception { 
        // Mock service response to return an empty list 
        when(loanUserCollectionService.getLoansByUserPanCard("SS1234TH")).thenReturn(List.of()); 
 
        // Perform GET request and validate response 
        mockMvc.perform(get("/api/loanUser/SS1234TH")) 
                .andExpect(status().isNotFound());  // Expect HTTP 404 Not Found 
 
        // Verify service method was called exactly once 
        verify(loanUserCollectionService, times(1)).getLoansByUserPanCard("SS1234TH"); 
    } 
}