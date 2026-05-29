package com.example.tripmind.service;
import com.example.tripmind.dto.TripRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;
@Service
public class AiService {
    @Value("${openrouter.api.key}") private String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();
    @Cacheable(value = "itineraries", key = "#req.cities + '_' + #req.numberOfDays + '_' + #req.budgetLevel")
    public String generateItinerary(TripRequest req) {
        String prompt = String.format("Create a detailed %d-day travel itinerary for %s. Family of %d people. Budget level: %s. Food preference: %s. For each day provide: morning activity, lunch spot, afternoon activity, dinner restaurant, hotel suggestion. Include approximate costs in INR.", req.getNumberOfDays(), String.join(" and ", req.getCities()), req.getFamilySize(), req.getBudgetLevel(), req.getFoodPreference());
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("HTTP-Referer", "http://localhost:8080");
        headers.set("X-Title", "TripMind");
        Map<String, Object> body = Map.of("model", "openrouter/auto", "messages", List.of(Map.of("role", "user", "content", prompt)));
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity("https://openrouter.ai/api/v1/chat/completions", entity, Map.class);
            List choices = (List) response.getBody().get("choices");
            Map choice = (Map) choices.get(0);
            Map message = (Map) choice.get("message");
            return (String) message.get("content");
        } catch (Exception e) { return "Could not generate itinerary: " + e.getMessage(); }
    }
}
