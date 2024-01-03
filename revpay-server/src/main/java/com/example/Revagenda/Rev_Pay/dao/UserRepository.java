package com.example.Revagenda.Rev_Pay.dao;

import com.example.Revagenda.Rev_Pay.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String username);


    @Query(value = "SELECT * FROM Users u WHERE u.userName = :identifier OR u.email = :identifier", nativeQuery = true)
    User findByUserNameOrEmail(@Param("identifier") String identifier);
}
