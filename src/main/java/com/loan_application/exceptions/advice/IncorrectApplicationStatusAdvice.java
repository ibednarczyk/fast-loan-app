package com.loan_application.exceptions.advice;

import com.loan_application.exceptions.IncorrectApplicationStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class IncorrectApplicationStatusAdvice {

    @ExceptionHandler(IncorrectApplicationStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String incorrectApplicationStatusAdvice(IncorrectApplicationStatusException ex){
        return ex.getMessage();
    }
}
