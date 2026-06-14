package com.astra.banking_system.config;

import com.astra.banking_system.enums.Role;
import com.astra.banking_system.model.User;
import com.astra.banking_system.repository.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
public class AdminConfig {

    private final UserRepo userRepo;

    public AdminConfig(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    private String name;

    private String email;

    private String password;

    private String cNic;

    @Bean
    public CommandLineRunner init(){
        return args -> {
            if (userRepo.findByEmail(email).isEmpty()){
            User admin = new User();
            admin.setUserName(name);
            admin.setEmail(email);
            admin.setRole(Role.ROLE_ADMIN);
            admin.setPassword(password);
            admin.setNicNo(cNic);
            userRepo.save(admin);
        }
    };
    }
}
