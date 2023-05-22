package com.example.rt.auth.requests;

public record PasswordResetRequest(
        String email,
        String newPassword
) {
}
