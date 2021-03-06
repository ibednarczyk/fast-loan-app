package com.loan_application.domain.loan;


import com.loan_application.domain.user.User;
import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "LOANS")
public class Loan {


    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID", unique = true)
    private Long loanId;

    @JoinColumn(name = "APPLICATION_ID", referencedColumnName = "ID")
    private Long applicationId;

    @Column(name = "COST")
    private BigDecimal cost;

    @Column(name = "OPEN_DATE")
    private LocalDateTime openDate;

    @Column(name = "DUE_DATE")
    private LocalDateTime dueDate;

    @Column(name = "STATUS")
    private String status;



    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;













}
