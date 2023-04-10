package com.example.rt.reviewable;

import com.example.rt.reviewable.domain.AbstractBody;
import lombok.Data;

@Data
public class AbstractRequest<B extends AbstractBody>{
    B body;
    Long authorId;
}
