package com.example.rt.planned_activity;

import lombok.Data;

import java.util.Date;

@Data
public class PostPlannedActivityRequest {
    private String title;
    private String description;
    private String placeName;
    private String photo;
    private Date plannedDate;
}
