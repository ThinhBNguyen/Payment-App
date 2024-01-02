package com.example.Revagenda.Rev_Pay.controller;


import com.example.Revagenda.Rev_Pay.entity.Account;
import com.example.Revagenda.Rev_Pay.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController()
@RequestMapping("/accounts")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }


    @GetMapping("/my-accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getCurrentUserAccounts(Principal principal) {
        String username = principal.getName();
        return accountService.getAccountByUser(username);
    }

    @PostMapping("/create-account")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account createAccountForCurrentUser(@RequestBody Account account, Authentication authentication) {
        String username = authentication.getName();
        return accountService.createAccountForUser(account, username);
    }

    @PostMapping("/{accountId}/deposit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BigDecimal deposit(@PathVariable int accountId, @RequestBody BigDecimal amount){
       Account account = accountService.deposit(accountId, amount);
       return account.getBalance();
    }

}
