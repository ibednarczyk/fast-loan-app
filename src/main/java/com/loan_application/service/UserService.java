package com.loan_application.service;

import com.loan_application.email.service.EmailRegistrationService;
import com.loan_application.domain.user.User;
import com.loan_application.exceptions.UserNotFoundException;
import com.loan_application.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UsersRepository repository;
    private EmailRegistrationService emailRegistrationService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UsersRepository repository, EmailRegistrationService registrationService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.emailRegistrationService = registrationService;
        this.passwordEncoder = passwordEncoder;
    }


    public User findById(Long id){
        return repository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
    }

    public List<User> findAll(){
        return repository.findAll();
    }

    public void register(User user){
       user.setPassword(passwordEncoder.encode(user.getPassword()));
        User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUserName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
        repository.save(user);
        emailRegistrationService.send(user.getUserId());
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
