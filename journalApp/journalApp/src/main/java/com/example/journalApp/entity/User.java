package com.example.journalApp.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
public class User {

    @Id
    private ObjectId id;
    @Indexed(unique = true) // By using this annotation searching will be faster on the userName field and all the userNames must be unique
//    therfore we have written (unique = true)
    // Here automatically indexing is not going to happen therefore we have to tell the spring boot to do auto indexing at application properties folder.
    @NonNull // This annotation states that userName should not be null
    private String userName;
    @NonNull
    private String password;

    @DBRef // This annotation is creating a reference inside user collection of JournalEntry
    private List<JournalEntry> usersJournalEntries = new ArrayList<>();

    private List<String> roles;

}
