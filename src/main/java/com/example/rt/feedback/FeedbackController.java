package com.example.rt.feedback;

import com.example.rt.auth.responses.CreatePasswordRestoreCodeResponse;
import com.example.rt.data.Status;
import com.example.rt.feedback.responce.FeedbackPostResponse;
import com.example.rt.mail.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
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
    public FeedbackPostResponse sendMessage(
            @RequestBody PostFeedbackRequest request,
            Authentication authentication
    ) {
        try{

            mailSender.sendFeedbackEmail(request, authentication);

        }catch (MailException e){
            return new FeedbackPostResponse(
                    "Не удалось отправить ваше заявление",
                    Status.FAILURE
            );
        }

        return new FeedbackPostResponse(
                "Ваше заявление успешно отправлено",
                Status.SUCCESS
        );
    }
}
