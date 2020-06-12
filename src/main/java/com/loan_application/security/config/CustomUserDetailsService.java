package com.loan_application.security.config;

import com.loan_application.domain.user.User;
import com.loan_application.repository.UsersRepository;
import com.loan_application.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UsersRepository usersRepository;

    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByUserName(username);
        user.orElseThrow(()-> new UsernameNotFoundException("User " + username + " not found"));
        return user.map(CustomUserDetails::new).get();
    }
}
