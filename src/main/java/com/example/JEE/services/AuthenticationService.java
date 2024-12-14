package com.example.JEE.services;

import com.example.JEE.config.JwtService;
import com.example.JEE.controllers.AuthenticationRequest;
import com.example.JEE.controllers.AuthenticationResponse;
import com.example.JEE.controllers.RegisterRequest;
import com.example.JEE.entities.Role;
import com.example.JEE.entities.User;
import com.example.JEE.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repos;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public String register(RegisterRequest request) {
        Optional<User> optionalUser = repos.findUtilisateurByEmail(request.getEmail());

        if (!optionalUser.isPresent()){
        var user= User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode( request.getPassword()))
                .role(Role.User)
                .build();
        repos.save(user);
        var jwtToken =  jwtService.generateToken(user);
        return "email has been created successfully";}
        else {
            return "email already exist";
        }
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repos.findUtilisateurByEmail(request.getEmail()).orElseThrow();
        var jwtToken =  jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .name(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
    public String  changePassword(AuthenticationRequest req) {
        Optional<User> optionalUser = repos.findUtilisateurByEmail(req.getEmail());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(req.getPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(req.getNewPassword()));
                repos.save(user);
                return "password changed";
            }else {
                return "password incorrect";
            }
            }else {
            throw new IllegalArgumentException("User with email " + req.getEmail() + " not found");
        }
            // Update the user's password with the new encoded password

    }


}
