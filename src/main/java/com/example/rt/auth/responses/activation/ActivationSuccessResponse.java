package com.example.rt.auth.responses.activation;

import com.example.rt.auth.responses.ResponseBase;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActivationSuccessResponse extends ResponseBase {
    private String message;
}
