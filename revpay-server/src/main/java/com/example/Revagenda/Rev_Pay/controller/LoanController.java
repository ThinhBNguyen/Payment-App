package com.example.Revagenda.Rev_Pay.controller;


import com.example.Revagenda.Rev_Pay.dto.LoanDTO;
import com.example.Revagenda.Rev_Pay.entity.Loan;
import com.example.Revagenda.Rev_Pay.service.LoanService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class LoanController {

    private LoanService loanService;

    @Autowired
    public LoanController (LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/issue")
    public ResponseEntity<Loan> issueLoan(@RequestBody LoanDTO loanDTO) {
        Loan loan = loanService.issueLoan(loanDTO);
        return ResponseEntity.ok(loan);
    }


    //EXCEPTIONS HANDLING

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEntityNotFound(EntityNotFoundException ex) {
        return ex.getMessage();
    }
}
