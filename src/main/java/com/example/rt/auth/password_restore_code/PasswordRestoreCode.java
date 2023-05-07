package com.example.rt.auth.password_restore_code;

import com.example.rt.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PasswordRestoreCode {
    @Id
    @GeneratedValue
    public long id;
    public String code;
    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;
}

