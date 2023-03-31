package com.example.rt.membership;

import com.example.rt.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MembershipApplication {
    @Id
    @GeneratedValue
    private Long id;
    private String photo;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Enum<MembershipApplicationState> state;
}
