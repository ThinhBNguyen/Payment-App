package com.example.Revagenda.Rev_Pay.controller;

import com.example.Revagenda.Rev_Pay.dto.TransactionDTO;
import com.example.Revagenda.Rev_Pay.dto.TransferDTO;
import com.example.Revagenda.Rev_Pay.entity.Transaction;
import com.example.Revagenda.Rev_Pay.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }



    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> transferMoney(@RequestBody TransferDTO transferDTO) {
        transactionService.transferMoney(transferDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-transactions")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TransactionDTO>> getUsersTransactions(Authentication authentication){
        String username = authentication.getName();
        List<TransactionDTO> transactions = transactionService.getTransactionsForUser(username);
        return ResponseEntity.ok(transactions);
    }
}
