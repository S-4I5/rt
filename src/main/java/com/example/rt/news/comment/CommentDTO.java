package com.example.rt.news.comment;

import com.example.rt.news.News;
import com.example.rt.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private String author;
    private Long parent;
    private String message;
}
