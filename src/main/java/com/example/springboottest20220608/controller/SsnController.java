package com.example.springboottest20220608.controller;

import com.example.springboottest20220608.entity.Ssn;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ssn")
public class SsnController {

    private Ssn ssn;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello Spring Boot!";
    }

    @PostMapping
    public String ssnValidation(@RequestBody Ssn ssn) {

        String responseStr = "ssn value received: " + ssn;

        return responseStr;
    }
}
