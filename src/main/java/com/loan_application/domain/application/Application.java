package com.loan_application.domain.application;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.loan_application.domain.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "APPLICATIONS")
public class Application {


    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID", unique = true)
    private Long appId;

    @Column(name = "PRINCIPAL")
    private BigDecimal principal;

    @Column(name = "TERM")
    private Integer term;

    @Column(name = "SUBMISSION_DATE")
    private LocalDateTime submissionDate;

    @Column(name = "STATUS")
    private String status;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public static class Builder {
        private BigDecimal principal;
        private Integer term;
        private LocalDateTime submissionDate;
        private String status;
        private User user;

        public Builder principal(BigDecimal principal){
            this.principal = principal;
            return this;
        }
        public Builder term(Integer term){
            this.term = term;
            return this;
        }
        public Builder submissionDate(LocalDateTime submissionDate){
            this.submissionDate = submissionDate;
            return this;
        }
        public Builder status(String status){
            this.status = status;
            return this;
        }
        public Builder user(User user){
            this.user = user;
            return this;
        }
        public Application build(){
            return new Application(principal, term, submissionDate, status, user);
        }
    }


    private Application(BigDecimal principal, Integer term, LocalDateTime submissionDate, String status, User user) {
        this.principal = principal;
        this.term = term;
        this.submissionDate = submissionDate;
        this.status = status;
        this.user = user;
    }
}
