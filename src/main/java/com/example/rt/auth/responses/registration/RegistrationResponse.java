package com.example.rt.auth.responses.registration;

import com.example.rt.auth.responses.ResponseBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegistrationResponse extends ResponseBase {
    private String token;
}
