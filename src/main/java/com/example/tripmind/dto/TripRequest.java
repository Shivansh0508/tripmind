package com.example.tripmind.dto;
import lombok.Data;
import java.util.List;
@Data
public class TripRequest {
    private List<String> cities;
    private int numberOfDays;
    private int familySize;
    private String budgetLevel;
    private String foodPreference;
}
