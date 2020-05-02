package com.loan_application.service;


import com.loan_application.config.AppConfiguration;
import com.loan_application.email.service.EmailApplicationService;
import com.loan_application.domain.application.Application;
import com.loan_application.domain.user.User;
import com.loan_application.exceptions.ApplicationNotFoundException;
import com.loan_application.exceptions.IncorrectRequirementsException;
import com.loan_application.status.ApplicationStatus;
import com.loan_application.repository.ApplicationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ApplicationService {
    private ApplicationsRepository repository;
    private AppConfiguration configuration;
    private UserService service;
    private EmailApplicationService emailService;

    @Autowired
    public ApplicationService(AppConfiguration configuration, ApplicationsRepository repository,
                              UserService service, EmailApplicationService emailService) {
        this.configuration = configuration;
        this.repository = repository;
        this.service = service;
        this.emailService = emailService;

    }

    public Application findById(Long id) throws ApplicationNotFoundException {
        return  repository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));
    }

    public List<Application> findAll(){
        return repository.findAll();
    }


    public void apply (Application application, Long userId) {
        User user = Objects.requireNonNull(service.findById(userId));
        new Application.Builder()
                .principal(application.getPrincipal())
                .term(application.getTerm())
                .build();
        application.setSubmissionDate(LocalDateTime.now());
        application.setStatus(ApplicationStatus.NEW);
        application.setUser(user);
        repository.save(application);
        emailService.send(userId);
    }



    public void verify(Long id) throws ApplicationNotFoundException, IncorrectRequirementsException {
       Application application = Objects.requireNonNull(findById(id));
        if (application.getPrincipal().compareTo(configuration.getMaxPrincipal()) >= 0 &&
                application.getSubmissionDate().toLocalTime().isAfter(configuration.getMinTime()) ||
                application.getSubmissionDate().toLocalTime().isBefore(configuration.getMaxTime())) {

            application.setStatus(ApplicationStatus.REJECTED);
            repository.save(application);
            throw new IncorrectRequirementsException();

        } else if ((!ApplicationStatus.REJECTED.equals(application.getStatus())) &&
                application.getPrincipal().compareTo(configuration.getMinPrincipal()) < 0 ||
                application.getPrincipal().compareTo(configuration.getMinPrincipal()) < 0 ||
                application.getPrincipal().compareTo(configuration.getMaxPrincipal()) > 0 ||
                application.getTerm() < configuration.getMinTerm() ||
                application.getTerm() > configuration.getMaxTerm()) {

            application.setStatus(ApplicationStatus.REJECTED);
            repository.save(application);
            throw new IncorrectRequirementsException();
        } else {
            application.setStatus(ApplicationStatus.ACCEPTED);
            repository.save(application);
        }
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }


}
