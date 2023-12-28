package com.se.carrenting_backend.model.dto;

import lombok.Getter;

@Getter
public class SignupRequest {
    private String username;
    private String country;
    private String city;
    private String street;
    private String postCode;
    private String email;
    private String password;
}
