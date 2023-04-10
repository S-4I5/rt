package com.example.rt.reviewable;

import com.example.rt.reviewable.domain.AbstractBody;
import com.example.rt.reviewable.domain.State;

public record AbstractDTO<B extends AbstractBody>(
    Long id,
    B body,
    Long authorId,
    State state
) {

}
