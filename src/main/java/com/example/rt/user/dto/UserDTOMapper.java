package com.example.rt.user.dto;

import com.example.rt.user.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPhoto(),
                user.getBirthdayDate(),
                user.getPhoneNumber(),
                user.getBalance(),
                user.getRole()
        );
    }
}
