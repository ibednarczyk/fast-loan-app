package com.loan_application.exceptions;

public class ApplicationNotFoundException extends RuntimeException {
    public ApplicationNotFoundException(Long id) {
        super("No application found with id: " + id);
    }
}
