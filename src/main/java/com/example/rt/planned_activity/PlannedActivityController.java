package com.example.rt.planned_activity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/events")
public class PlannedActivityController {
    @GetMapping()
    String getPlannedEvents() {
        return "plan birthday";
    }

    @PostMapping()
    void suggestEvent() {

    }

    @PostMapping("{id}")
    void acceptEvent() {

    }
}
