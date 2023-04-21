package com.example.rt.auth;

import com.example.rt.auth.requests.AuthenticationRequest;
import com.example.rt.auth.requests.EmailAuthenticationRequest;
import com.example.rt.auth.responses.registration.RegisterRequest;
import com.example.rt.auth.responses.ResponseBase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<ResponseBase> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @GetMapping("/authenticate")
    public ResponseEntity<ResponseBase> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PutMapping("/activate/{code}")
    public ResponseEntity<ResponseBase> activate(
            @PathVariable String code,
            @RequestBody EmailAuthenticationRequest request
    ){
        return ResponseEntity.ok(service.activate(code, request.email()));
    }
}
