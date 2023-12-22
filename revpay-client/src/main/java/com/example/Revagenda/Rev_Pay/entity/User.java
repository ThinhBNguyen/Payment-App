package com.example.Revagenda.Rev_Pay.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.List;

@Entity(name = "users")
@Table(indexes = {@Index(columnList = "username")})
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name ="firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;


    @Column(name = "username", unique = true, nullable = false)
    private String userName;


    @Column(name = "password", nullable = false)
    private String passWord;


    @Column(name = "email", unique = true)
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts;

    public User() {
    }

    public User(int id, String firstName, String lastName, String userName, String passWord, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
