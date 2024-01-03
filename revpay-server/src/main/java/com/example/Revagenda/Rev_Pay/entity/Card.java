package com.example.Revagenda.Rev_Pay.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "cards")
public class Card {


    private enum CardType {
        DEBIT,
        CREDIT

    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CardType type;

    @Id
    @Column(name = "cardnumber", unique = true, nullable = false)
    private BigDecimal cardNumber;


    @Column(name = "cardsecurity", unique = true,nullable = false)
    private int cardSecurity;

    @OneToOne
    @JoinColumn(name = "accountid")
    @JsonBackReference
    private Account account;

    public Card() {
    }

    public Card(BigDecimal cardNumber, int cardSecurity) {
        this.cardNumber = cardNumber;
        this.cardSecurity = cardSecurity;
    }

    public BigDecimal getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(BigDecimal cardNumber) {
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

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }
}
