package com.loan_application.service;


import com.loan_application.config.AppConfiguration;
import com.loan_application.email.service.EmailApplicationService;
import com.loan_application.domain.application.Application;
import com.loan_application.domain.user.User;
import com.loan_application.exceptions.ApplicationNotFoundException;
import com.loan_application.exceptions.IncorrectRequirementsException;
import com.loan_application.mappers.ApplicationMapper;
import com.loan_application.repository.UsersRepository;
import com.loan_application.representation.ApplicationDto;
import com.loan_application.status.ApplicationStatus;
import com.loan_application.repository.ApplicationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.loan_application.mappers.ApplicationMapper.mapToApplication;

@Service
public class ApplicationService {
    private ApplicationsRepository repository;
    private AppConfiguration configuration;
    private UsersRepository usersRepository;
    private EmailApplicationService emailService;

    @Autowired
    public ApplicationService(AppConfiguration configuration, ApplicationsRepository repository,
                              UsersRepository usersRepository, EmailApplicationService emailService) {
        this.configuration = configuration;
        this.repository = repository;
        this.usersRepository = usersRepository;
        this.emailService = emailService;
    }

    public ApplicationDto findById(Long id) throws ApplicationNotFoundException {
        return  repository.findById(id).map(ApplicationMapper::mapToApplicationDto)
                .orElseThrow(() -> new ApplicationNotFoundException(id));
    }

    public List<ApplicationDto> findAll(){
        return repository.findAll().stream()
                .map(ApplicationMapper :: mapToApplicationDto)
                .collect(Collectors.toList());
    }


    public void apply (ApplicationDto applicationDto, Long userId) {
        Optional<User> optionalUser = Objects.requireNonNull(usersRepository.findById(userId));
        User user = optionalUser.orElseThrow(IllegalArgumentException::new);
        Application application = mapToApplication(applicationDto);
        application.setSubmissionDate(LocalDateTime.now());
        application.setStatus(ApplicationStatus.NEW);
        application.setUser(user);
        application = repository.save(application);
        applicationDto.setAppId(application.getAppId());
        applicationDto.setStatus(application.getStatus());
        applicationDto.setSubmissionDate(application.getSubmissionDate());
        emailService.send(userId);
    }



    public void verify(Long id) throws ApplicationNotFoundException, IncorrectRequirementsException {
       Optional<Application> application = Objects.requireNonNull(repository.findById(id));
        if (application.isPresent() && application.get().getPrincipal().compareTo(configuration.getMaxPrincipal()) >= 0 &&
                application.get().getSubmissionDate().toLocalTime().isAfter(configuration.getMinTime()) ||
                application.isPresent() && application.get().getSubmissionDate().toLocalTime().isBefore(configuration.getMaxTime())) {

            application.get().setStatus(ApplicationStatus.REJECTED);
            repository.save(application.get());
            throw new IncorrectRequirementsException();

        } else if ((application.isPresent() && !ApplicationStatus.REJECTED.equals(application.get().getStatus())) &&
                application.get().getPrincipal().compareTo(configuration.getMinPrincipal()) < 0 ||
                application.isPresent() && application.get().getPrincipal().compareTo(configuration.getMinPrincipal()) < 0 ||
                application.isPresent() && application.get().getPrincipal().compareTo(configuration.getMaxPrincipal()) > 0 ||
                application.isPresent() && application.get().getTerm() < configuration.getMinTerm() ||
                application.isPresent() && application.get().getTerm() > configuration.getMaxTerm()) {

            application.get().setStatus(ApplicationStatus.REJECTED);
            repository.save(application.get());
            throw new IncorrectRequirementsException();
        } else if (application.isPresent()) {
            application.get().setStatus(ApplicationStatus.ACCEPTED);
            repository.save(application.get());
        }
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }


}
