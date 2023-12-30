package com.example.Revagenda.Rev_Pay.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "accounts")
public class Account {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "balance")
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @OneToOne(mappedBy = "account")
    private Card card;
    private enum Type {
        USER,
        BUSSINESS,
        ADMIN
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    public Account() {
    }

    public Account(Card card, Type type) {
        this.card = card;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
