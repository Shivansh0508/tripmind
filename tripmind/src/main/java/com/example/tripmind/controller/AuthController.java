package com.example.tripmind.controller;
import com.example.tripmind.dto.LoginRequest;
import com.example.tripmind.dto.RegisterRequest;
import com.example.tripmind.model.User;
import com.example.tripmind.repository.UserRepository;
import com.example.tripmind.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) return ResponseEntity.badRequest().body("Email already exists");
        User user = new User();
        user.setName(req.getName()); user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail()).orElse(null);
        if (user == null) return ResponseEntity.status(401).body("User not found");
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) return ResponseEntity.status(401).body("Wrong password");
        return ResponseEntity.ok(Map.of("token", jwtUtil.generateToken(user.getEmail()), "name", user.getName(), "email", user.getEmail()));
    }
}
