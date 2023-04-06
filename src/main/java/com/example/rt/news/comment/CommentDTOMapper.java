package com.example.rt.news.comment;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CommentDTOMapper implements Function<Comment, CommentDTO> {
    @Override
    public CommentDTO apply(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getUser().getId(),
                comment.getUser().getFirstname() + comment.getUser().getLastname(),
                comment.getParent().getId(),
                comment.getMessage()
        );
    }
}
