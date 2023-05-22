package com.example.rt.auth;

import com.example.rt.auth.email_activation_code.EmailActivationCode;
import com.example.rt.auth.email_activation_code.EmailActivationCodeRepository;
import com.example.rt.auth.password_restore_code.PasswordRestoreCode;
import com.example.rt.auth.password_restore_code.PasswordRestoreCodeRepository;
import com.example.rt.auth.requests.AuthenticationRequest;
import com.example.rt.auth.requests.CheckPasswordRestoreCodeRequest;
import com.example.rt.auth.requests.PasswordResetRequest;
import com.example.rt.auth.responses.*;
import com.example.rt.auth.requests.RegisterRequest;
import com.example.rt.auth.token.Token;
import com.example.rt.auth.token.TokenRepository;
import com.example.rt.auth.token.TokenType;
import com.example.rt.data.Status;
import com.example.rt.mail.MailSender;
import com.example.rt.user.Role;
import com.example.rt.user.User;
import com.example.rt.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailActivationCodeRepository emailActivationCodeRepository;
    private final MailSender mailSender;
    private final PasswordRestoreCodeRepository passwordRestoreCodeRepository;

    public ResponseEntity<RegistrationResponse> register(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            return ResponseEntity.badRequest().body(RegistrationResponse.builder()
                    .status(Status.FAILURE)
                    .message("Пользователь уже существует")
                    .build());
        }

        var activationCode = generateActivationCode(
                request.email().toLowerCase(),
                passwordEncoder.encode(request.password())
        );

        Optional<EmailActivationCode> code = emailActivationCodeRepository.findByEmail(request.email());
        if(code.isPresent()){
            code.get().setCode(activationCode.code);
            emailActivationCodeRepository.save(code.get());
        } else {
            emailActivationCodeRepository.save(activationCode);
        }

        try{

            mailSender.sendActivationEmail(activationCode);

        }catch (MailException e){
            emailActivationCodeRepository.delete(activationCode);

            return ResponseEntity.badRequest().body(RegistrationResponse.builder()
                    .status(Status.FAILURE)
                    .message("Эта почта занята!")
                    .build());
        }

        return ResponseEntity.ok(RegistrationResponse.builder()
                .status(Status.SUCCESS)
                .message("Письмо активации выслано на почту")
                .build());
    }

    public ResponseEntity<AuthenticationResponse> activate(String code, String email){
        Optional<EmailActivationCode> emailActivationCode = emailActivationCodeRepository.findByCode(code)
                .filter(x -> x.getEmail().equals(email));

        if(emailActivationCode.isEmpty()){
            return ResponseEntity.badRequest().body(AuthenticationResponse.builder()
                    .message("Неверный код активации")
                    .status(Status.FAILURE)
                    .build());
        }

        var user = User.builder()
                .email(emailActivationCode.get().email)
                .password(emailActivationCode.get().password)
                .role(Role.USER)
                .isEnabled(true)
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        saveUserToken(user, jwtToken);

        emailActivationCodeRepository.delete(emailActivationCode.get());

        return ResponseEntity.ok(AuthenticationResponse.builder()
                .token(jwtToken)
                .message("Аккаунт успешно активирован")
                .status(Status.SUCCESS)
                .id(user.getId())
                .build());
    }

    public ResponseEntity<ActivationResponse> authenticate(AuthenticationRequest request) {
        try {
            if(userRepository.findByEmail(request.email()).isEmpty()){
                return ResponseEntity.badRequest().body(ActivationResponse.builder()
                        .status(Status.FAILURE)
                        .message("Пользователь не найден")
                        .build());
            }

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
        }catch (BadCredentialsException exception){
            return ResponseEntity.badRequest().body(ActivationResponse.builder()
                    .status(Status.FAILURE)
                    .message("Неверный пароль")
                    .build());
        }catch (DisabledException exception){
            return ResponseEntity.badRequest().body(ActivationResponse.builder()
                    .status(Status.FAILURE)
                    .message("Подтвердите почту")
                    .build());
        }

        User user = userRepository.findByEmail(request.email()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return ResponseEntity.ok(ActivationResponse.builder()
                .token(jwtToken)
                .message("Авторизация прошла успешно")
                .status(Status.SUCCESS)
                .id(user.getId())
                .build());
    }

    public ResponseEntity<PasswordResetResponse> restorePassword(String code, PasswordResetRequest request){
        Optional<PasswordRestoreCode> restoreCode = passwordRestoreCodeRepository.findByCode(code);
        if(restoreCode.isEmpty() || !Objects.equals(restoreCode.get().user.getEmail(), request.email())){
            return ResponseEntity.badRequest().body(PasswordResetResponse.builder()
                    .message("Неверный код")
                    .status(Status.FAILURE)
                    .build());
        }

        restoreCode.get().user.setPassword(passwordEncoder.encode(request.newPassword()));

        var jwtToken = jwtService.generateToken(restoreCode.get().user);

        saveUserToken(restoreCode.get().user, jwtToken);

        return ResponseEntity.ok(PasswordResetResponse.builder()
                .message("Смена пароля прошла успешно")
                .status(Status.SUCCESS)
                .token(jwtToken)
                .build());
    }

    public ResponseEntity<CreatePasswordRestoreCodeResponse> sendPasswordRestoreCode(String email){
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isEmpty()){
            return ResponseEntity.badRequest().body(CreatePasswordRestoreCodeResponse.builder()
                    .message("Пользователь не существует")
                    .status(Status.FAILURE)
                    .build());
        }

        var activationCode = generatePasswordRestoreCode(user.get());

        System.out.println(activationCode.code + " " + user.get().getEmail());

        passwordRestoreCodeRepository.save(activationCode);

        try{

            mailSender.sendPasswordRestoreEmail(activationCode);

        }catch (MailException e){
            return ResponseEntity.badRequest().body(CreatePasswordRestoreCodeResponse.builder()
                    .message("Не удалось отправить код для смены пароля")
                    .status(Status.FAILURE)
                    .build());
        }

        return ResponseEntity.ok(CreatePasswordRestoreCodeResponse.builder()
                .message("Письмо активации выслано на почту")
                .status(Status.SUCCESS)
                .build());
    }

    public ResponseEntity<CheckPasswordRestoreCodeResponse> checkPasswordRestoreCode(String code, CheckPasswordRestoreCodeRequest request){
        Optional<PasswordRestoreCode> restoreCode = passwordRestoreCodeRepository.findByCode(code);

        if(restoreCode.isEmpty() || !Objects.equals(restoreCode.get().user.getEmail(), request.email())){
            return ResponseEntity.badRequest().body(CheckPasswordRestoreCodeResponse.builder()
                    .message("Неверный код")
                    .status(Status.FAILURE)
                    .build());
        }

        return ResponseEntity.ok(CheckPasswordRestoreCodeResponse.builder()
                .message("Код верный")
                .status(Status.SUCCESS)
                .build());
    }

    private PasswordRestoreCode generatePasswordRestoreCode(User user){
        return PasswordRestoreCode.builder()
                .user(user)
                .code(generateCode())
                .build();
    }

    private EmailActivationCode generateActivationCode(String email, String password){
        return EmailActivationCode.builder()
                .password(password)
                .email(email)
                .code(generateCode())
                .build();
    }

    private String generateCode(){
        return new Random().ints(6, 65, 90)
                .mapToObj(i -> String.valueOf((char)i)).collect(Collectors.joining());
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());

        if (validUserTokens.isEmpty()) {
            return;
        }

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }
}
