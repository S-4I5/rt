package com.example.rt.news;

import com.example.rt.data.Status;
import com.example.rt.news.comment.Comment;
import com.example.rt.news.comment.CommentDTO;
import com.example.rt.news.comment.CommentDTOMapper;
import com.example.rt.news.comment.CommentRepository;
import com.example.rt.news.like.Like;
import com.example.rt.news.like.LikeDTO;
import com.example.rt.news.like.LikeDTOMapper;
import com.example.rt.news.like.LikeRepository;
import com.example.rt.news.requests.CommentNewsRequest;
import com.example.rt.news.requests.PostNewsRequest;
import com.example.rt.news.responses.GetNewsLikesResponse;
import com.example.rt.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final CommentDTOMapper commentDTOMapper;
    private final LikeDTOMapper likeDTOMapper;

    public List<News> getAllNews(int pageNo, int pageSize) {
        Page<News> newsPage = newsRepository.findAll(PageRequest.of(pageNo, pageSize));

        return newsPage.getContent();
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

    public List<LikeDTO> getNewsLikes(long id) {
        if (newsRepository.findById(id).isEmpty()) {
            return null;
        }

        return likeRepository.findAllByNews(newsRepository.findById(id).get())
                .stream().map(likeDTOMapper)
                .collect(Collectors.toList());
    }

    public GetNewsLikesResponse getAmountOfLikesOnNews(long id) {
        if (newsRepository.findById(id).isEmpty()) {
            return GetNewsLikesResponse.builder()
                    .message("Новость не найдена")
                    .status(Status.FAILURE)
                    .build();
        }

        return GetNewsLikesResponse.builder()
                .message("Получено кол-во лайков новости")
                .status(Status.FAILURE)
                .amountOfLikes(likeRepository.findAllByNews(newsRepository.findById(id).get()).size())
                .build();
    }

    public LikeDTO addLikeNews(long newsId, String username) {
        if (newsRepository.findById(newsId).isEmpty()) {
            return null;
        }

        if (userRepository.findByEmail(username).isEmpty()) {
            return null;
        }

        var newLike = Like.builder()
                .news(newsRepository.findById(newsId).get())
                .user(userRepository.findByEmail(username).get())
                .build();

        likeRepository.save(newLike);

        return likeDTOMapper.apply(newLike);
    }

    public List<CommentDTO> getNewsComments(long id, int pageNo, int pageSize) {
        if (newsRepository.findById(id).isEmpty()) {
            return null;
        }

        Page<Comment> commentDTOPage = commentRepository.findAllByNews(
                newsRepository.findById(id).get(),
                PageRequest.of(pageNo, pageSize)
        );

        return commentDTOPage.getContent()
                .stream().map(commentDTOMapper)
                .collect(Collectors.toList());
    }

    public CommentDTO addNewsComments(CommentNewsRequest request, long newsId, String username) {
        if (newsRepository.findById(newsId).isEmpty() ||
                userRepository.findByEmail(username).isEmpty()) {
            return null;
        }

        var newComment = Comment.builder()
                .message(request.getMessage())
                .user(userRepository.findByEmail(username).get())
                .news(newsRepository.findById(newsId).get())
                .build();

        if(commentRepository.findById(request.getParentId()).isPresent()){
            newComment.setParent(commentRepository.findById(request.getParentId()).get());
        }

        commentRepository.save(newComment);

        return commentDTOMapper.apply(newComment);
    }

}
