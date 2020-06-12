package com.loan_application.controller;

import com.loan_application.domain.application.Application;
import com.loan_application.exceptions.ApplicationNotFoundException;
import com.loan_application.exceptions.IncorrectRequirementsException;
import com.loan_application.service.ApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService service;



    public ApplicationController (ApplicationService service) {
        this.service = service;
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




}
