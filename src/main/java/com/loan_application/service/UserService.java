package com.loan_application.service;

import com.loan_application.email.service.EmailRegistrationService;
import com.loan_application.domain.user.User;
import com.loan_application.exceptions.UserNotFoundException;
import com.loan_application.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UsersRepository repository;
    private EmailRegistrationService emailRegistrationService;

    @Autowired
    public UserService(UsersRepository repository, EmailRegistrationService registrationService) {
        this.repository = repository;
        this.emailRegistrationService = registrationService;
    }


    public User findById(Long id){
        return repository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
    }

    public List<User> findAll(){
        return repository.findAll();
    }

    public void register(User user){
        new User.Builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
        repository.save(user);
        emailRegistrationService.send(user.getUserId());
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
