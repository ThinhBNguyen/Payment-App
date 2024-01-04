package com.example.Revagenda.Rev_Pay.service;


import com.example.Revagenda.Rev_Pay.dao.TransactionRepository;
import com.example.Revagenda.Rev_Pay.dto.TransactionDTO;
import com.example.Revagenda.Rev_Pay.dto.TransferDTO;
import com.example.Revagenda.Rev_Pay.entity.Account;
import com.example.Revagenda.Rev_Pay.entity.Transaction;
import com.example.Revagenda.Rev_Pay.entity.User;
import com.example.Revagenda.Rev_Pay.exceptions.InsufficientFundsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class TransactionService {

    private UserService userService;
    private AccountService accountService;
    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionService (UserService userService,
                               AccountService accountService,
                               TransactionRepository transactionRepository){
        this.userService = userService;
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
    }

    public void transferMoney(TransferDTO transferDTO) {
        User recipient = userService.findByUsernameOrEmail(transferDTO.getRecipientIdentifier());
        Account fromAccount = accountService.getAccountById(transferDTO.getSenderAccountId());
        Account toAccount = recipient.getAccounts().get(0);
        if (fromAccount.getBalance().compareTo(transferDTO.getAmount()) < 0) {
            throw new InsufficientFundsException("Insufficient funds for the transfer.");
        }
        fromAccount.setBalance(fromAccount.getBalance().subtract(transferDTO.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(transferDTO.getAmount()));
        accountService.save(fromAccount);
        accountService.save(toAccount);
        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(transferDTO.getAmount());
        transaction.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    public List<TransactionDTO> getTransactionsForUser(String username) {
        User user = userService.findByUserName(username);
        int userId = user.getId();
        List<Object[]> results = transactionRepository.findByUser(userId);
        List<TransactionDTO> transactions = new ArrayList<>();
        for (Object[] result : results) {
            TransactionDTO dto = new TransactionDTO(
                    (Integer) result[0],
                    (BigDecimal) result[1],
                    ((Timestamp) result[2]).toLocalDateTime(),
                    (Integer) result[3],
                    (Integer) result[4]
            );
            transactions.add(dto);
        }
        return transactions;
    }
}
