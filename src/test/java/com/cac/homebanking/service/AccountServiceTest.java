package com.cac.homebanking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.cac.homebanking.exception.InsufficientFundsException;
import com.cac.homebanking.exception.NotFoundException;
import com.cac.homebanking.model.Account;
import com.cac.homebanking.model.DTO.AccountDTO;
import com.cac.homebanking.repository.AccountRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

  @Mock
  private AccountRepository accountRepository;

  @Mock
  private UserService userService;

  @InjectMocks
  private AccountService accountService;

  @Test
  void withdraw() throws NotFoundException, InsufficientFundsException {
    when(accountRepository.findById(1L)).thenReturn(Optional.of(new Account(1L, BigDecimal.valueOf(1000), 1L)));
    AccountDTO result = accountService.withdraw(BigDecimal.valueOf(100), 1L);
    assertEquals(BigDecimal.valueOf(900), result.getBalance());
  }

  @Test
  void deposit() throws NotFoundException {
    when(accountRepository.findById(1L)).thenReturn(Optional.of(new Account(1L, BigDecimal.valueOf(1000), 1L)));
    AccountDTO result = accountService.deposit(BigDecimal.valueOf(100), 1L);
    assertEquals(BigDecimal.valueOf(1100), result.getBalance());
  }
}