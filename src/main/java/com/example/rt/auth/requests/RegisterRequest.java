package com.example.rt.auth.requests;

public record RegisterRequest(
        String email,
        String password
) {
}
