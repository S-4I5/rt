package com.example.rt.auth.responses.registration;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
