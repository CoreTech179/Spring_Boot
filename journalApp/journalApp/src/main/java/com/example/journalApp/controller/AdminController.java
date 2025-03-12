package com.example.journalApp.controller;

import com.example.journalApp.entity.User;
import com.example.journalApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin APIs")
public class AdminController {

    @Autowired
    private UserService userServiceObj;

    @GetMapping("/getAllUsers")
    @Operation(summary = "Get All Users")
    public ResponseEntity<?> getAllUsers(){
        List<User> allUsersList = userServiceObj.getAllData();
        if(allUsersList != null && !allUsersList.isEmpty()){
            return new ResponseEntity<>(allUsersList, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
