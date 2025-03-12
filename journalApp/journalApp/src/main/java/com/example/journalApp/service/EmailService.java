package com.example.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSenderObj;

    public void sendEmail(String to, String subject, String body){
        try{

            SimpleMailMessage mail = new SimpleMailMessage(); // This "simpleMailMessage" is used to set the subject, where we need to send the email
//            & what is the body of the email
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);
            javaMailSenderObj.send(mail); // then with the help of java mail sender object we will send the email

        } catch (Exception e) {
            log.error("Error occurred while sending the Mail", e);
        }
    }
}
