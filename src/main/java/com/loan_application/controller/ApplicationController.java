package com.loan_application.controller;

import com.loan_application.domain.application.Application;
import com.loan_application.exceptions.ApplicationNotFoundException;
import com.loan_application.exceptions.IncorrectRequirementsException;
import com.loan_application.service.ApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService service;



    public ApplicationController (ApplicationService service) {
        this.service = service;
    }


    @GetMapping(value = "/all")
    public List<Application> getAll(){
        return service.findAll();
    }

    @GetMapping
    public Application getById(@RequestParam(value = "id") Long id) throws ApplicationNotFoundException {
        return service.findById(id);
    }

    @PostMapping(value = "/apply")
    public ResponseEntity<String> apply(@RequestBody Application application, @RequestParam Long userId) {
        service.apply(application, userId);
        return new ResponseEntity<>("Success! Application number: " + application.getAppId() + " was submitted.", HttpStatus.OK) ;
    }


    @PutMapping(value = "/verify")
    public void verify(@RequestParam(value = "id") Long id) throws ApplicationNotFoundException, IncorrectRequirementsException {
        service.verify(id);
    }

    @DeleteMapping(value = "/delete")
    public void deleteById(@RequestParam(value = "id") Long id){
        service.deleteById(id);
    }


}
