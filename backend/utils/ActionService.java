package com.example.sample_project.service; 
 
import com.example.sample_project.entity.Actions; 
import com.example.sample_project.repository.ActionRepository; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
 
import java.util.List; 
 
@Service 
public class ActionService { 
 
    @Autowired 
    private ActionRepository actionRepository; 
 
    /** 
     * Retrieves all actions for a specific user, sorted in descending order by timestamp. 
     * Ensures that the latest actions appear first. 
     * 
     * @param userId The ID of the user whose actions are being retrieved. 
     * @return A list of actions sorted by timestamp. 
     */ 
    public List<Actions> getActionsSorted(String userId) { 
        return actionRepository.findAllByActionUserIdOrderByActionTimestampDesc(userId); 
    } 
}