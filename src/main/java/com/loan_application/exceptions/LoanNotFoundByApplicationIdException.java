package com.loan_application.exceptions;

public class LoanNotFoundByApplicationIdException extends RuntimeException {

    public LoanNotFoundByApplicationIdException(Long id) {
        super("No loan found to application with id: "+ id);
    }
}
