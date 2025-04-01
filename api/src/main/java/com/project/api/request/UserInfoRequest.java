package com.project.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoRequest {
    private String username;
    private String email;
    private String password;
    private String Address;
    private long pincode;
    private String Mobile_no;

}