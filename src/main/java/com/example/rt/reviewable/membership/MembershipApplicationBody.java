package com.example.rt.reviewable.membership;

import com.example.rt.reviewable.domain.AbstractBody;
import jakarta.persistence.Embeddable;

@Embeddable
public class MembershipApplicationBody extends AbstractBody {
    private String photo;
}
