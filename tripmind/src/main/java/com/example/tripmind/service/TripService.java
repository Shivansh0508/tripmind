package com.example.tripmind.service;
import com.example.tripmind.dto.TripRequest;
import com.example.tripmind.model.Trip;
import com.example.tripmind.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class TripService {
    @Autowired private TripRepository tripRepository;
    @Autowired private AiService aiService;
    public Trip createTrip(String userId, TripRequest req) {
        Trip trip = new Trip();
        trip.setUserId(userId); trip.setCities(req.getCities());
        trip.setNumberOfDays(req.getNumberOfDays()); trip.setFamilySize(req.getFamilySize());
        trip.setBudgetLevel(req.getBudgetLevel()); trip.setFoodPreference(req.getFoodPreference());
        trip.setGeneratedItinerary(aiService.generateItinerary(req));
        return tripRepository.save(trip);
    }
    public List<Trip> getUserTrips(String userId) { return tripRepository.findByUserId(userId); }
    public void deleteTrip(String tripId) { tripRepository.deleteById(tripId); }
}
