package com.example.rt.auth.responses;

import com.example.rt.data.ResponseBase;
import com.example.rt.data.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PasswordResetResponse extends ResponseBase {
    private String token;
}
