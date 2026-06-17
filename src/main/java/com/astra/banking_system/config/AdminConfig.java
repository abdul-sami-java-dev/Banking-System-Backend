package com.astra.banking_system.config;

import com.astra.banking_system.enums.Role;
import com.astra.banking_system.model.User;
import com.astra.banking_system.repository.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Configuration
public class AdminConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    public AdminConfig(PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }


    @Value("${admin.name}")
    private String name;

    @Value("${admin.email}")
    private String email;

    @Value("${admin.password}")
    private String password;


    @Bean
    public CommandLineRunner init(){
        return args -> {
            if (userRepo.findByEmail(email).isEmpty()){
            User admin = new User();
            admin.setUserName(name);
            admin.setEmail(email);
            admin.setRole(Role.ROLE_ADMIN);
            admin.setPassword(passwordEncoder.encode(password));
            admin.setNicNo("N/A");
            userRepo.save(admin);
        }
    };
    }
}
