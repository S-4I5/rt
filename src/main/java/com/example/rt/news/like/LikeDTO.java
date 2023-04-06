package com.example.rt.news.like;

import java.util.function.Function;

public record LikeDTO(
        Long id,
        Long newsId,
        Long userId
){

}
