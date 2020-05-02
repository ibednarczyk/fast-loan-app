package com.loan_application.domain.user;

import com.fasterxml.jackson.annotation.*;
import com.loan_application.domain.application.Application;
import com.loan_application.domain.loan.Loan;
import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @NotNull
    @GeneratedValue
    @Column(name = "ID", unique = true)
    private Long userId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "NUMBER")
    private String phoneNumber;


    @JsonManagedReference
    @OneToMany(
            targetEntity = Application.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Application> applicationsList;

    @JsonManagedReference
    @OneToMany(
            targetEntity = Loan.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Loan> loansList;

    private User(Builder builder){
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.applicationsList = builder.applicationsList;
        this.loansList = builder.loansList;

    }
    public static class Builder{
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private List<Application> applicationsList;
        private List<Loan> loansList;



        public Builder firstName(String firstName){
            this.firstName = firstName;
            return this;

        }
        public Builder lastName(String lastName){
            this.lastName = lastName;
            return this;

        }
        public Builder email(String email){
            this.email = email;
            return this;

        }
        public Builder phoneNumber(String phoneNumber){
            this.phoneNumber = phoneNumber;
            return this;

        }
        public Builder applicationsList(List<Application> applicationsList){
            this.applicationsList= applicationsList;
            return this;

        }
        public Builder loansList(List<Loan> loansList){
            this.loansList = loansList;
            return this;

        }
        public User build(){
            return new User(this);
        }

    }
}
