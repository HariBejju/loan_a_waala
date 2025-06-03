package com.example.sample_project.controller; 
 
import com.example.sample_project.entity.Repayment; 
import com.example.sample_project.service.RepaymentService; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith; 
import org.mockito.InjectMocks; 
import org.mockito.Mock; 
import org.mockito.MockitoAnnotations; 
import org.mockito.junit.jupiter.MockitoExtension; 
import org.springframework.http.MediaType; 
import org.springframework.test.web.servlet.MockMvc; 
import org.springframework.test.web.servlet.setup.MockMvcBuilders; 
 
import java.util.List; 
import java.util.Optional; 
 
import static org.mockito.Mockito.*; 
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; 
 
@ExtendWith(MockitoExtension.class)  // Enables Mockito support for JUnit 5 
public class RepaymentControllerTest { 
 
    private MockMvc mockMvc;  // MockMvc to simulate HTTP requests to the controller 
 
    @InjectMocks 
    private RepaymentController repaymentController;  // Inject mocks into RepaymentController 
 
    @Mock 
    private RepaymentService repaymentService;  // Mock service layer for repayment operations 
 
    @BeforeEach 
    void setUp() { 
        // Initialize Mockito and MockMvc before each test case 
        MockitoAnnotations.openMocks(this); 
        mockMvc = MockMvcBuilders.standaloneSetup(repaymentController).build(); 
    } 
 
    //  Test @GetMapping("/repayments") - Retrieve all repayments successfully 
    @Test 
    public void testGetAllRepayments() throws Exception { 
        System.out.println("Executing testGetAllRepayments..."); 
 
        // Mock service response to return a list with one repayment entry 
        when(repaymentService.getAllRepayments()).thenReturn(List.of(new Repayment())); 
 
        // Perform GET request and validate response 
        mockMvc.perform(get("/repayments") 
                        .contentType(MediaType.APPLICATION_JSON)) 
                .andExpect(status().isOk());  // Expect HTTP 200 OK 
 
        System.out.println("testGetAllRepayments completed successfully!"); 
    } 
 
    //  Test @GetMapping("/repayments/{repaymentId}") - Retrieve repayment by ID 
    @Test 
    public void testGetRepaymentById() throws Exception { 
        String repaymentId = "R123"; 
        System.out.println("Executing testGetRepaymentById for repaymentId: " + repaymentId); 
 
        // Mock service response returning a repayment object for the ID 
        Repayment repayment = new Repayment(); 
        when(repaymentService.getRepaymentById(repaymentId)).thenReturn(repayment); 
 
        // Perform GET request and validate response 
        mockMvc.perform(get("/repayments/" + repaymentId) 
                        .contentType(MediaType.APPLICATION_JSON)) 
                .andExpect(status().isOk());  // Expect HTTP 200 OK 
 
        System.out.println("testGetRepaymentById completed successfully!"); 
    } 
 
    //  Test @PostMapping("/repayments") - Save a new repayment 
    @Test 
    public void testSaveRepayment() throws Exception { 
        System.out.println("Executing testSaveRepayment..."); 
 
        // Mock service response returning saved repayment object 
        Repayment repayment = new Repayment(); 
        when(repaymentService.saveRepayment(any(Repayment.class))).thenReturn(repayment); 
 
        // Perform POST request with empty JSON payload 
        mockMvc.perform(post("/repayments") 
                        .content("{}") 
                        .contentType(MediaType.APPLICATION_JSON)) 
                .andExpect(status().isOk());  // Expect HTTP 200 OK 
 
        System.out.println("testSaveRepayment completed successfully!"); 
    } 
 
    //  Test @GetMapping("/repayments/loan/{loanId}") - Get repayments by loan ID 
    @Test 
    public void testGetRepaymentsByLoanId() throws Exception { 
        String loanId = "L123"; 
        System.out.println("Executing testGetRepaymentsByLoanId for loanId: " + loanId); 
 
        // Mock service response returning list of repayments for loan ID 
        when(repaymentService.getRepaymentsByLoanId(loanId)).thenReturn(List.of(new Repayment())); 
 
        // Perform GET request and validate response 
        mockMvc.perform(get("/repayments/loan/" + loanId) 
                        .contentType(MediaType.APPLICATION_JSON)) 
                .andExpect(status().isOk());  // Expect HTTP 200 OK 
 
        System.out.println("testGetRepaymentsByLoanId completed successfully!"); 
    } 
 
    //  Test @DeleteMapping("/repayments/{repaymentId}") - Delete repayment successfully 
    @Test 
    public void testDeleteRepayment() throws Exception { 
        String repaymentId = "R123"; 
        System.out.println("Executing testDeleteRepayment for repaymentId: " + repaymentId); 
 
        // Mock service response where delete operation completes successfully 
        doNothing().when(repaymentService).deleteRepayment(repaymentId); 
 
        // Perform DELETE request and validate response 
        mockMvc.perform(delete("/repayments/" + repaymentId) 
                        .contentType(MediaType.APPLICATION_JSON)) 
                .andExpect(status().isOk());  // Expect HTTP 200 OK 
 
        System.out.println("testDeleteRepayment completed successfully!"); 
    } 
 
    //  Test @GetMapping("/repayments/latest/{loanId}") - Retrieve latest repayment by loan ID 
    @Test 
    public void testGetLatestRepaymentByLoanId() throws Exception { 
        String loanId = "L123"; 
        System.out.println("Executing testGetLatestRepaymentByLoanId for loanId: " + loanId); 
 
        // Mock service response returning the latest repayment for the loan ID 
        when(repaymentService.getLatestRepaymentByLoanId(loanId)).thenReturn(Optional.of(new Repayment())); 
 
        // Perform GET request and validate response 
        mockMvc.perform(get("/repayments/latest/" + loanId) 
                        .contentType(MediaType.APPLICATION_JSON)) 
                .andExpect(status().isOk());  // Expect HTTP 200 OK 
 
        System.out.println("testGetLatestRepaymentByLoanId completed successfully!"); 
    } 
}