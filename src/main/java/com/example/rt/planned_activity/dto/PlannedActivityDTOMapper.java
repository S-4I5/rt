package com.example.rt.planned_activity;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PlannedActivityDTOMapper implements Function<PlannedActivity, PlannedActivityDTO> {
    @Override
    public PlannedActivityDTO apply(PlannedActivity plannedActivity) {
        return new PlannedActivityDTO(
                plannedActivity.getId(),
                plannedActivity.getTitle(),
                plannedActivity.getDescription(),
                plannedActivity.getPlaceName(),
                plannedActivity.getPhoto(),
                plannedActivity.getPlannedDate(),
                plannedActivity.getAuthor().getFirstname() + " " + plannedActivity.getAuthor().getLastname()
        );
    }
}