package com.example.journalApp.scheduler;

import com.example.journalApp.cache.ApplicationCache;
import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.entity.User;
import com.example.journalApp.repository.UserRepositoryImplementationByCriteria;
import com.example.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailServiceObj;

    @Autowired
    private UserRepositoryImplementationByCriteria userRepositoryImplementationByCriteriaObj;

    @Autowired
    private ApplicationCache applicationCacheObj;

    @Scheduled(cron = "0 0 9 * * SUN") // Now by using this annotation this function will be run automatically at every week 9AM sunday. Because we have defined cron expression for 9Am Sunday
//    Now also we need to tell the spring we have this type of scheduled function --> for that we have to defined @EnableScheduling annotation inside the Main function
    public void fetchUsersAndSendSentimentAnalysisEmail(){
        List<User> users = userRepositoryImplementationByCriteriaObj.getAllUsersForSentimentAnalysis();
        for(User user : users){
            List<JournalEntry> journalEntries = user.getUsersJournalEntries();
//           we do Filter using stream because the entry contains unnecessary things also like date,etc.
            List<String> filteredList = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minusDays(7))).map(x -> x.getContent()).toList();
            String singleEntry = String.join(" ",filteredList);
//            String getSentimentResponse = sentimentAnalysisServiceObj.getSentiment(singleEntry);
//            emailServiceObj.sendEmail(user.getEmail(),"Sentiment for last 7 day's",getSentimentResponse);
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
        applicationCacheObj.init();
    }

}
