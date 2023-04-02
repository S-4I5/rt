package com.example.rt.reviewable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class Controller<B extends Body> {
    private Service<B> service;
    @PostMapping()
    ResponseEntity<Entity<B>> apply(@RequestBody Request<B> request) {
        return ResponseEntity.ok(service.apply(request));
    }
    @PutMapping("{id}")
    ResponseEntity<Entity<B>> accept(@PathVariable long id) {
        return ResponseEntity.ok(service.accept(id));
    }
}
