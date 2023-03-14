package com.example.rt.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/events")
public class EventsController {
    @GetMapping()
    String getEvents() {
        return "plan birthday";
    }
}
