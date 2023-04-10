package com.example.rt.reviewable;

import com.example.rt.reviewable.domain.AbstractBody;
import com.example.rt.reviewable.domain.AbstractEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
public class AbstractController<B extends AbstractBody> {
    private final GenericService<B> service;

    @GetMapping()
    public ResponseEntity<List<AbstractDTO<B>>> getAllReviewable(
            @RequestParam(value = "pageNo", required = false) int pageNo,
            @RequestParam(value = "pageSize", required = false) int pageSize
    ) {
        return ResponseEntity.ok(service.getAllReviewable(pageNo, pageSize));
    }

    @PostMapping()
    public ResponseEntity<AbstractDTO<B>> suggest(@RequestBody AbstractRequest<B> request) {
        return ResponseEntity.ok(service.suggest(request));
    }

    @PostMapping("{id}")
    public ResponseEntity<AbstractDTO<B>> accept(@PathVariable long id) {
        return ResponseEntity.ok(service.accept(id));
    }
}
