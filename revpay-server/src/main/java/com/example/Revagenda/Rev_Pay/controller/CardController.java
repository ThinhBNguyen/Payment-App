package com.example.Revagenda.Rev_Pay.controller;


import com.example.Revagenda.Rev_Pay.entity.Card;
import com.example.Revagenda.Rev_Pay.exceptions.CardAlreadyExistsException;
import com.example.Revagenda.Rev_Pay.exceptions.CardNotFoundException;
import com.example.Revagenda.Rev_Pay.security.Authentication;
import com.example.Revagenda.Rev_Pay.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController()
@RequestMapping("/accounts")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class CardController {

    private CardService cardService;

    @Autowired
    public CardController(CardService cardService){
        this.cardService = cardService;
    }
    @PostMapping("/{accountId}/add-card")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Card addCardToAccount(@RequestBody Card card, @PathVariable("accountId") int id){
        return cardService.addCardToAccount(id, card);
    }

    @GetMapping("/{accountId}/get-card")
    @ResponseStatus(HttpStatus.OK)
    public Card getCardByAccount(@PathVariable("accountId") int id){
        return cardService.getCardByAccount(id);
    }

    //EXCEPTIONS HANDLING


    @ExceptionHandler(CardAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleCardAlreadyExists(CardAlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(CardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCardNotFound(CardNotFoundException ex) {
        return ex.getMessage();
    }
}
