package com.example.rt.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(value = "/news")
public class NewsController {
    @GetMapping("news")
    String getBasicNews() {
        return "a";
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


