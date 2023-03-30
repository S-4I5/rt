package com.example.rt.news.like;

import com.example.rt.news.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findAllByNews(News news);
}
