package com.example.rt.news.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostNewsRequest {
    private String title;
    private String description;
    private String photo;
}
