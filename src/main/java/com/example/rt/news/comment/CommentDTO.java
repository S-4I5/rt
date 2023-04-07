package com.example.rt.news.comment;

public record CommentDTO(
     Long id,
     Long authorId,
     String authorName,
     Long parent,
     String message
){

}
