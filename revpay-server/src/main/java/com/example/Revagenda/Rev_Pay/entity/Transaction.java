package com.example.Revagenda.Rev_Pay.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "fromAccountId")
    @JsonBackReference("account-sentTransactions")

    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "toAccountId")
    @JsonBackReference("account-receivedTransactions")
    private Account toAccount;



    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "transactionDate")
    private LocalDateTime transactionDate;

    public Transaction() {
    }

    public Transaction(Account fromAccount, Account toAccount, BigDecimal amount, LocalDateTime transactionDate) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
