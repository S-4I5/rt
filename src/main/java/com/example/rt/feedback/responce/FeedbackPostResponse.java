package com.example.rt.feedback.responce;

import com.example.rt.data.Status;

public record FeedbackPostResponse(
        String message,
        Status status
) {
}
