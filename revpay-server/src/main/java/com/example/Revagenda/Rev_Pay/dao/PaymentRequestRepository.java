package com.example.Revagenda.Rev_Pay.dao;

import com.example.Revagenda.Rev_Pay.entity.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Integer> {
    List<PaymentRequest> findByReceiverAccountId(int id);
}
