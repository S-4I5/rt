package com.example.rt.reviewable;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository<E extends Entity> extends JpaRepository<E, Long> {
}
