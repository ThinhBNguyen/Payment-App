package com.example.Revagenda.Rev_Pay.service;


import com.example.Revagenda.Rev_Pay.dao.CardRepository;
import com.example.Revagenda.Rev_Pay.entity.Account;
import com.example.Revagenda.Rev_Pay.entity.Card;
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
        account.setBalance(BigDecimal.valueOf(000));
        card.setAccount(account);
        Card savedCard = cardRepository.save(card);
        return savedCard;
    }

    public Optional<Card> getCardByAccount(int cardId){
        return cardRepository.findByAccountId(cardId);
    }
}
