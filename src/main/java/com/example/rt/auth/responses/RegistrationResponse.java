package com.example.rt.auth.responses;

import com.example.rt.data.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RegistrationResponse {
    private String message;
    private Status status;
}
