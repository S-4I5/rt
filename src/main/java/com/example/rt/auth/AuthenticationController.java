package com.example.rt.auth;

import com.example.rt.auth.requests.*;
import com.example.rt.auth.responses.*;
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

    @PutMapping("/restore/{code}")
    public ResponseEntity<PasswordResetResponse> restorePassword(
            @PathVariable String code,
            @RequestBody PasswordResetRequest request
            ){
        return ResponseEntity.ok(service.restorePassword(code, request));
    }

    @GetMapping("/restore/check/{code}")
    public ResponseEntity<CheckPasswordRestoreCodeResponse> checkPasswordRestoreCode(
            @PathVariable String code,
            @RequestBody CheckPasswordRestoreCodeRequest request
    ){
        return ResponseEntity.ok(service.checkPasswordRestoreCode(code, request));
    }

    @GetMapping("/restore/{email}")
    public ResponseEntity<CreatePasswordRestoreCodeResponse> createPasswordRestoreRequest(
            @PathVariable String email
    ){
        return ResponseEntity.ok(service.sendPasswordRestoreCode(email));
    }
}
