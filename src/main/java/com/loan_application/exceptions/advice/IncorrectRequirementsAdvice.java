package com.loan_application.exceptions.advice;


import com.loan_application.exceptions.IncorrectRequirementsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class IncorrectRequirementsAdvice {



    @ExceptionHandler(IncorrectRequirementsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String incorrectRequirementsHandler(IncorrectRequirementsException ex){
        return ex.getMessage();

    }
}
