package com.example.rt.news.comment;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CommentDTOMapper implements Function<Comment, CommentDTO> {
    @Override
    public CommentDTO apply(Comment comment) {
        long parentId = -1;
        if(comment.getParent() != null){
            parentId = comment.getParent().getId();
        }

        return new CommentDTO(
                comment.getId(),
                comment.getUser().getId(),
                comment.getUser().getFirstname() + comment.getUser().getLastname(),
                parentId,
                comment.getMessage()
        );
    }
}
