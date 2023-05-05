package com.example.rt.auth;

import com.example.rt.auth.requests.AuthenticationRequest;
import com.example.rt.auth.requests.EmailAuthenticationRequest;
import com.example.rt.auth.responses.ActivationResponse;
import com.example.rt.auth.responses.AuthenticationResponse;
import com.example.rt.auth.requests.RegisterRequest;
import com.example.rt.auth.responses.RegistrationResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @GetMapping("/authenticate")
    public ResponseEntity<ActivationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PutMapping("/activate/{code}")
    public ResponseEntity<AuthenticationResponse> activate(
            @PathVariable String code,
            @RequestBody EmailAuthenticationRequest request
    ){
        return ResponseEntity.ok(service.activate(code, request.email()));
    }
}
