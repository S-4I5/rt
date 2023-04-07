package com.example.rt.news.like;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class LikeDTOMapper implements Function<Like, LikeDTO> {
    @Override
    public LikeDTO apply(Like like) {
        return new LikeDTO(
                like.getId(),
                like.getNews().getId(),
                like.getUser().getId()
        );
    }
}
