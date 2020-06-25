package com.loan_application.representation;

import com.loan_application.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class LoanDto {


    private Long loanId;
    private BigDecimal cost;
    private LocalDateTime openDate;
    private LocalDateTime dueDate;
    private String status;
    private User user;

}
