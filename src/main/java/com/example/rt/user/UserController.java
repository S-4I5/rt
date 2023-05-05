package com.example.rt.user;

import com.example.rt.planned_activity.dto.PlannedActivityDTO;
import com.example.rt.user.request.GetTokensResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/tokens/{id}")
    ResponseEntity<GetTokensResponse> getUserTokens(@PathVariable long id) {
        return ResponseEntity.ok(userService.getUserTokens(id));
    }
}
