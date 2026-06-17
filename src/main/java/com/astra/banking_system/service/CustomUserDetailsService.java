package com.astra.banking_system.service;

import com.astra.banking_system.model.User;
import com.astra.banking_system.repository.UserRepo;
import com.astra.banking_system.userPrincipal.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email).orElseThrow(
                ()-> new RuntimeException("No User Found")
        );
        return new UserPrincipal(user);
    }
}
