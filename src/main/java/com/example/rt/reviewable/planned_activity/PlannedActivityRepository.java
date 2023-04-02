package com.example.rt.reviewable.planned_activity;

import com.example.rt.reviewable.Repository;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface PlannedActivityRepository extends Repository<PlannedActivity> {
}
