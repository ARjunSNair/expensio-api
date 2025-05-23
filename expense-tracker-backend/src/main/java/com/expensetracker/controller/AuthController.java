package com.expensetracker.controller;

import com.expensetracker.dto.UserRegistrationRequest;
import com.expensetracker.dto.UserLoginRequest;
import com.expensetracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserRegistrationRequest request) {
        userService.registerUser(request.getEmail(), request.getPassword());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/confirm")
    public ResponseEntity<Void> confirm(@RequestParam String token) {
        boolean result = userService.confirmUser(token);
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest request) {
        String jwt = userService.login(request.getEmail(), request.getPassword());
        if (jwt != null) {
            return ResponseEntity.ok(jwt);
        } else {
            return ResponseEntity.status(401).build();
        }
    }
} 