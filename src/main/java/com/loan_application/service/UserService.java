package com.loan_application.service;

import com.loan_application.email.service.EmailRegistrationService;
import com.loan_application.domain.user.User;
import com.loan_application.exceptions.UserNotFoundException;
import com.loan_application.mappers.UserMapper;
import com.loan_application.repository.UsersRepository;
import com.loan_application.representation.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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


    public UserDto findById(Long id){

        return repository.findById(id).map(UserMapper::mapToUserDto)
                .orElseThrow(()-> new UserNotFoundException(id));
    }

    public List<UserDto> findAll(){
        return repository.findAll().stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public ResponseEntity<String> register(UserDto userDto){
       userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
       userDto.setActive(true);
       User user = UserMapper.mapToUser(userDto);
        repository.save(user);
        emailRegistrationService.send(user.getUserId());
        return new ResponseEntity<>("You have been register successfully!, Your id: " + user.getUserId(), HttpStatus.CREATED);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
