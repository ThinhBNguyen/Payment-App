package com.example.Revagenda.Rev_Pay.controller;


import com.example.Revagenda.Rev_Pay.dto.PaymentRequestDTO;
import com.example.Revagenda.Rev_Pay.entity.PaymentRequest;
import com.example.Revagenda.Rev_Pay.exceptions.InsufficientFundsException;
import com.example.Revagenda.Rev_Pay.service.PaymentRequestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class PaymentRequestController {

    private PaymentRequestService paymentRequestService;


    @Autowired
    public PaymentRequestController(PaymentRequestService paymentRequestService){
        this.paymentRequestService = paymentRequestService;
    }

    @PostMapping("/send-payment")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<PaymentRequest> createPaymentRequest(@RequestBody PaymentRequestDTO payRequest) {
        paymentRequestService.createPaymentRequest(payRequest.getSenderAccountId(),
                                                    payRequest.getReceiverAccountId(),
                                                    payRequest.getAmount());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept-payment/{paymentRequestId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> acceptPaymentRequest(@PathVariable int paymentRequestId) {
        paymentRequestService.acceptPaymentRequest(paymentRequestId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/decline-payment/{paymentRequestId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> declinePaymentRequest(@PathVariable int paymentRequestId) {
        paymentRequestService.declinePaymentRequest(paymentRequestId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/received-requests")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PaymentRequest>> getReceivedPaymentRequests(Authentication authentication) {
        String username = authentication.getName();
        List<PaymentRequest> paymentRequests = paymentRequestService.getReceivedPaymentRequests(username);
        return ResponseEntity.ok(paymentRequests);
    }


    //EXCEPTION HANDLING


    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFound(UsernameNotFoundException ex) {
        return ex.getMessage();
    }



    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String EntityNotFoundException(EntityNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(InsufficientFundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String InsufficientFundsException(InsufficientFundsException ex) {
        return ex.getMessage();
    }
}
