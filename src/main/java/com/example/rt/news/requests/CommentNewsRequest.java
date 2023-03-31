package com.example.rt.news.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentNewsRequest {
    private String email;
    private String message;
    private long parentId;
}
