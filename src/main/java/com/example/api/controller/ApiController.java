package com.example.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.core.JacksonException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ApiController {

    private List<String> tasks = new ArrayList<>();

    private ObjectMapper objectMapper;

    public ApiController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping(path = "/tasks")
    public ResponseEntity<String> listTasks() throws JacksonException {
        return ResponseEntity.ok(objectMapper.writeValueAsString(tasks));
    }

    @PostMapping(path = "/tasks")
    public ResponseEntity<Void> createTask(@RequestBody String task){
        tasks.add(task);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/tasks")
    public ResponseEntity<Void> clearTasks() {
        tasks = new ArrayList<>();
        return ResponseEntity.ok().build();
    }
}