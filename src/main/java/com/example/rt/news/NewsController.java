package com.example.rt.news;

import com.example.rt.news.comment.CommentDTO;
import com.example.rt.news.like.LikeDTO;
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
    public ResponseEntity<List<News>> getAllNews(
            @RequestParam(value = "pageNo", required = false) int pageNo,
            @RequestParam(value = "pageSize", required = false) int pageSize
    ) {
        return ResponseEntity.ok(newsService.getAllNews(pageNo, pageSize));
    }

    @PostMapping()
    public ResponseEntity<News> postNews(@RequestBody PostNewsRequest request) {
        return ResponseEntity.ok(newsService.postNews(request));
    }

    @GetMapping("/{id}/likes")
    public ResponseEntity<List<LikeDTO>> getNewsLikes(@PathVariable long id) {
        return ResponseEntity.ok(newsService.getNewsLikes(id));
    }

    @PostMapping("/{id}/likes")
    public ResponseEntity<LikeDTO> likeNews(@PathVariable long id) {
        return ResponseEntity.ok(newsService.addLikeNews(id));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDTO>> getNewsComments(
            @PathVariable long id,
            @RequestParam(value = "pageNo", required = false) int pageNo,
            @RequestParam(value = "pageSize", required = false) int pageSize
    ) {
        return ResponseEntity.ok(newsService.getNewsComments(id, pageNo, pageSize));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDTO> commentNews(@RequestBody CommentNewsRequest request, @PathVariable long id) {
        return ResponseEntity.ok(newsService.addNewsComments(request, id));
    }
}


