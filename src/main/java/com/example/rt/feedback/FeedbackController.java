package com.example.rt.feedback;

import com.example.rt.feedback.responce.FeedbackPostResponce;
import com.example.rt.mail.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/feedback")
public class FeedbackController {
    private final MailSender mailSender;

    @PostMapping()
    public ResponseEntity<FeedbackPostResponce> sendMessage(
            @RequestBody PostFeedbackRequest request,
            Authentication authentication
    ) {
        mailSender.sendFeedbackEmail(request, authentication);
        return ResponseEntity.ok(new FeedbackPostResponce("Feedback posted"));
    }
}
