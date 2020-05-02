package com.loan_application.domain.loan;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.loan_application.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "LOANS")
public class Loan {


    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID", unique = true)
    private Long loanId;

    @Column(name = "COST")
    private BigDecimal cost;

    @Column(name = "OPEN_DATE")
    private LocalDateTime openDate;

    @Column(name = "DUE_DATE")
    private LocalDateTime dueDate;

    @Column(name = "STATUS")
    private String status;


    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;


    private Loan(Builder builder){
        this.cost = builder.cost;
        this.openDate = builder.openDate;
        this.dueDate = builder.dueDate;
        this.status = builder.status;
        this.user = builder.user;

    }
    public static class Builder{
        private BigDecimal cost;
        private LocalDateTime openDate;
        private LocalDateTime dueDate;
        private String status;
        private User user;

        public Builder cost(BigDecimal cost){
            this.cost = cost;
            return this;

        }
        public Builder openDate(LocalDateTime openDate){
            this.openDate = openDate;
            return this;

        }
        public Builder dueDate(LocalDateTime dueDate){
            this.dueDate = dueDate;
            return this;

        }
        public Builder status(String status){
            this.status = status;
            return this;

        }
        public Builder user(User user){
            this.user= user;
            return this;

        }

        public Loan build(){ return new Loan(this);
        }

    }












}
