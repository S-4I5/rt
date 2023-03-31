package com.example.rt.news.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostNewsRequest {
    private String title;
    private String description;
    private String photo;
}
