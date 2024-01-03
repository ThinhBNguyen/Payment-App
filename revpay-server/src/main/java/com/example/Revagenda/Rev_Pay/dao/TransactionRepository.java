package com.example.Revagenda.Rev_Pay.dao;

import com.example.Revagenda.Rev_Pay.entity.Transaction;
import com.example.Revagenda.Rev_Pay.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query(value = "SELECT t.id, t.amount, t.transaction_date, t.from_account_id, t.to_account_id " +
            "FROM transactions t JOIN accounts a ON t.from_account_id = a.id OR t.to_account_id = a.id " +
            "WHERE a.userid = :userId", nativeQuery = true)
    List<Object[]> findByUser(@Param("userId") int userId);
}
