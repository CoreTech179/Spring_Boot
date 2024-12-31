package com.example.journalApp.repository;

import com.example.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
    //    this interface will run queries from database
//      MongoRepository<mongodb-collection-Name, primary-key-datatype>
}

// basically MongoRepository is an interface provided by SpringMongodb to perform standard CURD Operations
