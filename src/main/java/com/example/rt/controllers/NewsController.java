package com.example.rt.controllers;

import com.example.rt.repository.NewsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(value = "/news")
public class NewsController {
    private NewsRepository newsRepository;

    public NewsController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @GetMapping("news")
    String getBasicNews() {
        return newsRepository.getAll().toString();
    }

    @GetMapping("photo-reports")
    void getBasicEventPhotoReports() {

    }
    @PostMapping("news")
    void postNews() {

    }
    @PostMapping("photo-reports")
    void postPhotoReport() {

    }
}


