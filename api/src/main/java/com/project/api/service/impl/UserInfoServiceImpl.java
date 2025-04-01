package com.project.api.service.impl;

import com.project.api.entity.UserInfo;
import com.project.api.entity.UserLogin;
import com.project.api.repository.UserInfoRepository;
import com.project.api.repository.UserLoginRepository;
import com.project.api.request.UserInfoRequest;
import com.project.api.response.UserInfoResponse;
import com.project.api.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;


    @Autowired
    private UserLoginRepository userLoginRepository;

    @Override
    public UserInfoResponse registerUser(@NotNull UserInfoRequest userInfoRequest) {
        if (existsByEmail_id(userInfoRequest.getEmail())) {
            throw new IllegalArgumentException("Email is already in use.");
        }
        if (existsByPassword(userInfoRequest.getPassword())) {
            throw new IllegalArgumentException("Password is already in use.");
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(userInfoRequest.getUsername());
        userInfo.setEmail(userInfoRequest.getEmail());
        userInfo.setPassword(userInfoRequest.getPassword());
        userInfo.setAddress(userInfoRequest.getAddress());
        userInfo.setPincode(userInfoRequest.getPincode());
        userInfo.setMobile_no(userInfoRequest.getMobile_no());

        UserInfo savedUser = userInfoRepository.save(userInfo);

        UserLogin userLogin = new UserLogin();
        userLogin.setEmail(savedUser.getEmail());
        userLogin.setPassword(savedUser.getPassword());
        userLoginRepository.save(userLogin);

        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setId(savedUser.getId());
        userInfoResponse.setUsername(savedUser.getUsername());
        userInfoResponse.setEmail(savedUser.getEmail());
        userInfoResponse.setAddress(savedUser.getAddress());
        userInfoResponse.setPincode(savedUser.getPincode());
        userInfoResponse.setMobile_no(savedUser.getMobile_no());

        return userInfoResponse;
    }

    @Override
    public boolean existsByEmail_id(String email_Id) {
        return userInfoRepository.existsByEmail(email_Id);
    }

    @Override
    public boolean existsByPassword(String password) {
        return userInfoRepository.existsByPassword(password);
    }
}
