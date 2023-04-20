package com.example.rt.planned_activity;

import com.example.rt.membership.MembershipApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/planned-activities")
@RequiredArgsConstructor
public class PlannedActivityController {
    private final PlannedActivityService plannedActivityService;

    @GetMapping()
    ResponseEntity<List<PlannedActivity>> getAllPlannedActivities(
            @RequestParam(value = "pageNo", required = false) int pageNo,
            @RequestParam(value = "pageSize", required = false) int pageSize
    ) {
        return ResponseEntity.ok(plannedActivityService.getAllPlannedActivities(pageNo, pageSize));
    }

    @PostMapping()
    ResponseEntity<PlannedActivity> suggestPlannedActivity(@RequestBody PostPlannedActivityRequest request, Authentication authentication) {
        return ResponseEntity.ok(plannedActivityService.suggestEvent(request, authentication.getName()));
    }

    @PutMapping("{id}")
    ResponseEntity<PlannedActivity> acceptPlannedActivity(@PathVariable long id) {
        return ResponseEntity.ok(plannedActivityService.acceptEvent(id));
    }
}
