package com.loan_application.controller;

import com.loan_application.domain.user.User;
import com.loan_application.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService service;


    public UserController(UserService userService) { this.service = userService; }

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody User user){
        service.register(user);
        return new ResponseEntity<>("You have been register successfully.", HttpStatus.OK);}

    @GetMapping(value = "/all")
    public List<User> getAll(){
        return service.findAll();
    }

    @GetMapping
    public User getById(@RequestParam(value = "id") Long id){
        return service.findById(id);
    }

    @DeleteMapping(value = "/delete")
    public void deleteById(@RequestParam(value = "id") Long id){ service.deleteById(id); }
}
