package com.example.Revagenda.Rev_Pay.service;

import com.example.Revagenda.Rev_Pay.dao.UserRepository;
import com.example.Revagenda.Rev_Pay.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(Transactional.TxType.REQUIRED)
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }
}
