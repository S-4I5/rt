package com.example.rt.news;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NewsRepository {
    public List<String> getAll() {
        return List.of("birthday", "b");
    }
}
