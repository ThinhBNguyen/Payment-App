package com.example.Revagenda.Rev_Pay.dao;

import com.example.Revagenda.Rev_Pay.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
