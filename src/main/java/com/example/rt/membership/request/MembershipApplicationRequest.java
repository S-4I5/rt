package com.example.rt.membership.request;

import com.example.rt.user.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MembershipApplicationRequest {
    private String photo;
    private long userId;
}
