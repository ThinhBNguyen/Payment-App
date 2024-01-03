package com.example.Revagenda.Rev_Pay.controller;


import com.example.Revagenda.Rev_Pay.entity.User;
import com.example.Revagenda.Rev_Pay.exceptions.EmailAlreadyExistsException;
import com.example.Revagenda.Rev_Pay.exceptions.UserAlreadyExistsException;
import com.example.Revagenda.Rev_Pay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController (UserService userService){
        this.userService = userService;
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    User createNewUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    List<User> getAllUsers(){
        List<User> userList = userService.getAllUser();
        return userList;
    }


    //EXCEPTIONS HANDLING


    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFound(UsernameNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return ex.getMessage();
    }

}
