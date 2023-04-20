package com.example.rt.membership;

import com.example.rt.membership.requests.MembershipApplicationRequest;
import com.example.rt.news.comment.Comment;
import com.example.rt.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MembershipApplicationService {
    private final MembershipApplicationRepository membershipApplicationRepository;
    private final UserRepository userRepository;
    private final MembershipApplicationDTOMapper membershipApplicationDTOMapper;

    public MembershipApplicationDTO applyMembershipApplication(MembershipApplicationRequest request, String username) {
        if (userRepository.findByEmail(username).isEmpty()) {
            return null;
        }

        MembershipApplication newMembershipApplication = MembershipApplication.builder()
                .photo(request.getPhoto())
                .user(userRepository.findByEmail(username).get())
                .state(MembershipApplicationState.IN_REVIEWING)
                .build();

        membershipApplicationRepository.save(newMembershipApplication);

        return membershipApplicationDTOMapper.apply(newMembershipApplication);
    }

    public List<MembershipApplicationDTO> getAllMembershipApplications(int pageNo, int pageSize) {
        Page<MembershipApplication> membershipApplications = membershipApplicationRepository.findAll(PageRequest.of(pageNo, pageSize));

        return membershipApplications.getContent()
                .stream().map(membershipApplicationDTOMapper).collect(Collectors.toList());
    }

    public MembershipApplicationDTO acceptMembershipApplication(long id) {
        if (membershipApplicationRepository.findById(id).isEmpty()) {
            return null;
        }

        MembershipApplication membershipApplication = membershipApplicationRepository.findById(id).get();
        membershipApplication.setState(MembershipApplicationState.APPROVED);

        membershipApplicationRepository.save(membershipApplication);

        return membershipApplicationDTOMapper.apply(membershipApplication);
    }
}
