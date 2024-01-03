package com.example.Revagenda.Rev_Pay.exceptions;

public class CardAlreadyExistsException extends RuntimeException {
    public CardAlreadyExistsException(String message) {
        super(message);
    }
}

