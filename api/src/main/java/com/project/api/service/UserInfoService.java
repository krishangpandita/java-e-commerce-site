package com.project.api.service;

import com.project.api.request.UserInfoRequest;
import com.project.api.response.UserInfoResponse;

public interface UserInfoService {
    UserInfoResponse registerUser(UserInfoRequest userInfoRequest);
    boolean existsByEmail_id(String email);
    boolean existsByPassword(String password);
}
