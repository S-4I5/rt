package com.example.rt.reviewable.planned_activity;

import com.example.rt.reviewable.Body;
import jakarta.persistence.Embeddable;

import java.util.Date;

@Embeddable
public class PlannedActivityBody extends Body {
    private String title;
    private String description;
    private String placeName;
    private String photo;
    private Date plannedDate;
    private long authorId;
}
