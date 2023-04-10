package com.example.rt.reviewable;

import com.example.rt.reviewable.domain.AbstractBody;
import com.example.rt.reviewable.domain.AbstractEntity;
import com.example.rt.reviewable.membership.MembershipApplication;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AbstractDTOMapper<B extends AbstractBody> implements Function<AbstractEntity<B>, AbstractDTO<B>> {
    @Override
    public AbstractDTO<B> apply(AbstractEntity<B> entity) {
        return new AbstractDTO<B>(
                entity.getId(),
                entity.getBody(),
                entity.getAuthor().getId(),
                entity.getState()
        );
    }
}
