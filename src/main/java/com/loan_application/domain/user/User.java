package com.loan_application.domain.user;


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
@Builder
@Table(name = "USERS")
public class User {

    @Id
    @NotNull
    @GeneratedValue
    @Column(name = "ID", unique = true)
    private Long userId;

    @Column(name = "USERNAME", nullable = false)
    private String userName;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "NUMBER")
    private String phoneNumber;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "ACTIVE")
    private boolean active;



    @OneToMany(
            targetEntity = Application.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Application> applicationsList;


    @OneToMany(
            targetEntity = Loan.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Loan> loansList;


}
