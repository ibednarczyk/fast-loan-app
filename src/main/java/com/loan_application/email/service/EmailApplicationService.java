package com.loan_application.email.service;

import com.loan_application.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


@Service
public class EmailApplicationService implements SimpleEmailService {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailApplicationService.class);

    private JavaMailSender javaMailSender;
    private UsersRepository repository;




    @Autowired
    public EmailApplicationService(JavaMailSender javaMailSender, UsersRepository repository) {

        this.javaMailSender = javaMailSender;
        this.repository = repository;

    }

    @Override
    public void send(Long userId) {
        try {
            MimeMessage mailMessage = create(userId);
            javaMailSender.send(mailMessage);
        } catch (MessagingException e){
            LOGGER.error("Fail to send an email " + e.getMessage(), e);
        }

    }

    @Override
    public MimeMessage create(Long userId) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        if(repository.findById(userId).isPresent()){
        helper.setTo(repository.findById(userId).get().getEmail());}
        helper.setFrom("project.codingit@gmail.com");
        helper.setSubject("Your application has been accepted!");
        helper.setText("Dear " + repository.findById(userId).get().getFirstName() + ", \n" +
                "we kindly inform you, that your application has been accepted." + "\n" +
                "Please sing in attached documents to finish the process of creating a loan.");

        FileSystemResource file = new FileSystemResource(new File("/Users/i.bednarczyk/desktop/contract.pdf"));
        helper.addAttachment("Contract", file);

        return mimeMessage;

    }

}
