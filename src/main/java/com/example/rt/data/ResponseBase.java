package com.example.rt.data;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class ResponseBase {
    private Status status;
    private String message;
}
