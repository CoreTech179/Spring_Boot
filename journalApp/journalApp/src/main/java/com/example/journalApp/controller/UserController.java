package com.example.journalApp.controller;

import com.example.journalApp.API.response.WeatherResponse;
import com.example.journalApp.entity.User;
import com.example.journalApp.repository.UserRepository;
import com.example.journalApp.service.UserService;
import com.example.journalApp.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "User APIs", description = "Get, Update and Delete the Data")
public class UserController {

    @Autowired
    private UserService userServiceObj;

    @Autowired
    private WeatherService weatherServiceObj;

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
    @Operation(summary = "Update Username")
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
    @Operation(summary = "Delete Username")
    public ResponseEntity<?> deleteByUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepositoryObj.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{city}")
    @Operation(summary = "Get Weather API Response")
    public ResponseEntity<?> greetings(@PathVariable String city){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse getResponse =  weatherServiceObj.getWeather(city);

        String getAllRequiredData = "";

        if(getResponse != null){
            getAllRequiredData = ", Today's Weather feels Like " + getResponse.getCurrent().getFeelsLike() +" degree & Most Likely " + getResponse.getCurrent().getWeatherDescription();
        }
        return new ResponseEntity<>("Hi " + authentication.getName() + " "+ getAllRequiredData, HttpStatus.OK);
    }

}
