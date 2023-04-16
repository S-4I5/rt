package com.example.rt.membership;

import com.example.rt.membership.requests.MembershipApplicationRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/applications")
public class MembershipApplicationController {
    private final MembershipApplicationService membershipApplicationService;

    @GetMapping()
    public ResponseEntity<List<MembershipApplicationDTO>> getAllMembershipApplications(
            @RequestParam(value = "pageNo", required = false) int pageNo,
            @RequestParam(value = "pageSize", required = false) int pageSize
    ) {
        return ResponseEntity.ok(membershipApplicationService.getAllMembershipApplications(pageNo, pageSize));
    }

    @PostMapping()
    public ResponseEntity<MembershipApplicationDTO> applyMembershipApplication(@RequestBody MembershipApplicationRequest request, Authentication authentication) {
        return ResponseEntity.ok(membershipApplicationService.applyMembershipApplication(request, authentication.getName()));
    }

    @PutMapping("{id}")
    public ResponseEntity<MembershipApplicationDTO> acceptMembershipApplication(@PathVariable long id) {
        return ResponseEntity.ok(membershipApplicationService.acceptMembershipApplication(id));
    }
}
