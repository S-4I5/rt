package com.example.rt.reviewable;

import com.example.rt.reviewable.domain.AbstractBody;
import com.example.rt.reviewable.domain.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractRepository<B extends AbstractBody> extends JpaRepository<AbstractEntity<B>, Long> {
}
