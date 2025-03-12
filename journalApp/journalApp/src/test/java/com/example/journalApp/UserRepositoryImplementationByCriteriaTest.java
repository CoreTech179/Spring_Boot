package com.example.journalApp;

import com.example.journalApp.repository.UserRepositoryImplementationByCriteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplementationByCriteriaTest {

    @Autowired
    private UserRepositoryImplementationByCriteria userRepositoryImplementationByCriteriaObj;

    @Test
    public void GetAllUsersOptedForSentimentAnalysis(){
        Assertions.assertNotNull(userRepositoryImplementationByCriteriaObj.getAllUsersForSentimentAnalysis());
    }
}
