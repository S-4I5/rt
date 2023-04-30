package com.example.rt.mail;

import com.example.rt.auth.email_activation.EmailActivationCode;
import com.example.rt.feedback.PostFeedbackRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSender {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.feedback-mail}")
    private String feedbackMail;

    private SimpleMailMessage generateSimpleMailMessage(
            String from,
            String to,
            String emailSubject,
            String text
    ){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(emailSubject);
        mailMessage.setText(text);

        return mailMessage;
    }

    public void sendActivationEmail(EmailActivationCode emailActivationCode) {

        String EMAIL_SUBJECT = "Email activation";

        String AUTH_MESSAGE = String.format("Hello, %s! \n" +
                        "Welcome to RT. Your activation code is %s",
                emailActivationCode.user.getFirstname() + " " + emailActivationCode.user.getLastname(),
                emailActivationCode.code);

        SimpleMailMessage mailMessage = generateSimpleMailMessage(
                username,
                emailActivationCode.getUser().getEmail(),
                EMAIL_SUBJECT,
                AUTH_MESSAGE
        );

        mailSender.send(mailMessage);
    }

    public void sendFeedbackEmail(PostFeedbackRequest postFeedbackRequest, Authentication authentication) {

        SimpleMailMessage mailMessage = generateSimpleMailMessage(
                username,
                feedbackMail,
                postFeedbackRequest.topic() + " from " + authentication.getName(),
                postFeedbackRequest.text()
        );

        mailSender.send(mailMessage);
    }
}
