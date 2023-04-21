package com.example.rt.auth.email_activation;

import com.example.rt.auth.token.Token;
import com.example.rt.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailActivationCodeRepository extends JpaRepository<EmailActivationCode, Long> {
    Optional<EmailActivationCode> findByUser(User user);

    Optional<EmailActivationCode> findByCode(String code);
}
