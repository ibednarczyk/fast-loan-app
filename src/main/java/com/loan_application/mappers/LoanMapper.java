package com.loan_application.mappers;

import com.loan_application.domain.loan.Loan;
import com.loan_application.representation.LoanDto;

public class LoanMapper {

    public LoanMapper() {
    }


    public static LoanDto toLoanDto(Loan loan) {

        return LoanDto.builder()
                .loanId(loan.getLoanId())
                .cost(loan.getCost())
                .openDate(loan.getOpenDate())
                .dueDate(loan.getDueDate())
                .status(loan.getStatus())
                .user(loan.getUser())
                .build();
    }


}
