package com.example.journalApp.controller;

import com.example.journalApp.entity.User;
import com.example.journalApp.repository.UserRepository;
import com.example.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userServiceObj;

    @Autowired
    private UserRepository userRepositoryObj;

//    @GetMapping
//    public List<User> getAllUsers(){
//        return userServiceObj.getAllData(); // Here conversion of objects to JSON format is happening
    ////        Since it returns list of objects of User class then the spring boot automatically converts these java objects into JSON when sending back the response
    ////        Therefore we don't see object references like "user@15245" because "jackson" converts the object into readable JSON format using the fields in the User class
//
//    }

// from now onwards 25/12/2024 we are removing getAllUser() function from the "/user" endpoint because we don't want
// a user will see all the users
// This function can be implemented inside admin



//  Now whenever we want to create a new user we will use "/public" endpoint for creation of a new User
//  and this "/public" endpoint is not authenticated and any one can use this endpoint

//  Now we will modify the updateUsername function because we have authenticated this endpoint ( i.e. defined inside the SpringSecurity class )
//  Now the 'username' will not come from the "Path url", now when we send a username with password it will come automatically inside the SecurityContextHolder
//  And then it will verify that the username and the password matched inside the db or not
//  if matched then update user or else throw forbidden

    @PutMapping
    public ResponseEntity<?> updateUsername(@RequestBody User user){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User userInDb = userServiceObj.findByUsername(username);

        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userServiceObj.saveNewUserData(userInDb);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteByUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepositoryObj.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
