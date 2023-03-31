package com.example.rt.planned_activity;

import com.example.rt.membership.MembershipApplicationState;
import com.example.rt.user.User;
import com.example.rt.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlannedActivityService {
    private final PlannedActivityRepository plannedActivityRepository;
    private final UserRepository userRepository;


    public List<PlannedActivity> getPlannedEvents() {
        return plannedActivityRepository.findAll();
    }

    public PlannedActivity suggestEvent(PostPlannedActivityRequest request) {
        Optional<User> optionalUser = userRepository.findById(request.getAuthorId());
        if (optionalUser.isEmpty()) {
            return null;
        }
        return plannedActivityRepository.save(
                PlannedActivity.builder()
                        .title(request.getTitle())
                        .description(request.getDescription())
                        .placeName(request.getPlaceName())
                        .photo(request.getPhoto())
                        .plannedDate(request.getPlannedDate())
                        .state(PlannedActivityState.IN_REVIEWING)
                        .author(optionalUser.get())
                        .build()
        );
    }

    public PlannedActivity acceptEvent(long id) {
        Optional<PlannedActivity> optionalPlannedActivity = plannedActivityRepository.findById(id);
        if (optionalPlannedActivity.isEmpty()) {
            return null;
        }
        optionalPlannedActivity.get().setState(PlannedActivityState.APPROVED);
        return plannedActivityRepository.save(optionalPlannedActivity.get());
    }
}
