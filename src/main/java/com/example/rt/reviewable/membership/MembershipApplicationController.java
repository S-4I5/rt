package com.example.rt.reviewable.membership;

import com.example.rt.reviewable.AbstractController;
import com.example.rt.reviewable.GenericService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/applications")
public class MembershipApplicationController extends AbstractController<MembershipApplicationBody> {
    public MembershipApplicationController(GenericService<MembershipApplicationBody> service) {
        super(service);
    }
}
