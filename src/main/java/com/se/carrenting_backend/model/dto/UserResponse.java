package com.se.carrenting_backend.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private String username;
    private boolean isSuccess;
    private String message;
}
