package com.example.Revagenda.Rev_Pay.entity;

import jakarta.persistence.*;

@Entity(name = "cards")
public class Card {

    @Id
    @Column(name = "cardnumber", unique = true, nullable = false)
    private int cardNumber;


    @Column(name = "cardsecurity", unique = true,nullable = false)
    private int cardSecurity;

    @OneToOne
    @JoinColumn(name = "accountid")
    private Account account;

    public Card() {
    }

    public Card(int cardNumber, int cardSecurity) {
        this.cardNumber = cardNumber;
        this.cardSecurity = cardSecurity;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCardSecurity() {
        return cardSecurity;
    }

    public void setCardSecurity(int cardSecurity) {
        this.cardSecurity = cardSecurity;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
