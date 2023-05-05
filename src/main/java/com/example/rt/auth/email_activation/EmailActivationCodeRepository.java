package com.example.rt.auth.email_activation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailActivationCodeRepository extends JpaRepository<EmailActivationCode, Long> {
    Optional<EmailActivationCode> findByCode(String code);
}
