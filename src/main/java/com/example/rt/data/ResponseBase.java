package com.example.rt.data;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class ResponseBase {
    private Status status;
    private String message;
}
