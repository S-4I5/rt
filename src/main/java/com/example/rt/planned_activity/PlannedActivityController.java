package com.example.rt.planned_activity;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/planned-activities")
@RequiredArgsConstructor
public class PlannedActivityController {
    private final PlannedActivityService plannedActivityService;

    @GetMapping()
    ResponseEntity<List<PlannedActivity>> getPlannedEvents() {
        return ResponseEntity.ok(plannedActivityService.getPlannedEvents());
    }

    @PostMapping()
    ResponseEntity<PlannedActivity> suggestEvent(@RequestBody PostPlannedActivityRequest request) {
        return ResponseEntity.ok(plannedActivityService.suggestEvent(request));
    }

    @PostMapping("{id}")
    void acceptEvent(@PathVariable long id) {
        plannedActivityService.acceptEvent(id);
    }
}
