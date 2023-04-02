package com.example.rt.reviewable.planned_activity;

import com.example.rt.reviewable.Body;
import com.example.rt.reviewable.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/planned-activities")
public class PlannedActivityController<B extends Body> extends Controller<B> {
}
