package com.example.rt.user;

import com.example.rt.data.Status;
import com.example.rt.user.dto.UserDTO;
import com.example.rt.user.dto.UserDTOMapper;
import com.example.rt.user.requests.ChangeUserInfoRequest;
import com.example.rt.user.responses.GetTokensResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;

    public GetTokensResponse getUserTokens(long id) {
        if (!userRepository.existsById(id)) {
            return GetTokensResponse.builder()
                    .message("Пользователь найден")
                    .status(Status.FAILURE)
                    .build();
        }

        return GetTokensResponse.builder()
                .message("Кол-во токенов получено")
                .status(Status.FAILURE)
                .amountOfTokens(userRepository.getReferenceById(id).getBalance())
                .build();
    }

    public UserDTO getUser(long id){
        Optional<User> user = userRepository.findById(id);

        return user.map(userDTOMapper).orElse(null);

    }

    public UserDTO updateUser(ChangeUserInfoRequest request, long id) {

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            return null;
        }

        Optional.ofNullable(request.getFirstname()).ifPresent(user.get()::setFirstname);
        Optional.ofNullable(request.getLastname()).ifPresent(user.get()::setLastname);
        Optional.ofNullable(request.getEmail()).ifPresent(user.get()::setEmail);
        Optional.ofNullable(request.getBirthdayDate()).ifPresent(user.get()::setBirthdayDate);
        Optional.ofNullable(request.getPhoneNumber()).ifPresent(user.get()::setPhoneNumber);
        Optional.ofNullable(request.getPhoto()).ifPresent(user.get()::setPhoto);

        userRepository.save(user.get());

        return userDTOMapper.apply(user.get());
    }
}
