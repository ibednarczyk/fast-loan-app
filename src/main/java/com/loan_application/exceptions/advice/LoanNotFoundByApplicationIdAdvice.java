package com.loan_application.exceptions.advice;

import com.loan_application.exceptions.LoanNotFoundByApplicationIdException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class LoanNotFoundByApplicationIdAdvice {


    @ExceptionHandler(LoanNotFoundByApplicationIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String loanNotFoundByApplicationIdHandler(LoanNotFoundByApplicationIdException ex){
        return ex.getMessage();
    }
}
