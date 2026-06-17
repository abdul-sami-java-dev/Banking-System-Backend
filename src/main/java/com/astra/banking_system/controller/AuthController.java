package com.astra.banking_system.controller;

import com.astra.banking_system.dto.AuthResponse;
import com.astra.banking_system.dto.LoginRequest;
import com.astra.banking_system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        AuthResponse authResponse = userService.login(request);
        return ResponseEntity.ok(authResponse);
    }
}
