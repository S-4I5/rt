package com.example.rt.auth;

import com.example.rt.auth.email_activation.EmailActivationCode;
import com.example.rt.auth.email_activation.EmailActivationCodeRepository;
import com.example.rt.auth.requests.AuthenticationRequest;
import com.example.rt.auth.responses.ActivationResponse;
import com.example.rt.auth.responses.AuthenticationResponse;
import com.example.rt.auth.requests.RegisterRequest;
import com.example.rt.auth.responses.RegistrationResponse;
import com.example.rt.auth.token.Token;
import com.example.rt.auth.token.TokenRepository;
import com.example.rt.auth.token.TokenType;
import com.example.rt.data.Status;
import com.example.rt.mail.MailSender;
import com.example.rt.user.Role;
import com.example.rt.user.User;
import com.example.rt.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public RegistrationResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return RegistrationResponse.builder()
                    .status(Status.FAILURE)
                    .message("Пользовтель с такой почтой уже существует")
                    .build();
        }

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail().toLowerCase())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .isEnabled(false)
                .build();

        userRepository.save(user);

        var activationCode = generateActivationCode(user);
        emailActivationCodeRepository.save(activationCode);
        mailSender.sendActivationEmail(activationCode);

        return RegistrationResponse.builder()
                .status(Status.SUCCESS)
                .message("Письмо активации выслано на почту")
                .build();
    }

    public AuthenticationResponse activate(String code, String email){
        Optional<EmailActivationCode> emailActivationCode = emailActivationCodeRepository.findByCode(code)
                .filter(x -> x.getUser().getEmail().equals(email));

        if(emailActivationCode.isEmpty()){
            return AuthenticationResponse.builder()
                    .message("Неверный код активации")
                    .status(Status.FAILURE)
                    .build();
        }

        emailActivationCode.get().user.setEnabled(true);

        var jwtToken = jwtService.generateToken(emailActivationCode.get().user);

        saveUserToken(emailActivationCode.get().user, jwtToken);

        emailActivationCodeRepository.delete(emailActivationCode.get());

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .message("Аккаунт успешно активирован")
                .status(Status.SUCCESS)
                .id(emailActivationCode.get().user.getId())
                .build();
    }

    public ActivationResponse authenticate(AuthenticationRequest request) {
        try {
            if(userRepository.findByEmail(request.getEmail()).isEmpty()){
                return ActivationResponse.builder()
                        .status(Status.FAILURE)
                        .message("Пользователь не найден")
                        .build();
            }

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        }catch (BadCredentialsException exception){
            return ActivationResponse.builder()
                    .status(Status.FAILURE)
                    .message("Неверный пароль")
                    .build();
        }catch (DisabledException exception){
            return ActivationResponse.builder()
                    .status(Status.FAILURE)
                    .message("Подтвердите почту")
                    .build();
        }

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return ActivationResponse.builder()
                .token(jwtToken)
                .message("Авторизация прошла успешно")
                .status(Status.SUCCESS)
                .id(user.getId())
                .build();
    }

    private EmailActivationCode generateActivationCode(User user){
        return EmailActivationCode.builder()
                .user(user)
                .code(new Random().ints(6, 65, 90)
                        .mapToObj(i -> String.valueOf((char)i)).collect(Collectors.joining()))
                .build();
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
