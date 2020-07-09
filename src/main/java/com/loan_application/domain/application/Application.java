package com.loan_application.domain.application;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.loan_application.domain.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
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


    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
