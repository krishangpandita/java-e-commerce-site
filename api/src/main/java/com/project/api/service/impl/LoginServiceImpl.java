package com.project.api.service.impl;

import com.project.api.entity.UserLogin;
import com.project.api.repository.UserLoginRepository;
import com.project.api.request.LoginRequest;
import com.project.api.response.LoginResponse;
import com.project.api.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        UserLogin userLogin = userLoginRepository.findByEmail(loginRequest.getEmail());
        if (userLogin != null && userLogin.getPassword().equals(loginRequest.getPassword())) {
            return new LoginResponse("Login successful");
        } else {
            return new LoginResponse("Invalid email or password");
        }
    }
}
