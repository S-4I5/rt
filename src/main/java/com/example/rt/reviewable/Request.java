package com.example.rt.reviewable;

import lombok.Data;

@Data
public class Request<B extends Body> {
    private B body;
    private long authorId;
}
