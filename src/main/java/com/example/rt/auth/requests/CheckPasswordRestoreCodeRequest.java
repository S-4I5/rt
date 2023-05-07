package com.example.rt.auth.requests;

public record CheckPasswordRestoreCodeRequest(
        String email
) {
}
