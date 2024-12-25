package com.example.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

//@RestController
@Document(collection = "journal_entries")
// This annotation will map this class to a mongodb table (i.e.collection)
// here table name is journal_entries

// @Getter // This annotation is written using lombok --> basically this annotation means it will automatically generate GETTER for you during compilation of the code
// @Setter // This annotation is written using lombok --> basically this annotation means it will automatically generate SETTER for you during compilation of the code
@Data
@NoArgsConstructor
public class JournalEntry {

    @Id // This annotation represents id is unique inside the table (collection)
    private ObjectId id; // ObjectId is a datatype of mongodb
    @NonNull
    private String title;
    private String content;

    private LocalDateTime date;


}
