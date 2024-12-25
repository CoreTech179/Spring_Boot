package com.example.journalApp.repository;

import com.example.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    //    this interface will run queries from database

    User findByUserName(String username);

    void deleteByUserName(String username);

}

// basically MongoRepository is a interface provided by SpringMongodb to perform standard CURD Operations
