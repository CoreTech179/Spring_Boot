package com.example.journalApp.service;

import com.example.journalApp.entity.User;
import com.example.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UserService {

    private static final PasswordEncoder securePassword = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepositoryObj;

    public void saveData(User entry){
        entry.setPassword(securePassword.encode(entry.getPassword()));
        entry.setRoles(List.of("USER"));
        userRepositoryObj.save(entry);
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
