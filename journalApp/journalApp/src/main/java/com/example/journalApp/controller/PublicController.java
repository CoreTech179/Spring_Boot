package com.example.journalApp.controller;

import com.example.journalApp.entity.User;
import com.example.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userServiceObj;

    @GetMapping("/check")
    public String status(){
        return "Ok";
    }

    @PostMapping("/createUser")
    public void createNewUser(@RequestBody User user){
        userServiceObj.saveData(user);
    }

}
