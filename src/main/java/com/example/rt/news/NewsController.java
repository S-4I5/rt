package com.example.rt.news;

import com.example.rt.news.comment.Comment;
import com.example.rt.news.like.Like;
import com.example.rt.news.requests.CommentNewsRequest;
import com.example.rt.news.requests.PostNewsRequest;
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

    @GetMapping("/{id}/like")
    public ResponseEntity<List<Like>> getNewsLikes(@PathVariable long id) {
        return ResponseEntity.ok(newsService.getNewsLikes(id));
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<List<Like>> likeNews(@PathVariable long id) {
        return ResponseEntity.ok(newsService.addLikeNews(id));
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<List<Comment>> getNewsComments(@PathVariable long id) {
        return ResponseEntity.ok(newsService.getNewsComments(id));
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Comment> commentNews(@RequestBody CommentNewsRequest request, @PathVariable long id) {
        return ResponseEntity.ok(newsService.addNewsComments(request, id));
    }
}


