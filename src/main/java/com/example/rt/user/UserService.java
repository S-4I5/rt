package com.example.rt.user;

import com.example.rt.data.Status;
import com.example.rt.user.request.GetTokensResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public GetTokensResponse getUserTokens(long id){
        if(!userRepository.existsById(id)){
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
}
