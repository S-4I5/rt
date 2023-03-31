package com.example.rt.news;

import com.example.rt.news.comment.Comment;
import com.example.rt.news.comment.CommentRepository;
import com.example.rt.news.like.Like;
import com.example.rt.news.like.LikeRepository;
import com.example.rt.news.requests.CommentNewsRequest;
import com.example.rt.news.requests.PostNewsRequest;
import com.example.rt.user.User;
import com.example.rt.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public News postNews(PostNewsRequest request) {
        var newNews = News.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .photo(request.getPhoto())
                .date(new Date(System.currentTimeMillis()))
                .build();

        newsRepository.save(newNews);

        return newNews;
    }

    public List<Like> getNewsLikes(long id) {
        if (newsRepository.findById(id).isEmpty()) {
            return null;
        }

        return likeRepository.findAllByNews(newsRepository.findById(id).get());
    }

    public List<Like> addLikeNews(long id) {
        if (newsRepository.findById(id).isEmpty()) {
            return null;
        }

        var newLike = Like.builder()
                .news(newsRepository.findById(id).get())
                .build();

        likeRepository.save(newLike);

        return likeRepository.findAllByNews(newsRepository.findById(id).get());
    }

    public List<Comment> getNewsComments(long id) {
        if (newsRepository.findById(id).isEmpty()) {
            return null;
        }

        return commentRepository.findAllByNews(newsRepository.findById(id).get());
    }

    public Comment addNewsComments(CommentNewsRequest request, long id) {
        if (newsRepository.findById(id).isEmpty() ||
                userRepository.findByEmail(request.getEmail()).isEmpty()) {
            return null;
        }

        var newComment = Comment.builder()
                .message(request.getMessage())
                .user(userRepository.findByEmail(request.getEmail()).get())
                .news(newsRepository.findById(id).get())
                .build();

        if(commentRepository.findById(request.getParentId()).isPresent()){
            newComment.setParent(commentRepository.findById(request.getParentId()).get());
        }

        commentRepository.save(newComment);

        return newComment;
    }

}
