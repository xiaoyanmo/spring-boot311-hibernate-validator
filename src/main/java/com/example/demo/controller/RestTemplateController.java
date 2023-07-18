package com.example.demo.controller;

import com.example.demo.entities.Person;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestTemplateController {
    @GetMapping("/getMsg")
    public String getMsg() {
        return "Hello World!";
    }

    @PostMapping("/person")
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {

        return ResponseEntity.ok(person);
    }
}
