package com.example.rt.membership.dto;

import com.example.rt.membership.MembershipApplicationState;
import com.example.rt.user.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public record MembershipApplicationDTO(
        Long id,
        String photo,
        Long userID,
        Enum<MembershipApplicationState> state
) {
}
