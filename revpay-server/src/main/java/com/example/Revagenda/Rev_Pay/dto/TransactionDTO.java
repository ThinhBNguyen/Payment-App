package com.example.Revagenda.Rev_Pay.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO {
    private int id;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private int fromAccountId;
    private int toAccountId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(int fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public int getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(int toAccountId) {
        this.toAccountId = toAccountId;
    }

    public TransactionDTO(int id, BigDecimal amount, LocalDateTime transactionDate, int fromAccountId, int toAccountId) {
        this.id = id;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
    }
}
