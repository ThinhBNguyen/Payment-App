package com.example.Revagenda.Rev_Pay.service;


import com.example.Revagenda.Rev_Pay.dao.CardRepository;
import com.example.Revagenda.Rev_Pay.entity.Account;
import com.example.Revagenda.Rev_Pay.entity.Card;
import com.example.Revagenda.Rev_Pay.exceptions.CardAlreadyExistsException;
import com.example.Revagenda.Rev_Pay.exceptions.CardNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional(Transactional.TxType.REQUIRED)
public class CardService {

    private CardRepository cardRepository;

    private AccountService accountService;

    @Autowired
    public CardService (CardRepository cardRepository,
                        AccountService accountService){
        this.cardRepository = cardRepository;
        this.accountService = accountService;
    }


    public Card addCardToAccount(int cardId, Card card){
        Account account = accountService.getAccountById(cardId);
        cardRepository.findByAccountId(cardId).ifPresent(existingCard -> {
            throw new CardAlreadyExistsException("A card is already associated with account ID: " + cardId);
        });
        account.setBalance(BigDecimal.valueOf(000));
        card.setAccount(account);
        Card savedCard = cardRepository.save(card);
        return savedCard;
    }

    public Card getCardByAccount(int accountId){
        return cardRepository.findByAccountId(accountId).orElseThrow(() -> new CardNotFoundException("No card found for account ID: " + accountId));
    }
}
