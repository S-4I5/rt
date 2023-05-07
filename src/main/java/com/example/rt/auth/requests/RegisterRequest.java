package com.example.rt.auth.requests;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
}
