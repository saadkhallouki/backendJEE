package com.example.JEE.controllers;

import com.example.JEE.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AuthenticationResponse {
    Integer id;
    private String token;
    private String name;
    private String lastName;
    private String email;
    private Role role;
}
