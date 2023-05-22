package com.example.rt.planned_activity.requests;

import java.util.Date;

public record PostPlannedActivityRequest(
        String title,
        String description,
        String placeName,
        String photo,
        Date plannedDate
) {
}
