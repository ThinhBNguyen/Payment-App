package com.example.Revagenda.Rev_Pay.service;

import com.example.Revagenda.Rev_Pay.dao.AccountRepository;
import com.example.Revagenda.Rev_Pay.entity.Account;
import com.example.Revagenda.Rev_Pay.entity.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(Transactional.TxType.REQUIRED)
public class AccountService {

    private AccountRepository accountRepository;
    private UserService userService;

    @Autowired
    public AccountService (AccountRepository accountRepository,
                           UserService userService){
        this.accountRepository = accountRepository;
        this.userService = userService;
    }

    public List<Account> getAccountByUser(String username){
        User user = userService.findByUserName(username);
        return accountRepository.findAllByUserId(user.getId());
    }

    public Account createAccount(Account account){

        return accountRepository.save(account);
    }

    public Account createAccountForUser(Account account, String username) {
        User user = userService.findByUserName(username);
        account.setUser(user);
        return accountRepository.save(account);
    }

    public Account getAccountById(int id){
        return accountRepository.findById(id).orElseThrow
                (() -> new EntityNotFoundException("Account not found with id: " + id));
    }

    public Account deposit(int accountId, BigDecimal amount) {
        Account account = getAccountById(accountId);
        if(account.getBalance() == null)
            account.setBalance(BigDecimal.valueOf(0));
        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance = currentBalance.add(amount);
        account.setBalance(newBalance);
        return accountRepository.save(account);
    }
}
