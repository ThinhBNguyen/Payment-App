package com.example.Revagenda.Rev_Pay.service;

import com.example.Revagenda.Rev_Pay.dao.UserRepository;
import com.example.Revagenda.Rev_Pay.entity.User;
import com.example.Revagenda.Rev_Pay.exceptions.EmailAlreadyExistsException;
import com.example.Revagenda.Rev_Pay.exceptions.UserAlreadyExistsException;
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
        userRepository.findByUserName(user.getUserName())
                .ifPresent(existingUser -> {
                    throw new UserAlreadyExistsException("User already exists with username: " + user.getUserName());
                });
        userRepository.findByEmail(user.getEmail())
                .ifPresent(existingUser -> {
                    throw new EmailAlreadyExistsException("Email already exists: " + user.getEmail());
                });
        user.setPassWord(passwordEncoder.encode(user.getPassWord()));
        return userRepository.save(user);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User findByUserName(String username){
        return userRepository.findByUserName(username).orElseThrow(()
                -> new UsernameNotFoundException("User not found: " + username));
    }

    public User findByUsernameOrEmail(String recipientIdentifier) {
        return userRepository.findByUserNameOrEmail(recipientIdentifier).orElseThrow(()
                -> new UsernameNotFoundException("User not found: " + recipientIdentifier));
    }
}
