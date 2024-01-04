package com.example.Revagenda.Rev_Pay.service;


import com.example.Revagenda.Rev_Pay.dao.LoanRepository;
import com.example.Revagenda.Rev_Pay.dto.LoanDTO;
import com.example.Revagenda.Rev_Pay.entity.Account;
import com.example.Revagenda.Rev_Pay.entity.Loan;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(Transactional.TxType.REQUIRED)
public class LoanService {

    private LoanRepository loanRepository;

    private AccountService accountService;

    public LoanService(LoanRepository loanRepository,
                       AccountService accountService){
        this.loanRepository = loanRepository;
        this.accountService = accountService;
    }


    public Loan issueLoan(LoanDTO loanDTO) {
        Account account = accountService.getAccountById(loanDTO.getAccountId());
        Loan loan = new Loan();
        loan.setAccount(account);
        loan.setAmount(loanDTO.getAmount());
        loan.setIssuedDate(LocalDateTime.now());
        loan.setDurationSeconds(loanDTO.getDurationSeconds());
        loan.setProcessed(false);
        account.setBalance(account.getBalance().add(loanDTO.getAmount()));
        accountService.save(account);
        return loanRepository.save(loan);
    }

}
