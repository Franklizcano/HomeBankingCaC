package com.cac.homebanking.integrationTests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.cac.homebanking.exception.InsufficientFundsException;
import com.cac.homebanking.model.Account;
import com.cac.homebanking.model.DTO.AccountDTO;
import com.cac.homebanking.repository.AccountRepository;
import com.cac.homebanking.service.AccountService;
import com.cac.homebanking.service.UserService;
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
    AccountDTO accountDTO = buildAccountDTO(1L, BigDecimal.valueOf(1000), 1L);
    when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));
    AccountDTO result = accountService.withdraw(BigDecimal.valueOf(100), accountDTO);
    assertEquals(BigDecimal.valueOf(900), result.getBalance());
  }

  @Test
  void deposit() {
    AccountDTO accountDTO = buildAccountDTO(1L, BigDecimal.valueOf(1000), 1L);
    when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));
    AccountDTO result = accountService.deposit(BigDecimal.valueOf(100), accountDTO);
    assertEquals(BigDecimal.valueOf(1100), result.getBalance());
  }

  private AccountDTO buildAccountDTO(Long id, BigDecimal balance, Long userId) {
    AccountDTO accountDTO = new AccountDTO();
    accountDTO.setId(id);
    accountDTO.setBalance(balance);
    accountDTO.setUserId(userId);
    return accountDTO;
  }
}