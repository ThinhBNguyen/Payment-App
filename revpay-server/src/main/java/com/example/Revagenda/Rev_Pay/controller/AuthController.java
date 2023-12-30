package com.example.Revagenda.Rev_Pay.controller;


import com.example.Revagenda.Rev_Pay.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AuthController {


    @PostMapping("/login")
    public ResponseEntity<?> customLogin(@RequestBody User user) {
        return ResponseEntity.ok().build();
    }


}
