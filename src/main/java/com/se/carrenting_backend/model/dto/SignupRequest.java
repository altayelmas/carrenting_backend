package com.se.carrenting_backend.model.dto;

import lombok.Getter;

@Getter
public class SignupRequest {
    private String username;
    private String email;
    private String password;
}
