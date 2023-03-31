package com.example.rt.news;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
