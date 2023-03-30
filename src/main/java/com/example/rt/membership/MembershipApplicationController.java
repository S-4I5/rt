package com.example.rt.membership;

import com.example.rt.membership.requests.MembershipApplicationRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/apply")
public class MembershipApplicationController {
    private final MembershipApplicationService membershipApplicationService;

    @PostMapping()
    public ResponseEntity<MembershipApplication> applyMembershipApplication(@RequestBody MembershipApplicationRequest request) {
        return ResponseEntity.ok(membershipApplicationService.applyMembershipApplication(request));
    }

    @PostMapping("{id}")
    public ResponseEntity<MembershipApplication> acceptMembershipApplication(@PathVariable long id) {
        return ResponseEntity.ok(membershipApplicationService.acceptMembershipApplication(id));
    }
}
