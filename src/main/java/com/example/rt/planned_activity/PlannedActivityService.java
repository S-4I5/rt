package com.example.rt.planned_activity;

import com.example.rt.membership.MembershipApplicationState;
import com.example.rt.news.News;
import com.example.rt.user.User;
import com.example.rt.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlannedActivityService {
    private final PlannedActivityRepository plannedActivityRepository;
    private final UserRepository userRepository;
    private final PlannedActivityDTOMapper plannedActivityDTOMapper;


    public List<PlannedActivityDTO> getAllPlannedActivities(int pageNo, int pageSize) {
        return plannedActivityRepository.findAll(PageRequest.of(pageNo, pageSize))
                .stream().map(plannedActivityDTOMapper).toList();
    }

    public PlannedActivityDTO suggestEvent(PostPlannedActivityRequest request, String username) {
        Optional<User> optionalUser = userRepository.findByEmail(username);

        return optionalUser.map(user -> plannedActivityDTOMapper.apply(
                plannedActivityRepository.save(
                        PlannedActivity.builder()
                                .title(request.getTitle())
                                .description(request.getDescription())
                                .placeName(request.getPlaceName())
                                .photo(request.getPhoto())
                                .plannedDate(request.getPlannedDate())
                                .state(PlannedActivityState.IN_REVIEWING)
                                .author(user)
                                .build()
                )
        )).orElse(null);
    }

    public PlannedActivityDTO acceptEvent(long id) {
        Optional<PlannedActivity> optionalPlannedActivity = plannedActivityRepository.findById(id);
        if (optionalPlannedActivity.isEmpty()) {
            return null;
        }

        optionalPlannedActivity.get().setState(PlannedActivityState.APPROVED);

        return plannedActivityDTOMapper.apply(plannedActivityRepository.save(optionalPlannedActivity.get()));
    }
}
