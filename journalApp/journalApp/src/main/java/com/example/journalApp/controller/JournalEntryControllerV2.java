package com.example.journalApp.controller;

import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.entity.User;
import com.example.journalApp.service.JournalEntryService;
import com.example.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService JournalEntryServiceObj;

    @Autowired
    private UserService userService;

    //    Getting all the journal Entries of a particular user not everything

//    Now since we have secured the journal endpoint we cannot pass the username in the path itself.
//    However, when we hit this endpoint with correct username and password it can automatically get the username which is stored at securityContext Holder.
//    From 26/12/2024 we are removing giving the username on the path variable in every function.

    @GetMapping
    public ResponseEntity<?> getAllDataOfUser(){

        Authentication gotValidUsernameAndPassword = SecurityContextHolder.getContext().getAuthentication();

        String userName = gotValidUsernameAndPassword.getName();
        User user = userService.findByUsername(userName);

        List<JournalEntry> entireUserData = user.getUsersJournalEntries();

        if(entireUserData!= null && !entireUserData.isEmpty()){
            return new ResponseEntity<>(entireUserData, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createAndSendJournal(@RequestBody JournalEntry receiveData){

//        Here a small flaw is there that is when we will create a new entry and try to save that entry and if some exception occurs at the
//        saveData() function then as a result journalEntry get saved inside the database but its reference is not stored inside user collection
//        therefore inconsistency occurs therefore we use an annotation on the saveData() function that is --> @Transactional
//        @Transactional annotation will treat each and every operation equally. Means if any of the operation failed then the entire function will be treated as failed
//        and all the data that is saved inside the database get rolled back.

//        We have to write @EnableTransactionManagement annotation on the main function --> this annotation will search for all the methods that is having
//        transactional annotation and keep a track of it.

        try{
            Authentication gotValidUsernameAndPassword = SecurityContextHolder.getContext().getAuthentication();

            String userName = gotValidUsernameAndPassword.getName();

            receiveData.setDate(LocalDateTime.now());
            JournalEntryServiceObj.saveData(receiveData, userName);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

//    From today onwards 21/12/2024 we are using mongodb Atlas a cloud based database for practicing mongodb rather than our local machine
//    Login mongodb atlas on brave

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId myId){

        Authentication gotValidUsernameAndPassword = SecurityContextHolder.getContext().getAuthentication();
        // Here we will get authenticated and login successfully after entering correct userName and Password

        String userName = gotValidUsernameAndPassword.getName();

        User user = userService.findByUsername(userName); // Here we are getting the entire details of that particular userName

        List<JournalEntry> userJournalIdList = user.getUsersJournalEntries().stream().filter(x -> x.getId().equals(myId)).toList();
//        Basically here we are getting the id of that particular journal Entry, if the id provided by the user gets matched inside the db

        if(!userJournalIdList.isEmpty()){

            Optional<JournalEntry> journalData = JournalEntryServiceObj.getDataById(myId);

            if(journalData.isPresent()) {
                return new ResponseEntity<>(journalData.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId myId){
//        Now here problem is that if we delete a journal from the journalEntries collection of mongodb then it will delete that journalEntry but
//        the reference of that journalEntry is still stored at the users collection of mongodb
//        Therefore now we need to perform such actions that when we delete a particular entry from the journalEntries collection then the reference is also get removed
        try{
            Authentication gotValidUsernameAndPassword = SecurityContextHolder.getContext().getAuthentication();
            // Here we will get authenticated and login successfully after entering correct userName and Password

            String userName = gotValidUsernameAndPassword.getName();

            boolean removed = JournalEntryServiceObj.deleteById(myId, userName);
//            basically we are returning a boolean value after deleting a particular entry.
//            if deleted then OK or else, if we are deleting the same entry again then there is no meaning of deletion.
            if(removed == true){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> modifyJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){

        Authentication gotValidUsernameAndPassword = SecurityContextHolder.getContext().getAuthentication();
        // Here we will get authenticated and login successfully after entering correct userName and Password

        String userName = gotValidUsernameAndPassword.getName();

        User user = userService.findByUsername(userName); // Here we are getting the entire details of that particular userName

        List<JournalEntry> userJournalIdList = user.getUsersJournalEntries().stream().filter(x -> x.getId().equals(myId)).toList();
//        Basically here we are getting the id of that particular journal Entry, if the id provided by the user gets matched inside the db

        if(!userJournalIdList.isEmpty()){

            Optional<JournalEntry> journalData = JournalEntryServiceObj.getDataById(myId);

            if(journalData.isPresent()) {
                JournalEntry oldData = journalData.get();
                // Here we are just retrieve the old data by its id if present and update the new entry inside the database that's it.

                oldData.setTitle(newEntry.getTitle() != null && newEntry.getTitle().isBlank() == false ? newEntry.getTitle() : oldData.getTitle());
                oldData.setContent(newEntry.getContent() != null && newEntry.getContent().isBlank() == false ? newEntry.getContent() : oldData.getContent());

                JournalEntryServiceObj.updateExistingData(oldData);
                return new ResponseEntity<>(oldData, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    }

}
