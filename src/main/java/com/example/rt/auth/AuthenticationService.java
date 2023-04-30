package com.example.rt.auth;

import com.example.rt.auth.email_activation.EmailActivationCode;
import com.example.rt.auth.email_activation.EmailActivationCodeRepository;
import com.example.rt.auth.requests.AuthenticationRequest;
import com.example.rt.auth.responses.activation.ActivationSuccessResponse;
import com.example.rt.auth.responses.registration.RegisterRequest;
import com.example.rt.auth.responses.ResponseBase;
import com.example.rt.auth.responses.authentication.AuthenticationFailedResponse;
import com.example.rt.auth.responses.authentication.AuthenticationSuccedResponse;
import com.example.rt.auth.token.Token;
import com.example.rt.auth.token.TokenRepository;
import com.example.rt.auth.token.TokenType;
import com.example.rt.mail.MailSender;
import com.example.rt.user.Role;
import com.example.rt.user.User;
import com.example.rt.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    public ResponseBase register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return new AuthenticationFailedResponse("User already exists");
        }

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .isEnabled(false)
                .build();

        userRepository.save(user);

        var activationCode = generateActivationCode(user);
        emailActivationCodeRepository.save(activationCode);
        mailSender.sendActivationEmail(activationCode);

        System.out.println("XDD" + emailActivationCodeRepository.count());

        return new ActivationSuccessResponse("Activation email was sent");
    }

    public ResponseBase activate(String code, String email){
        Optional<EmailActivationCode> emailActivationCode = emailActivationCodeRepository.findByCode(code)
                .filter(x -> x.getUser().getEmail().equals(email));

        if(emailActivationCode.isEmpty()){
            return AuthenticationFailedResponse.builder()
                    .message("Invalid code")
                    .build();
        }

        emailActivationCode.get().user.setEnabled(true);

        var jwtToken = jwtService.generateToken(emailActivationCode.get().user);

        saveUserToken(emailActivationCode.get().user, jwtToken);

        emailActivationCodeRepository.delete(emailActivationCode.get());

        return AuthenticationSuccedResponse.builder()
                .token(jwtToken)
                .id(emailActivationCode.get().user.getId())
                .build();
    }

    public AuthenticationSuccedResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationSuccedResponse.builder()
                .token(jwtToken)
                .id(user.getId())
                .build();
    }

    private EmailActivationCode generateActivationCode(User user){
        return EmailActivationCode.builder()
                .user(user)
                .code(new Random().ints(6, 33, 122)
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
