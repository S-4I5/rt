package com.example.rt.user.request;

import com.example.rt.data.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class GetTokensResponse {
    private long amountOfTokens;
    private String message;
    private Status status;
}
