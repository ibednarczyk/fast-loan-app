package com.loan_application.exceptions;

public class IncorrectRequirementsException extends RuntimeException {
    public IncorrectRequirementsException() {
        super("Incompatible requirements. Your request has been rejected.");
    }
}
