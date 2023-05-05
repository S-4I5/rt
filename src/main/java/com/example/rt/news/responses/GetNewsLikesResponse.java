package com.example.rt.news.responses;

import com.example.rt.data.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class GetNewsLikesResponse {
    private String message;
    private long amountOfLikes;
    private Status status;
}
