package com.example.journalApp.repository;

import com.example.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepositoryImplementationByCriteria {

    @Autowired
    private MongoTemplate mongoTemplateObj;

    public List<User> getAllUsersForSentimentAnalysis(){
        Query query = new Query();
//        We are using criteria because somehow we want only that users who have opted for sentiment Analysis and email is not null
        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")); // written the criteria but now if we want to execute the query we will use Mongo-template class of mongodb
//        Regex will check automatically weather you have entered a correct email id or not
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true)); // Here "AND" operator is automatically implemented because both the criteria is written differently.
        List<User> users = mongoTemplateObj.find(query, User.class);
        return users;
    }
}
