package com.example.rt.planned_activity;

import com.example.rt.user.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;

public class PlannedActivity {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;

    private String placeName;

    private String photo;

    private Date plannedDate;

    private boolean isApproved;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
}
