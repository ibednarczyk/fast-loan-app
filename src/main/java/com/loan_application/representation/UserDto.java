package com.loan_application.representation;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.loan_application.domain.application.Application;
import com.loan_application.domain.loan.Loan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserDto {


    private Long userId;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String role;
    private boolean active;
    @JsonManagedReference
    private List<Application> applicationsList;
    @JsonManagedReference
    private List<Loan> loansList;

}
