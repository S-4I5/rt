package com.example.rt.auth.responses;

import com.example.rt.data.ResponseBase;
import com.example.rt.data.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class RegistrationResponse extends ResponseBase {
}
