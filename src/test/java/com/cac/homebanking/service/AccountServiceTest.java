package com.cac.homebanking.service;

import static com.cac.homebanking.model.Currency.ARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cac.homebanking.exception.InsufficientFundsException;
import com.cac.homebanking.mapper.AccountMapper;
import com.cac.homebanking.model.Account;
import com.cac.homebanking.model.DTO.AccountDTO;
import com.cac.homebanking.repository.AccountRepository;
import java.math.BigDecimal;
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
  void withdraw() throws InsufficientFundsException {
    AccountDTO accountDTO = AccountMapper.accountEntityToDTO(new Account(1L, BigDecimal.valueOf(1000), 1L, ARS));
    AccountDTO result = accountService.withdraw(BigDecimal.valueOf(100), accountDTO);
    assertEquals(BigDecimal.valueOf(900), result.getBalance());
  }

  @Test
  void deposit() {
    AccountDTO accountDTO = AccountMapper.accountEntityToDTO(new Account(1L, BigDecimal.valueOf(1000), 1L, ARS));
    AccountDTO result = accountService.deposit(BigDecimal.valueOf(100), accountDTO);
    assertEquals(BigDecimal.valueOf(1100), result.getBalance());
  }
}