package com.example.rt.news;

import com.example.rt.news.request.PostNewsRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;

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

}
