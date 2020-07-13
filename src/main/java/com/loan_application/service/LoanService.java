package com.loan_application.service;



import com.loan_application.config.AppConfiguration;
import com.loan_application.domain.application.Application;
import com.loan_application.domain.loan.Loan;
import com.loan_application.exceptions.*;
import com.loan_application.mappers.LoanMapper;
import com.loan_application.repository.ApplicationsRepository;
import com.loan_application.representation.LoanDto;
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
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class LoanService {
    private LoansRepository repository;
    private ApplicationsRepository applicationsRepository;
    private AppConfiguration configuration;
    private BigDecimal total;


    @Autowired
    public LoanService(LoansRepository repository, ApplicationsRepository applicationsRepository, AppConfiguration configuration) {
        this.repository = repository;
        this.applicationsRepository = applicationsRepository;
        this.configuration = configuration;
        this.total = new BigDecimal(BigInteger.ZERO);
    }

    public List<LoanDto> findAll(){
        return repository.findAll().stream()
                .map(LoanMapper::toLoanDto)
                .collect(Collectors.toList());
    }

    public LoanDto findById (Long id) throws LoanNotFoundException {
        return repository.findById(id).map(LoanMapper::toLoanDto)
                .orElseThrow(() -> new LoanNotFoundException(id));
    }

    public LoanDto findByApplicationId (Long applicationId) {
        return repository.findByApplicationId(applicationId).map(LoanMapper::toLoanDto)
                .orElseThrow(() -> new LoanNotFoundByApplicationIdException(applicationId));
    }




    public ResponseEntity<String> create(Long applicationId) {
        Optional<Application> optionalApplication = Objects.requireNonNull(applicationsRepository.findById(applicationId));
        Application application = optionalApplication.orElseThrow(IllegalArgumentException::new);
        Loan loan = new Loan();
        validateApplicationStatus(application.getStatus(), applicationId);
        loan.setStatus(LoanStatus.VALID);
        loan.setOpenDate(LocalDateTime.now());
        loan.setDueDate(loan.getOpenDate().plusDays(application.getTerm()));
        loan.setCost(calculate(applicationId));
        loan.setUser(application.getUser());
        loan.setApplicationId(applicationId);
        repository.save(loan);

        return new ResponseEntity<>("Success! Loan number: " + loan.getLoanId() + " was created.", HttpStatus.OK);

    }

    private BigDecimal calculate (Long applicationId) {
        Optional<Application> optionalApplication = Objects.requireNonNull(applicationsRepository.findById(applicationId));
        Application application = optionalApplication.orElseThrow(IllegalArgumentException::new);
        BigDecimal cost = application.getPrincipal().multiply(configuration.getCommission());
        this.total = cost.setScale(2, RoundingMode.CEILING);
        return this.total;
    }

    public ResponseEntity<String> extend (Long loanId, Integer extensionDays) {
        Optional<Loan> loanOptional = Objects.requireNonNull(repository.findById(loanId));
        loanOptional.ifPresent(loan -> validateLoanStatus(loan.getStatus(), loanId));
        Loan loan = loanOptional.orElseThrow(IllegalArgumentException::new);
        if (!configuration.getExtension().contains(extensionDays)) {
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


    private void validateLoanStatus(String actual, Long loanId){
        if (LoanStatus.EXTENDED.equals(actual)) {
            throw new IncorrectLoanStatusException(loanId);
        }
    }

    private void validateApplicationStatus(String actual, Long appId){
        if (!ApplicationStatus.ACCEPTED.equals(actual)) {
            throw new IncorrectApplicationStatusException(appId);
        }
    }





    


}



