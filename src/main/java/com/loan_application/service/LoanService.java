package com.loan_application.service;


import com.loan_application.config.AppConfiguration;
import com.loan_application.domain.application.Application;
import com.loan_application.domain.loan.Loan;
import com.loan_application.exceptions.*;
import com.loan_application.status.ApplicationStatus;
import com.loan_application.status.LoanStatus;
import com.loan_application.repository.LoansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class LoanService {
    private LoansRepository repository;
    private ApplicationService service;
    private AppConfiguration configuration;
    private BigDecimal total;


    @Autowired
    public LoanService(LoansRepository repository, ApplicationService applicationService, AppConfiguration configuration) {
        this.repository = repository;
        this.service = applicationService;
        this.configuration = configuration;
        this.total = new BigDecimal(BigInteger.ZERO);
    }

    public List<Loan> findAll(){
        return repository.findAll();
    }

    public Loan findById (Long id) throws LoanNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException(id));
    }

    public Loan findByApplicationId (Long applicationId) throws LoanNotFoundByApplicationIdException {
        return repository.findById(applicationId)
                .orElseThrow(() -> new LoanNotFoundByApplicationIdException(applicationId));
    }




    public ResponseEntity<String> create(Long applicationId) throws IncorrectApplicationStatusException {
        Application application = Objects.requireNonNull(service.findById(applicationId));
        Loan loan = new Loan();
        if (!ApplicationStatus.ACCEPTED.equals(application.getStatus())){
            throw new IncorrectApplicationStatusException(applicationId);
        } else {
            loan.setStatus(LoanStatus.VALID);
            loan.setOpenDate(LocalDateTime.now());
            loan.setDueDate(loan.getOpenDate().plusDays(application.getTerm()));
            loan.setCost(calculate(applicationId));
            loan.setUser(application.getUser());
            repository.save(loan);
        }
        return new ResponseEntity<>("Success! Loan number: " + loan.getLoanId() + " was created.", HttpStatus.OK);

    }

    private BigDecimal calculate (Long applicationId) {
        Application application = Objects.requireNonNull(service.findById(applicationId));
        BigDecimal cost = application.getPrincipal().multiply(configuration.getCommission());
        this.total = cost.setScale(2, RoundingMode.CEILING);
        return this.total;
    }

    public ResponseEntity<String> extend (Long loanId, Integer extensionDays) throws IncorrectRequirementsException, IncorrectLoanStatusException {
        Loan loan = Objects.requireNonNull(findById(loanId));
        if (LoanStatus.EXTENDED.equals(loan.getStatus())){
            throw new IncorrectLoanStatusException(loanId);
        } else if (!configuration.getExtension().contains(extensionDays)) {
            throw new IncorrectRequirementsException();
        } else {
            LocalDateTime extended = loan.getDueDate().plusDays(extensionDays);
            loan.setDueDate(extended);
            loan.setStatus(LoanStatus.EXTENDED);
            repository.save(loan);
            return new ResponseEntity<>("Due date of loan number: " + loanId +
                    " was changed successfully. New due date: " + extended + ".", HttpStatus.OK);
        }
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }





    


}



