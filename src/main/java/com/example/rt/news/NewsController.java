package com.example.rt.news;

import com.example.rt.auth.requests.RegisterRequest;
import com.example.rt.auth.responses.ResponseBase;
import com.example.rt.auth.responses.authentication.AuthenticationFailedResponse;
import com.example.rt.auth.responses.authentication.AuthenticationSuccedResponse;
import com.example.rt.news.request.PostNewsRequest;
import com.example.rt.user.Role;
import com.example.rt.user.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/news")
public class NewsController {
    private final NewsService newsService;

    @GetMapping()
    public ResponseEntity<List<News>> getBasicNews() {
        return ResponseEntity.ok(newsService.getAllNews());
    }

    @PostMapping()
    public ResponseEntity<News> postNews(@RequestBody PostNewsRequest request) {
        return ResponseEntity.ok(newsService.postNews(request));
    }
}


