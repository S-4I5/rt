package com.example.rt.auth.responses;

import com.example.rt.data.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AuthenticationResponse {
    private String message;
    private String token;
    private long id;
    private Status status;
}
