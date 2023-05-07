package com.example.rt.auth.password_restore_code;

import com.example.rt.auth.email_activation_code.EmailActivationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordRestoreCodeRepository extends JpaRepository<PasswordRestoreCode, Long> {
    Optional<PasswordRestoreCode> findByCode(String code);
}
