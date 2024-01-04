package com.example.Revagenda.Rev_Pay.service;


import com.example.Revagenda.Rev_Pay.dao.AccountRepository;
import com.example.Revagenda.Rev_Pay.entity.Account;
import com.example.Revagenda.Rev_Pay.entity.User;
import com.example.Revagenda.Rev_Pay.exceptions.InsufficientFundsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccountServiceTest {

    @MockBean
    private AccountRepository mockAccountRepository;

    @Autowired
    private AccountService accountService;


    @MockBean
    private UserService userService;

    @Test
    public void test_createAccountExpectNewAccount() {
        Account testAccount = new Account();
        testAccount.setId(1);
        testAccount.setBalance(new BigDecimal("0"));
        testAccount.setType(Account.Type.USER);

        when(mockAccountRepository.save(testAccount)).thenReturn(testAccount);

        Account savedAccount = accountService.save(testAccount);

        assertEquals(testAccount, savedAccount);
    }

    @Test
    public void test_createAccountForUserExpectAccountWithUserId(){
        String username = "johnDoe";
        User mockUser = new User();
        mockUser.setUserName(username);

        Account testAccount = new Account();
        testAccount.setBalance(new BigDecimal("0"));

        when(userService.findByUserName(username)).thenReturn(mockUser);
        when(mockAccountRepository.save(testAccount)).thenReturn(testAccount);

        Account createdAccount = accountService.createAccountForUser(testAccount, username);

        assertEquals(mockUser, createdAccount.getUser());
        assertEquals(testAccount, createdAccount);
    }

    @Test
    public void test_createAccountForUserNotFound() {
        String username = "user1";
        when(userService.findByUserName(username)).thenThrow(new UsernameNotFoundException("User not found with username: " + username));

        Account testAccount = new Account();

        assertThrows(UsernameNotFoundException.class, () -> {
            accountService.createAccountForUser(testAccount, username);
        });
    }

    @Test
    public void test_getAccountByIdExpectAccountById() {
        int id = 1;
        Account testAccount = new Account();
        testAccount.setId(1);

        when(mockAccountRepository.findById(id)).thenReturn(Optional.of(testAccount));

        Account foundAccound = accountService.getAccountById(id);

        assertNotNull(foundAccound);
        assertEquals(testAccount, foundAccound);
    }
    @Test
    public void test_getAccountByIdExpectAccountNotFound() {
        int id = 1;

        when(mockAccountRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            accountService.getAccountById(id);
        });
    }

    @Test
    public void test_depositIntoBalanceExpectSuccessfulDeposit() {
        int id = 1;
        BigDecimal depositAmount = new BigDecimal("100.00");
        Account testAccount = new Account();
        testAccount.setId(1);
        testAccount.setBalance(new BigDecimal("300.00"));

        when(mockAccountRepository.findById(id)).thenReturn(Optional.of(testAccount));
        when(mockAccountRepository.save(testAccount)).thenReturn(testAccount);

        Account updatedAccount = accountService.deposit(id, depositAmount);

        assertNotNull(updatedAccount);
        assertEquals(0, updatedAccount.getBalance().compareTo(new BigDecimal("400.00")));
    }

    @Test
    public void test_getAccountWithAssociatedCardSuccess() {
        String username = "user";
        int userId = 1;
        User testUser = new User();
        testUser.setId(userId);
        testUser.setUserName(username);

        Account testAccount1 = new Account();
        Account testAccount2 = new Account();

        List<Account> expectedAccounts = Arrays.asList(testAccount1, testAccount2);
        when(userService.findByUserName(username)).thenReturn(testUser);
        when(mockAccountRepository.findAllWithBalanceByUserId(userId)).thenReturn(expectedAccounts);

        List<Account> actualAccounts = accountService.getAccountsWithCard(username);

        assertNotNull(actualAccounts);
        assertEquals(expectedAccounts, actualAccounts);
    }

    @Test
    public void test_getAccountWithAssociatedCardUnsuccessful() {
        String username = "user";
        when(userService.findByUserName(username)).thenThrow(new UsernameNotFoundException("User not found with username: " + username));
        assertThrows(UsernameNotFoundException.class, () -> {
            accountService.getAccountsWithCard(username);
        });
    }

    @Test
    public void test_withdrawFromBalanceExpectSuccessfulWithdraw() {
        int accountId = 1;
        BigDecimal withdrawalAmount = new BigDecimal("100.00");
        BigDecimal initialBalance = new BigDecimal("200.00");

        Account testAccount = new Account();
        testAccount.setId(accountId);
        testAccount.setBalance(initialBalance);

        when(mockAccountRepository.findById(accountId)).thenReturn(Optional.of(testAccount));
        when(mockAccountRepository.save(testAccount)).thenReturn(testAccount);

        Account updatedAccount = accountService.withdraw(accountId, withdrawalAmount);

        assertNotNull(updatedAccount);
        assertEquals(0, updatedAccount.getBalance().compareTo(new BigDecimal("100.00")));
    }

    @Test
    public void test_withdrawFromBalanceExpectUnsuccessfulWithdraw() {
        int accountId = 1;
        BigDecimal withdrawalAmount = new BigDecimal("300.00");
        BigDecimal initialBalance = new BigDecimal("200.00");

        Account testAccount = new Account();
        testAccount.setId(accountId);
        testAccount.setBalance(initialBalance);

        when(mockAccountRepository.findById(accountId)).thenReturn(Optional.of(testAccount));

        assertThrows(InsufficientFundsException.class, () -> {
            accountService.withdraw(accountId, withdrawalAmount);
        });
    }
}
