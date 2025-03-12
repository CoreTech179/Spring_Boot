package com.example.journalApp.service;

import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.entity.User;
import com.example.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

//@RestController
@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepositoryObj;

    @Autowired
    private UserService userServiceObj;

    @Transactional
    public void saveData(JournalEntry entry, String userName){
        User user = userServiceObj.findByUsername(userName); // 1st finding out the user
        JournalEntry getSavedJournalEntryId = journalEntryRepositoryObj.save(entry);
//        First here we have save the journalEntry inside the database of a particular user
        // 2nd here we will get the id of the saved journalEntry of that particular user that is saved inside the database
        user.getUsersJournalEntries().add(getSavedJournalEntryId); // here we have saved the id of that entry inside the List
        userServiceObj.saveUser(user); // here we are saving that particular user everytime when a new entry is added inside the db for that user
    }

    public void updateExistingData(JournalEntry entry){
        journalEntryRepositoryObj.save(entry);
    }

    public List<JournalEntry> getAllData(){
        return journalEntryRepositoryObj.findAll();
    }

    public Optional<JournalEntry> getDataById(ObjectId id){
        return journalEntryRepositoryObj.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName){

        boolean removed = false;
        try{
            User user = userServiceObj.findByUsername(userName);
            boolean removalId = user.getUsersJournalEntries().removeIf(x -> x.getId().equals(id));
    //        this x --> x.getId().equals(id) means =  x --> it will iterate all the indivisual data inside the list
    //        x.getId().equals(id) --> from each data it will extract the id and check if it is equals to the provided id or not, if match delete or else skip
            if(removalId == true){
                userServiceObj.saveUser(user); // save the updated user after deletion
                journalEntryRepositoryObj.deleteById(id); // then also delete that particular journalEntry from the journal_entries collection
            }

            return removed;

        } catch (Exception e) {

            throw new RuntimeException("An error occurred while deleting a specific entry" + e);

        }

    }

}
