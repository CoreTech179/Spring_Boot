package com.example.journalApp.controller;

import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.entity.User;
import com.example.journalApp.service.JournalEntryService;
import com.example.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService JournalEntryServiceObj;

    @Autowired
    private UserService userService;

    //    Getting all the journal Entries of a particular user not everything
    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllDataOfUser(@PathVariable String userName){

        User user = userService.findByUsername(userName);

        List<JournalEntry> entireUserData = user.getUsersJournalEntries();

        if(entireUserData!= null && !entireUserData.isEmpty()){
            return new ResponseEntity<>(entireUserData, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntry> createAndSendJournal(@RequestBody JournalEntry receiveData, @PathVariable String userName){

//        Here a small flaw is there that is when we will create a new entry and try to save that entry and if some exception occurs at the
//        saveData() function then as a result journalEntry get saved inside the database but its reference is not stored inside user collection
//        therefore inconsistency occurs therefore we use a annotation on the saveData() function that is --> @Transactional
//        @Transactional annotation will treat each and every operation equally. Means if oen of the operation failed then the entire function will be treated as failed
//        and all the data that is save inside the database get rolled back.

//        We have to write @EnableTransactionManagement annotation on the main function --> this annotation will search for all the methods that is having
//        transactional annotation and keep a track of it.

        try{
            receiveData.setDate(LocalDateTime.now());
            JournalEntryServiceObj.saveData(receiveData, userName);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

//    From today onwards 21/12/2024 we are using mongodb Atlas a clod based database for practicing mongodb rather than our local machine
//    Login mongodb atlas on brave

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId myId){

        Optional<JournalEntry> journalData = JournalEntryServiceObj.getDataById(myId);

        if(journalData.isPresent()){
            return new ResponseEntity<>(journalData.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/id/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId myId, @PathVariable String userName){
//        Now here problem is that if we delete a journal from the journalEntries collection of mongodb then it will delete that journalEntry but
//        the reference of that journalEntry is still stored at the users collection of mongodb
//        Therefore now we need to perform such actions that when we delete a particular entry from the journalEntries collection then the reference is also get removed
        try{
            JournalEntryServiceObj.deleteById(myId, userName);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/id/{userName}/{myId}")
    public ResponseEntity<JournalEntry> modifyJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry, @PathVariable String userName){

        JournalEntry oldData = JournalEntryServiceObj.getDataById(myId).orElse(null);
//        Here we are just retrive the old data by its id and update the new entry inside the database that's it

        try {

            if(oldData != null){
                oldData.setTitle(newEntry.getTitle() != null && newEntry.getTitle().isBlank() == false ? newEntry.getTitle() : oldData.getTitle());
                oldData.setContent(newEntry.getContent() != null && newEntry.getContent().isBlank() == false ? newEntry.getContent() : oldData.getContent());
            }

            JournalEntryServiceObj.updateExistingData(oldData);
            return new ResponseEntity<>(oldData, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

    }

}
