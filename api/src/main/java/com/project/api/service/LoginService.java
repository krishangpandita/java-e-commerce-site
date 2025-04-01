package com.project.api.service;

import com.project.api.request.LoginRequest;
import com.project.api.response.LoginResponse;

public interface LoginService {
    LoginResponse loginUser(LoginRequest loginRequest);
}
