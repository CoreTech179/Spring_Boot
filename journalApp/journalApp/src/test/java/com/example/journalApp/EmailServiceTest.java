package com.example.journalApp;

import com.example.journalApp.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailServiceObj;

    @Test
    public void testSendEmail(){
        emailServiceObj.sendEmail("soumadeepcore2@gmail.com","Testing Mail Sender in java ","Hello ye mail mene send kiya testing ke liye");
    }

}
