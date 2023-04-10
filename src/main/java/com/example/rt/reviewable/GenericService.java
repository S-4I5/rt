package com.example.rt.reviewable;

import com.example.rt.reviewable.domain.AbstractBody;
import com.example.rt.reviewable.domain.AbstractEntity;
import com.example.rt.reviewable.domain.State;
import com.example.rt.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenericService<B extends AbstractBody, R extends AbstractRepository<B>, E extends AbstractEntity<B>> {
    private final R repository;
    private final UserRepository userRepository;
    private final AbstractDTOMapper<B> dtoMapper;

    public List<AbstractDTO<B>> getAllReviewable(int pageNo, int pageSize) {
        Page<E> reviewablesPage = (Page<E>) repository.findAll(
                PageRequest.of(pageNo, pageSize)
        );

        return reviewablesPage.getContent()
                .stream().map(dtoMapper).collect(Collectors.toList());
    }

    public AbstractDTO<B> suggest(AbstractRequest<B> request) {
        if (userRepository.findById(request.getAuthorId()).isEmpty()) {
            return null;
        }

        AbstractEntity<B> newReviewable = new AbstractEntity<B>(
                request.getBody(),
                userRepository.findById(request.getAuthorId()).get(),
                State.IN_REVIEWING
        );

        repository.save(newReviewable);

        return dtoMapper.apply(newReviewable);
    }

    public AbstractDTO<B> accept(long id) {
        if (repository.findById(id).isEmpty()) {
            return null;
        }

        AbstractEntity<B> reviewed = repository.findById(id).get();
        reviewed.setState(State.APPROVED);

        repository.save(reviewed);

        return dtoMapper.apply(reviewed);
    }
}
