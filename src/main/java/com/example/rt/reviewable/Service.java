package com.example.rt.reviewable;

import com.example.rt.user.User;
import com.example.rt.user.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class Service<B extends Body> {
    private final Repository<Entity<B>> repository;
    private final UserRepository userRepository;
    Entity<B> apply(Request<B> request) { //<E extends Entity<B>>
        Optional<User> optionalUser = userRepository.findById(request.getAuthorId());
        if (optionalUser.isEmpty()) {
            return null;
        }
        return repository.save(
                new Entity<B>(request.getBody(), State.IN_REVIEWING, optionalUser.get())
        );
    }
    public Entity<B> accept(long id) {
        Optional<Entity<B>> optionalEntity = repository.findById(id);
        if (optionalEntity.isEmpty()) {
            return null;
        }
        optionalEntity.get().setState(State.APPROVED);
        return repository.save(optionalEntity.get());
    }
}
