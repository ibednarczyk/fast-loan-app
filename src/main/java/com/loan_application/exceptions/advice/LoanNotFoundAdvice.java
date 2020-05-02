package com.loan_application.exceptions.advice;

import com.loan_application.exceptions.LoanNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class LoanNotFoundAdvice {


    @ExceptionHandler(LoanNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String loanNotFoundHandler(LoanNotFoundException ex){
        return ex.getMessage();
    }
}
