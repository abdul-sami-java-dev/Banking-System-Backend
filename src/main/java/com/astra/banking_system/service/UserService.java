package com.astra.banking_system.service;

import com.astra.banking_system.dto.AuthResponse;
import com.astra.banking_system.dto.LoginRequest;
import com.astra.banking_system.dto.RegisterRequest;
import com.astra.banking_system.enums.Role;
import com.astra.banking_system.model.User;
import com.astra.banking_system.repository.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserRepo userRepo;

    public UserService(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, UserDetailsService userDetailsService, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userRepo = userRepo;
    }

    public User register(RegisterRequest request){
        User user = new User();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setNicNo(request.getNicNo());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);
        return userRepo.save(user);
    }

    public AuthResponse login(LoginRequest request){
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        UserDetails user = (UserDetails) auth.getPrincipal();
        String token = jwtService.genToken(user);
        return new AuthResponse(token);
    }
}
