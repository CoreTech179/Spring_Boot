package com.example.journalApp.controller;

import com.example.journalApp.entity.User;
import com.example.journalApp.service.UserDetailsServiceImplementation;
import com.example.journalApp.service.UserService;
import com.example.journalApp.utilJWT.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private  UserService userServiceObj;

    @Autowired
    private AuthenticationManager authenticationManagerObj;

    @Autowired
    private JWTUtil jwtUtilObj;

    @Autowired
    private UserDetailsServiceImplementation userDetailsServiceImplementationObj;

    @GetMapping("/check")
    public String status(){
        return "Ok";
    }

    @PostMapping("/signup")
    public void createNewUser(@RequestBody User user){
        userServiceObj.saveNewUserData(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        try{
            authenticationManagerObj.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())); // Verify whether username and password is correct or not
            UserDetails userDetails = userDetailsServiceImplementationObj.loadUserByUsername(user.getUserName()); // fetch the username
            String jwt = jwtUtilObj.generateToken(userDetails.getUsername()); // If username found then I will give jwt to that particular user
            return new ResponseEntity<>(jwt, HttpStatus.OK); // then finally return the jwt token
        } catch (Exception e) {
            log.error("Error Occurred!", e);
            return new ResponseEntity<>("Incorrect username and password!", HttpStatus.BAD_REQUEST);
        }
    }

}
