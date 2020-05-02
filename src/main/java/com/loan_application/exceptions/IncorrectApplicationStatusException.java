package com.loan_application.exceptions;

public class IncorrectApplicationStatusException extends Throwable {
    public IncorrectApplicationStatusException(Long id) {
        super("Cannot create a loan. Application number: " + id + " has been rejected or not accepted yet.");
    }
}
