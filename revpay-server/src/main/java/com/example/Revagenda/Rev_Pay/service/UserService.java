package com.example.Revagenda.Rev_Pay.service;

import com.example.Revagenda.Rev_Pay.dao.UserRepository;
import com.example.Revagenda.Rev_Pay.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(Transactional.TxType.REQUIRED)
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService (UserRepository userRepository,
                        PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user){
        user.setPassWord(passwordEncoder.encode(user.getPassWord()));
        return userRepository.save(user);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User findByUserName(String username){
        return userRepository.findByUserName(username).orElseThrow(()
                -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public User findByUsernameOrEmail(String recipientIdentifier) {
        return userRepository.findByUserNameOrEmail(recipientIdentifier);
    }
}
