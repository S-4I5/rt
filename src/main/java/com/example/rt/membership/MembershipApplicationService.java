package com.example.rt.membership;

import com.example.rt.membership.request.MembershipApplicationRequest;
import com.example.rt.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MembershipApplicationService {
    private final MembershipApplicationRepository membershipApplicationRepository;
    private final UserRepository userRepository;

    public MembershipApplication applyMembershipApplication(MembershipApplicationRequest request) {
        if (userRepository.findById(request.getUserId()).isPresent()) {
            return null;
        }

        MembershipApplication newMembershipApplication = MembershipApplication.builder()
                .photo(request.getPhoto())
                .user(userRepository.findById(request.getUserId()).get())
                .state(MembershipApplicationState.IN_REVIEWING)
                .build();

        membershipApplicationRepository.save(newMembershipApplication);

        return newMembershipApplication;
    }

    public MembershipApplication acceptMembershipApplication(long id) {
        if (membershipApplicationRepository.findById(id).isPresent()) {
            return null;
        }

        MembershipApplication membershipApplication = membershipApplicationRepository.findById(id).get();
        membershipApplication.setState(MembershipApplicationState.APPROVED);

        membershipApplicationRepository.save(membershipApplication);

        return membershipApplication;
    }
}
