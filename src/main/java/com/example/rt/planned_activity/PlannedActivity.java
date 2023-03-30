package com.example.rt.planned_activity;

import com.example.rt.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "planned_activity_rt")
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
