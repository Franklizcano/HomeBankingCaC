package com.cac.homebanking.integrationTests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.cac.homebanking.exception.InsufficientFundsException;
import com.cac.homebanking.exception.NotFoundException;
import com.cac.homebanking.mapper.TransferMapper;
import com.cac.homebanking.model.Currency;
import com.cac.homebanking.model.DTO.AccountDTO;
import com.cac.homebanking.model.DTO.TransferDTO;
import com.cac.homebanking.model.TransferStatus;
import com.cac.homebanking.repository.AccountRepository;
import com.cac.homebanking.repository.TransferRepository;
import com.cac.homebanking.service.AccountService;
import com.cac.homebanking.service.TransferService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TransferServiceTest {

  @Mock
  private AccountRepository accountRepository;

  @Mock
  private TransferRepository transferRepository;

  @Mock
  private AccountService accountService;

  @InjectMocks
  private TransferService transferService;

  @Test
  void performTransfer() throws NotFoundException, InsufficientFundsException {
    AccountDTO originAccount = buildAccountDTO(1L, BigDecimal.valueOf(1000), 1L, Currency.ARS);
    AccountDTO destinationAccount = buildAccountDTO(2L, BigDecimal.valueOf(1000), 2L, Currency.ARS);

    when(accountService.getAccountById(1L)).thenReturn(originAccount);
    when(accountService.getAccountById(2L)).thenReturn(destinationAccount);

    when(accountService.withdraw(any(BigDecimal.class), eq(originAccount)))
        .thenReturn(buildAccountDTO(1L, BigDecimal.valueOf(900), 1L, Currency.ARS));
    when(accountService.deposit(any(BigDecimal.class), eq(destinationAccount)))
        .thenReturn(buildAccountDTO(2L, BigDecimal.valueOf(1100), 2L, Currency.ARS));

    TransferDTO transferDTO = new TransferDTO(1L, 2L, BigDecimal.valueOf(100), TransferStatus.COMPLETED);
    when(transferRepository.save(any())).thenReturn(TransferMapper.transferDTOToEntity(transferDTO));

    TransferDTO result = transferService.performTransfer(transferDTO);

    assertEquals(TransferStatus.COMPLETED, result.getStatus());
    assertEquals(transferDTO.getAmount(), result.getAmount());
    assertEquals(transferDTO.getOriginId(), result.getOriginId());
    assertEquals(transferDTO.getTargetId(), result.getTargetId());
  }

  AccountDTO buildAccountDTO(Long id, BigDecimal balance, Long userId, Currency currency) {
    AccountDTO accountDTO = new AccountDTO();
    accountDTO.setId(id);
    accountDTO.setBalance(balance);
    accountDTO.setUserId(userId);
    accountDTO.setCurrency(currency); // Assuming currency is a String in AccountDTO
    return accountDTO;
  }
}