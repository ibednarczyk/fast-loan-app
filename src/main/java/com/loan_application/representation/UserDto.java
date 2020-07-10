package com.loan_application.representation;


import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private List<ApplicationDto> applicationsList;
    @JsonManagedReference
    private List<LoanDto> loansList;

}
