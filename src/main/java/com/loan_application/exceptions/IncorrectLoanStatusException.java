package com.loan_application.exceptions;

public class IncorrectLoanStatusException extends RuntimeException {
    public IncorrectLoanStatusException(Long id){
        super("Loan number: " + id + " has already been extended. Cannot extend the loan again.");

    }
}
