package com.project.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "UserDetails")

public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name", nullable = false)
    private String username;

    @Column(name = "Email_Id", unique = true, nullable = false)
    private String email;

    @Column(name = "Password", unique = true)
    private String password;

    @Column(name = "Address")
    private String Address;

    @Column(name = "Pincode")
    private long pincode;

    @Column(name = "Mobile_No", nullable = false, unique = true)
    private String Mobile_no;

}
