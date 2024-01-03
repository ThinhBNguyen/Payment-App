package com.example.Revagenda.Rev_Pay.service;


import com.example.Revagenda.Rev_Pay.dao.PaymentRequestRepository;
import com.example.Revagenda.Rev_Pay.dto.TransferDTO;
import com.example.Revagenda.Rev_Pay.entity.Account;
import com.example.Revagenda.Rev_Pay.entity.PaymentRequest;
import com.example.Revagenda.Rev_Pay.entity.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(Transactional.TxType.REQUIRED)
public class PaymentRequestService {

    private PaymentRequestRepository paymentRequestRepository;
    private AccountService accountService;

    private TransactionService transactionService;

    private UserService userService;


    @Autowired
    public PaymentRequestService (PaymentRequestRepository paymentRequestRepository,
                                  AccountService accountService,
                                  TransactionService transactionService,
                                  UserService userService){
        this.paymentRequestRepository = paymentRequestRepository;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.userService = userService;
    }


    public PaymentRequest createPaymentRequest(int senderAccountId, int receiverAccountId, BigDecimal amount) {
        Account senderAccount = accountService.getAccountById(senderAccountId);
        Account receiverAccount = accountService.getAccountById(receiverAccountId);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setSenderAccount(senderAccount);
        paymentRequest.setReceiverAccount(receiverAccount);
        paymentRequest.setAmount(amount);
        paymentRequest.setStatus(PaymentRequest.PaymentRequestStatus.PENDING);
        return paymentRequestRepository.save(paymentRequest);
    }


    public void acceptPaymentRequest(int paymentRequestId) {
        PaymentRequest paymentRequest = paymentRequestRepository.findById(paymentRequestId)
                .orElseThrow(() -> new EntityNotFoundException("PaymentRequest not found"));

            String recipientIdentifier = paymentRequest.getSenderAccount().getUser().getUserName();
            TransferDTO transferDTO = new TransferDTO();
            transferDTO.setRecipientIdentifier(recipientIdentifier);
            transferDTO.setAmount(paymentRequest.getAmount());
            transferDTO.setSenderAccountId(paymentRequest.getReceiverAccount().getId());
            transactionService.transferMoney(transferDTO);
            paymentRequest.setStatus(PaymentRequest.PaymentRequestStatus.ACCEPTED);
            paymentRequestRepository.save(paymentRequest);
    }

    public void declinePaymentRequest(int paymentRequestId) {
        PaymentRequest paymentRequest = paymentRequestRepository.findById(paymentRequestId)
                .orElseThrow(() -> new EntityNotFoundException("PaymentRequest not found"));
        paymentRequest.setStatus(PaymentRequest.PaymentRequestStatus.DECLINED);
        paymentRequestRepository.save(paymentRequest);
    }

    public List<PaymentRequest> getReceivedPaymentRequests(String username) {
        User user = userService.findByUserName(username);
        List<PaymentRequest> receivedRequests = new ArrayList<>();
        for (Account account : user.getAccounts()) {
            List<PaymentRequest> requestsForAccount = paymentRequestRepository.findByReceiverAccountId(account.getId());
            receivedRequests.addAll(requestsForAccount);
        }
        return receivedRequests;
    }
}

