package com.se.carrenting_backend.service;

import com.se.carrenting_backend.model.User;
import com.se.carrenting_backend.model.dto.SignupRequest;
import com.se.carrenting_backend.model.dto.UserInfoDetails;
import com.se.carrenting_backend.model.dto.UserResponse;
import com.se.carrenting_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.map(UserInfoDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserResponse signup(SignupRequest signupRequest) {
        User user = User.builder()
                .email(signupRequest.getEmail())
                .username(signupRequest.getUsername())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .build();
        userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());
        return userResponse;
    }
}