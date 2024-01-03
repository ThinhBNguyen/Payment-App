package com.example.Revagenda.Rev_Pay.dao;

import com.example.Revagenda.Rev_Pay.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findAllByUserId(int id);


    @Query(value = "SELECT * FROM accounts WHERE userid = :id AND balance IS NOT NULL", nativeQuery = true)
    List<Account> findAllWithBalanceByUserId(@Param("id") int id);
}
