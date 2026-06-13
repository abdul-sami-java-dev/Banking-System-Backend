package com.astra.banking_system.service;

import com.astra.banking_system.dto.RegisterRequest;
import com.astra.banking_system.model.User;
import com.astra.banking_system.repository.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User register(RegisterRequest request){
        User user = new User();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setNicNo(request.getNicNo());
        user.setPassword(request.getPassword());
        return userRepo.save(user);
    }
}
