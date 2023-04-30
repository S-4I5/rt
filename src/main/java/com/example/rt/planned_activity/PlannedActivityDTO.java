package com.example.rt.planned_activity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

public record PlannedActivityDTO (
     Long id,
     String title,
     String description,
     String placeName,
     String photo,
     Date plannedDate,
     String author
    ){
}
