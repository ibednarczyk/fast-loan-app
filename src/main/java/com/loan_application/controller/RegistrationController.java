package com.loan_application.controller;

import com.loan_application.representation.UserDto;
import com.loan_application.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    private UserService service;


    public RegistrationController(UserService userService) { this.service = userService; }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        return service.register(userDto);
    }


}
