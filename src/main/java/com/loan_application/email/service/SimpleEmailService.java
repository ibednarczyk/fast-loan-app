package com.loan_application.email.service;

import org.springframework.mail.SimpleMailMessage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


public interface SimpleEmailService {

    void send(Long userId);

    MimeMessage create(Long userId) throws MessagingException;

}
