package com.loan_application.controller;

import com.loan_application.domain.application.Application;
import com.loan_application.domain.loan.Loan;
import com.loan_application.domain.user.User;
import com.loan_application.exceptions.ApplicationNotFoundException;
import com.loan_application.exceptions.LoanNotFoundByApplicationIdException;
import com.loan_application.exceptions.LoanNotFoundException;
import com.loan_application.service.ApplicationService;
import com.loan_application.service.LoanService;
import com.loan_application.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private UserService userService;
    private ApplicationService applicationService;
    private LoanService loanService;

    public AdminController(UserService userService, ApplicationService applicationService, LoanService loanService) {
        this.userService = userService;
        this.applicationService = applicationService;
        this.loanService = loanService;
    }

    @GetMapping(value = "/user/all")
    public List<User> getAllUsers(){
        return userService.findAll();
    }

    @GetMapping(value = "/user")
    public User getUserById(@RequestParam(value = "id") Long id){
        return userService.findById(id);
    }

    @DeleteMapping(value = "/user/delete")
    public void deleteUserById(@RequestParam(value = "id") Long id){ userService.deleteById(id); }


    @GetMapping(value = "/application/all")
    public List<Application> getAllApplications(){
        return applicationService.findAll();
    }

    @GetMapping(value = "/application")
    public Application getApplicationById(@RequestParam(value = "id") Long id) throws ApplicationNotFoundException {
        return applicationService.findById(id);
    }

    @DeleteMapping(value = "/application/delete")
    public void deleteById(@RequestParam(value = "id") Long id){
        applicationService.deleteById(id);
    }


    @GetMapping(value = "/loan/all")
    public List<Loan> getAllLoans(){
        return loanService.findAll();
    }

    @GetMapping(value = "/loan")
    public Loan getLoanById(@RequestParam(value = "id") Long id) throws LoanNotFoundException {
        return loanService.findById(id);
    }

    @GetMapping(value = "/loan/appId/{id}")
    public Loan getLoanByApplicationId(@PathVariable Long id) throws LoanNotFoundByApplicationIdException {
        return loanService.findByApplicationId(id);
    }

    @DeleteMapping(value = "/loan/delete")
    public void deleteLoanById(@RequestParam(value = "id") Long id){
        loanService.deleteById(id);
    }

}
