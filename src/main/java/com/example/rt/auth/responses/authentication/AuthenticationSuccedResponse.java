package com.example.rt.auth.responses.authentication;

import com.example.rt.auth.responses.ResponseBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class AuthenticationSuccedResponse extends ResponseBase {
    private String token;
    private long id;
}
