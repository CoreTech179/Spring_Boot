package com.example.journalApp.service;

import com.example.journalApp.entity.User;
import com.example.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class UserService {

//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

//    Here problem is that we have to write it everytime if we want to print a specific log.
//    Therefore, we use @Slf4j annotation and the object is created at the name of "log". Therefore, we have to write log.error("String") or log.warn("String")

    private static final PasswordEncoder securePassword = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepositoryObj;

    public void saveNewUserData(User entry){
        try{
            entry.setPassword(securePassword.encode(entry.getPassword()));
            entry.setRoles(List.of("USER"));
            userRepositoryObj.save(entry);

        } catch (Exception e) {
//            Log is an object of SlF4j annotation
            log.info("Error occurred at UserService class");
//            We can also use placeholder followed by "{}" and also we don't have to use "+" sign here. We can use ",".
//            log.info("Hello for {}:",entry.getUserName(),e);
        }
    }

    public void saveUser(User user){
        userRepositoryObj.save(user);
    }

    public List<User> getAllData(){
        return userRepositoryObj.findAll();
    }

    public Optional<User> getDataById(ObjectId id){
        return userRepositoryObj.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepositoryObj.deleteById(id);
    }

    public User findByUsername(String username){
        return userRepositoryObj.findByUserName(username);
    }

}
