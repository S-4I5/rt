package com.example.rt.auth.responses;

import com.example.rt.data.Status;

public record CreatePasswordRestoreCodeResponse(
        String message,
        Status status
){}
