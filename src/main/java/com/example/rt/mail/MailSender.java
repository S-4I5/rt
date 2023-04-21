package com.example.rt.mail;

import com.example.rt.auth.email_activation.EmailActivationCode;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSender {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String username;

    public void sendActivationEmail(EmailActivationCode emailActivationCode){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailActivationCode.getUser().getEmail());

        String EMAIL_SUBJECT = "Email activation";
        mailMessage.setSubject(EMAIL_SUBJECT);

        String AUTH_MESSAGE = "Hello, %s! \n" +
                "Welcome to RT. Please, visit next link: http://localhost:8080/activate/%s for activate your account.";
        mailMessage.setText(String.format(
                AUTH_MESSAGE,
                emailActivationCode.user.getFirstname() + " " + emailActivationCode.user.getLastname(),
                emailActivationCode.code
        ));

        mailSender.send(mailMessage);
    }


}
