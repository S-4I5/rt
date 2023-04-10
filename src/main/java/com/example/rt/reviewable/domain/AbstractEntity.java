package com.example.rt.reviewable.domain;

import com.example.rt.user.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@MappedSuperclass
public class AbstractEntity<B extends AbstractBody> {
    @Id
    @GeneratedValue
    private Long id;
    private B body;
    private State state;
    @JoinColumn(name = "user_id")
    private User author;

    public AbstractEntity(B body, User author, State state) {
        this.body = body;
        this.author = author;
        this.state = state;
    }
}
