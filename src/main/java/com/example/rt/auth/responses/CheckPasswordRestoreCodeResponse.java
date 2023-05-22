package com.example.rt.auth.responses;

import com.example.rt.data.ResponseBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CheckPasswordRestoreCodeResponse extends ResponseBase{
}
