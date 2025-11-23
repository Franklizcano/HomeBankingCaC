package com.cac.homebanking.integrationTests.service;

import com.cac.homebanking.exception.InsufficientFundsException;
import com.cac.homebanking.model.Account;
import com.cac.homebanking.model.dto.AccountDto;
import com.cac.homebanking.repository.AccountRepository;
import com.cac.homebanking.service.AccountService;
import com.cac.homebanking.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private AccountService accountService;

    @Test
    void withdraw() throws InsufficientFundsException {
        AccountDto accountDTO = buildAccountDTO(UUID.randomUUID(), BigDecimal.valueOf(1000), UUID.randomUUID());
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));
        AccountDto result = accountService.withdraw(BigDecimal.valueOf(100), accountDTO);
        assertEquals(BigDecimal.valueOf(900), result.getBalance());
    }

    @Test
    void deposit() {
        AccountDto accountDTO = buildAccountDTO(UUID.randomUUID(), BigDecimal.valueOf(1000), UUID.randomUUID());
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));
        AccountDto result = accountService.deposit(BigDecimal.valueOf(100), accountDTO);
        assertEquals(BigDecimal.valueOf(1100), result.getBalance());
    }

    private AccountDto buildAccountDTO(UUID id, BigDecimal balance, UUID userId) {
        AccountDto accountDTO = new AccountDto();
        accountDTO.setId(id);
        accountDTO.setBalance(balance);
        accountDTO.setUserId(userId);
        return accountDTO;
    }
}