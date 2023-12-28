package com.se.carrenting_backend.controller;

import com.se.carrenting_backend.model.dto.LoginRequest;
import com.se.carrenting_backend.model.dto.LoginResponse;
import com.se.carrenting_backend.model.dto.SignupRequest;
import com.se.carrenting_backend.model.dto.UserResponse;
import com.se.carrenting_backend.service.JwtService;
import com.se.carrenting_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:3000")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
        /*String token = jwtService.generateToken(loginRequest.getUsername());
        return ResponseEntity.ok(token);*/
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(loginRequest.getUsername());
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setAuthToken(token);
            return ResponseEntity.ok(loginResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            // TODO - Implement new exception
            //throw new Exception("asd");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok(userService.signup(signupRequest));
    }
}
