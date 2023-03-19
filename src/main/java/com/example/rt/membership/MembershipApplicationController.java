package com.example.rt.membership;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/apply")
public class MembershipApplicationController {
    @PostMapping()
    void applyMembershipApplication() {

    }
    
    @PostMapping("{id}")
    void acceptMembershipApplication() {

    }
}
