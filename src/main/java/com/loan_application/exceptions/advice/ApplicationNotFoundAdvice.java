package com.loan_application.exceptions.advice;

import com.loan_application.exceptions.ApplicationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApplicationNotFoundAdvice {


    @ExceptionHandler(ApplicationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String applicationNotFoundHandler(ApplicationNotFoundException ex){
        return ex.getMessage();
    }

}
