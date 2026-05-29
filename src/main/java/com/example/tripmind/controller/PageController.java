package com.example.tripmind.controller;
import com.example.tripmind.dto.TripRequest;
import com.example.tripmind.model.Trip;
import com.example.tripmind.model.User;
import com.example.tripmind.repository.TripRepository;
import com.example.tripmind.repository.UserRepository;
import com.example.tripmind.service.TripService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Controller
public class PageController {
    @Autowired private TripService tripService;
    @Autowired private UserRepository userRepository;
    @Autowired private TripRepository tripRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @GetMapping("/") public String index() { return "index"; }
    @GetMapping("/register") public String registerPage() { return "register"; }
    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String email, @RequestParam String password, Model model) {
        if (userRepository.existsByEmail(email)) { model.addAttribute("error", "Email already registered."); return "register"; }
        User user = new User();
        user.setName(name); user.setEmail(email); user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        model.addAttribute("success", "Account created! Please sign in."); return "login";
    }
    @GetMapping("/login") public String loginPage() { return "login"; }
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) { model.addAttribute("error", "Invalid email or password."); return "login"; }
        session.setAttribute("userEmail", email); session.setAttribute("userName", user.getName());
        return "redirect:/plan";
    }
    @GetMapping("/logout") public String logout(HttpSession session) { session.invalidate(); return "redirect:/"; }
    @GetMapping("/plan") public String planPage(HttpSession session) { if (session.getAttribute("userEmail") == null) return "redirect:/login"; return "plan"; }
    @PostMapping("/plan")
    public String submitPlan(@RequestParam String cities, @RequestParam int numberOfDays, @RequestParam int familySize, @RequestParam String budgetLevel, @RequestParam String foodPreference, HttpSession session, Model model) {
        if (session.getAttribute("userEmail") == null) return "redirect:/login";
        TripRequest req = new TripRequest();
        req.setCities(Arrays.stream(cities.split(",")).map(String::trim).collect(Collectors.toList()));
        req.setNumberOfDays(numberOfDays); req.setFamilySize(familySize);
        req.setBudgetLevel(budgetLevel); req.setFoodPreference(foodPreference);
        Trip trip = tripService.createTrip((String) session.getAttribute("userEmail"), req);
        model.addAttribute("trip", trip); return "result";
    }
    @GetMapping("/my-trips")
    public String myTrips(HttpSession session, Model model) {
        if (session.getAttribute("userEmail") == null) return "redirect:/login";
        model.addAttribute("trips", tripRepository.findByUserId((String) session.getAttribute("userEmail")));
        return "mytrips";
    }
    @GetMapping("/trips/{id}")
    public String viewTrip(@PathVariable String id, Model model, HttpSession session) {
        if (session.getAttribute("userEmail") == null) return "redirect:/login";
        Trip trip = tripRepository.findById(id).orElse(null);
        if (trip == null) return "redirect:/my-trips";
        model.addAttribute("trip", trip); return "result";
    }
    @PostMapping("/trips/{id}/delete")
    public String deleteTrip(@PathVariable String id, HttpSession session) {
        if (session.getAttribute("userEmail") == null) return "redirect:/login";
        tripRepository.deleteById(id); return "redirect:/my-trips";
    }
}
