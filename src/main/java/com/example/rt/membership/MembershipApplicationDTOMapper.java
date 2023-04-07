package com.example.rt.membership;

import java.util.function.Function;

public class MembershipApplicationDTOMapper implements Function<MembershipApplication, MembershipApplicationDTO> {
    @Override
    public MembershipApplicationDTO apply(MembershipApplication membershipApplication) {
        return new MembershipApplicationDTO(
                membershipApplication.getId(),
                membershipApplication.getPhoto(),
                membershipApplication.getUser().getId(),
                membershipApplication.getState()
        );
    }
}
