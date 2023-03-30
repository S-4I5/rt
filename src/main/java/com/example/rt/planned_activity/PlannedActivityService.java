package com.example.rt.planned_activity;

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
                        .isApproved(false)
                        .author(optionalUser.get())
                        .build()
        );
    }

    public void acceptEvent(long id) {
        plannedActivityRepository.findById(id).get().setApproved(true);
    }
}
