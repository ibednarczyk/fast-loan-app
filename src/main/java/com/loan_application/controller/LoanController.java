package com.loan_application.controller;


import com.loan_application.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.loan_application.service.LoanService;



@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService service;

    public LoanController(LoanService service) { this.service = service;
    }


    @PutMapping(value = "/extend")
    public ResponseEntity<String> extend(@RequestParam("id") Long id, @RequestParam("days") Integer days) throws IncorrectRequirementsException, IncorrectLoanStatusException {
       return service.extend(id, days);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> create (@RequestParam(value = "appId") Long appId) throws IncorrectApplicationStatusException {
       return service.create(appId);
    }



}
