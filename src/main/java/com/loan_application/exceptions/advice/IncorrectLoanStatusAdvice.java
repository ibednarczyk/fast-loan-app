package com.loan_application.exceptions.advice;

import com.loan_application.exceptions.IncorrectLoanStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class IncorrectLoanStatusAdvice {


    @ExceptionHandler(IncorrectLoanStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String incorrectStatusHandler(IncorrectLoanStatusException ex){
        return ex.getMessage();
    }
}
