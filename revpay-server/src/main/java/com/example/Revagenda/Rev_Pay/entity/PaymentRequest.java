package com.example.Revagenda.Rev_Pay.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "paymentrequests")
public class PaymentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "sender_account_id")
    private Account senderAccount; // The account sending the request

    @ManyToOne
    @JoinColumn(name = "receiver_account_id")
    private Account receiverAccount; // The account to receive funds

    @Column(name = "amount")
    private BigDecimal amount;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentRequestStatus status;


    public enum PaymentRequestStatus {
        PENDING,
        ACCEPTED,
        DECLINED
    }

    public PaymentRequest() {
    }

    public PaymentRequest(int id, Account senderAccount, Account receiverAccount, BigDecimal amount, PaymentRequestStatus status) {
        this.id = id;
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(Account senderAccount) {
        this.senderAccount = senderAccount;
    }

    public Account getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(Account receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentRequestStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentRequestStatus status) {
        this.status = status;
    }
}