package com.example.tripmind.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
@Document(collection = "trips")
@Data @NoArgsConstructor @AllArgsConstructor
public class Trip {
    @Id private String id;
    private String userId;
    private List<String> cities;
    private int numberOfDays;
    private int familySize;
    private String budgetLevel;
    private String foodPreference;
    private String generatedItinerary;
    private LocalDateTime createdAt = LocalDateTime.now();
}
