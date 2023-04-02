package com.example.rt.reviewable.planned_activity;

import com.example.rt.reviewable.Entity;
import com.example.rt.reviewable.Repository;
import com.example.rt.reviewable.Service;
import com.example.rt.user.UserRepository;

@org.springframework.stereotype.Service
public class PlannedActivityService extends Service<PlannedActivityBody> {
    public PlannedActivityService(Repository<Entity<PlannedActivityBody>> repository, UserRepository userRepository) {
        super(repository, userRepository);
    }
}
