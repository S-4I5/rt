package com.example.rt.feedback;

public record PostFeedbackRequest (
        String topic,
        String text
){
}
