package com.loan_application.controller;


import com.loan_application.domain.loan.Loan;
import com.loan_application.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.loan_application.service.LoanService;

import java.util.List;


@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService service;

    public LoanController(LoanService service) { this.service = service;
    }

    @GetMapping(value = "/all")
    public List<Loan> getAll(){
        return service.findAll();
    }

    @GetMapping
    public Loan getById(@RequestParam(value = "id") Long id) throws LoanNotFoundException {
        return service.findById(id);
    }

    @GetMapping(value = "/appId/{id}")
    public Loan getLoanByApplicationId(@PathVariable Long id) throws LoanNotFoundByApplicationIdException {
        return service.findByApplicationId(id);
    }


    @PutMapping(value = "/extend")
    public ResponseEntity<String> extend(@RequestParam("id") Long id, @RequestParam("days") Integer days) throws IncorrectRequirementsException, IncorrectLoanStatusException {
       return service.extend(id, days);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> create (@RequestParam(value = "appId") Long appId) throws IncorrectApplicationStatusException {
       return service.create(appId);
    }


    @DeleteMapping(value = "/delete")
    public void deleteById(@RequestParam(value = "id") Long id){
        service.deleteById(id);
    }

}
