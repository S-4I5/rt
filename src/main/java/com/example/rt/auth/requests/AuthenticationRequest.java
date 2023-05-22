package com.example.rt.auth.requests;

public record AuthenticationRequest(
        String email,
        String password
) {
}
