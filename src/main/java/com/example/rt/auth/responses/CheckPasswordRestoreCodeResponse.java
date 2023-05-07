package com.example.rt.auth.responses;

import com.example.rt.data.Status;

public record CheckPasswordRestoreCodeResponse(
        String message,
        Status status
){}
