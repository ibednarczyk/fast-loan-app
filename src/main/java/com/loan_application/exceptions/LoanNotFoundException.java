package com.loan_application.exceptions;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(Long id) {
        super("No loan found with id: " + id);
    }
}
