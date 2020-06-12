package com.loan_application.controller;

import com.loan_application.domain.user.User;
import com.loan_application.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    private UserService service;


    public RegistrationController(UserService userService) { this.service = userService; }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody User user){
        service.register(user);
        return new ResponseEntity<>("You have been register successfully!", HttpStatus.CREATED);}


}
