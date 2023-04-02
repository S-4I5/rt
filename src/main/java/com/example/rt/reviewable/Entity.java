package com.example.rt.reviewable;

import com.example.rt.reviewable.planned_activity.PlannedActivityBody;
import com.example.rt.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Data
@NoArgsConstructor
@MappedSuperclass
public class Entity<B extends Body> {
    @Id
    @GeneratedValue
    private Long id;
    private B body;
    private State state;
    @JoinColumn(name = "user_id")
    private User author;

    Entity(B body, State state, User author) {
        this.body = body;
        this.state = state;
        this.author = author;
    }
}
