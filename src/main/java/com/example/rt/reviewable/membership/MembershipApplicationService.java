package com.example.rt.reviewable.membership;

import com.example.rt.reviewable.AbstractDTOMapper;
import com.example.rt.reviewable.AbstractRepository;
import com.example.rt.reviewable.GenericService;
import com.example.rt.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class MembershipApplicationService extends GenericService<MembershipApplicationBody> {
    public MembershipApplicationService(MembershipApplicationRepository repository, UserRepository userRepository, AbstractDTOMapper<MembershipApplicationBody> dtoMapper) {
        super(repository, userRepository, dtoMapper);
    }
}
