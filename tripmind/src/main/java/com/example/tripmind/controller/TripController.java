package com.example.tripmind.controller;
import com.example.tripmind.dto.TripRequest;
import com.example.tripmind.model.Trip;
import com.example.tripmind.service.TripService;
import com.example.tripmind.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/trips")
public class TripController {
    @Autowired private TripService tripService;
    @Autowired private JwtUtil jwtUtil;
    private String getEmail(HttpServletRequest request) {
        return jwtUtil.extractEmail(request.getHeader("Authorization").replace("Bearer ", ""));
    }
    @PostMapping
    public ResponseEntity<Trip> createTrip(@RequestBody TripRequest req, HttpServletRequest request) {
        return ResponseEntity.ok(tripService.createTrip(getEmail(request), req));
    }
    @GetMapping
    public ResponseEntity<List<Trip>> getMyTrips(HttpServletRequest request) {
        return ResponseEntity.ok(tripService.getUserTrips(getEmail(request)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrip(@PathVariable String id) {
        tripService.deleteTrip(id); return ResponseEntity.ok("Deleted");
    }
}
