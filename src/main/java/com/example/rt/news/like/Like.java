package com.example.rt.news.like;

import com.example.rt.news.News;
import com.example.rt.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_like")
public class Like {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
