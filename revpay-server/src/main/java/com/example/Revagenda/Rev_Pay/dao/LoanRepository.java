package com.example.Revagenda.Rev_Pay.dao;

import com.example.Revagenda.Rev_Pay.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

    List<Loan> findByProcessedFalseAndIssuedDateBefore(LocalDateTime dateTime);
}
