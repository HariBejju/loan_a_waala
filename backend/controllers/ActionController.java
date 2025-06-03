package com.example.sample_project.controller; 
 
import com.example.sample_project.entity.Actions; 
import com.example.sample_project.repository.ActionRepository; 
import com.example.sample_project.service.ActionService; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*; 
 
import java.util.List; 
import java.util.Map; 
import java.util.UUID; 
 
@CrossOrigin(origins = "http://localhost:5173") 
@RestController 
@RequestMapping(value = "/api/actions", method = RequestMethod.POST) 
public class ActionController { 
    /** 
     * Controller for handling user action logs. 
     * It connects to the ActionService to retrieve user actions 
     * and interacts with ActionRepository for database operations. 
     */ 
    @Autowired 
    private final ActionService actionService; 
 
    @Autowired 
    private ActionRepository actionRepository; 
 
    public ActionController(ActionService actionService) { 
        this.actionService = actionService; 
    } 
 
    /** 
     * Retrieves a sorted list of user actions based on user ID. 
     * 
     * @param requestBody Contains userId as input from request body. 
     * @return List of actions performed by the user. 
     */ 
    @PostMapping("/user-actions") 
    public List<Actions> getActionsSorted(@RequestBody Map<String, String> requestBody) { 
        return actionService.getActionsSorted(requestBody.get("userId")); 
    } 
 
 
}