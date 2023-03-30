package com.example.rt.news.comment;

import com.example.rt.news.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByNews(News news);
}
