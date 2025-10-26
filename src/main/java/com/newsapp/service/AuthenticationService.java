package com.newsapp.service;

import com.newsapp.config.JwtUtil;
import com.newsapp.dto.*;
import com.newsapp.model.Role;
import com.newsapp.model.User;
import com.newsapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

//@Slf4j
//@Service
//@RequiredArgsConstructor
public class AuthenticationService {

//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtUtil jwtService;
//    private final AuthenticationManager authenticationManager;
//
//    public RegisterResponse register(RegisterRequest request) {
//        if (userRepository.existsByEmail(request.getEmail())) {
//            throw new RuntimeException("Email already exists");
//        }
//
//        var user = User.builder()
//                .name(request.getName())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
////                .role(Role.USER)
//                .build();
//
//        userRepository.save(user);
//
//        return RegisterResponse.builder()
//                .email(user.getEmail())
//                .name(user.getName())
////                .role(user.getRole().name())
//                .build();
//    }
//
//    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        log.info("Attempting to authenticate user: {}", request.getEmail());
//
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            request.getEmail(),
//                            request.getPassword()
//                    )
//            );
//            log.info("User authenticated successfully: {}", request.getEmail());
//        } catch (Exception e) {
//            log.error("Authentication failed for user: {}", request.getEmail(), e);
//            throw e;
//        }
//
//        var user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        UserDetails userDetails = new UserDetails() {
//            @Override
//            public Collection<? extends GrantedAuthority> getAuthorities() {
//                return List.of();
//            }
//
//            @Override
//            public String getPassword() {
//                return "";
//            }
//
//            @Override
//            public String getUsername() {
//                return "";
//            }
//        }
//
//        var jwtToken = jwtService.generateToken(userDetails);
//
//        Meta meta = new Meta();
//        meta.setStatus(true);
//        meta.setMessage("success");
//
//        long expiresAt = System.currentTimeMillis() + jwtService.getExpirationTime();
//
//        return AuthenticationResponse.builder()
//                .meta(meta)
//                .access_token(jwtToken)
//                .expired_at(expiresAt)
////                .role(user.getRole().name())
//                .build();
//    }
}