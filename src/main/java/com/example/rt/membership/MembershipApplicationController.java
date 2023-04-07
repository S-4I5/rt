package com.example.rt.membership;

import com.example.rt.membership.requests.MembershipApplicationRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/apply")
public class MembershipApplicationController {
    private final MembershipApplicationService membershipApplicationService;

    @GetMapping()
    public ResponseEntity<List<MembershipApplicationDTO>> getAllMembershipApplications() {
        return ResponseEntity.ok(membershipApplicationService.getAllMembershipApplications());
    }

    @PostMapping()
    public ResponseEntity<MembershipApplicationDTO> applyMembershipApplication(@RequestBody MembershipApplicationRequest request) {
        return ResponseEntity.ok(membershipApplicationService.applyMembershipApplication(request));
    }

    @PostMapping("{id}")
    public ResponseEntity<MembershipApplicationDTO> acceptMembershipApplication(@PathVariable long id) {
        return ResponseEntity.ok(membershipApplicationService.acceptMembershipApplication(id));
    }
}
