package com.example.rt.user;

import com.example.rt.user.dto.UserDTO;
import com.example.rt.user.dto.UserDTOMapper;
import com.example.rt.user.requests.ChangeUserInfoRequest;
import com.example.rt.user.responses.GetTokensResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}/tokens")
    ResponseEntity<GetTokensResponse> getUserTokens(@PathVariable long id) {
        return ResponseEntity.ok(userService.getUserTokens(id));
    }

    @GetMapping("/{id}")
    ResponseEntity<UserDTO> getUser(@PathVariable long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PutMapping("/{id}/info")
    ResponseEntity<UserDTO> setUserInfo(
            @PathVariable long id,
            @RequestBody ChangeUserInfoRequest request
    ) {
        return ResponseEntity.ok(userService.updateUser(request, id));
    }
}
