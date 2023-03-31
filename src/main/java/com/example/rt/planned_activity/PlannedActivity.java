package com.example.rt.planned_activity;

import com.example.rt.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private PlannedActivityState state;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
}
