package com.cac.homebanking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.cac.homebanking.exception.InsufficientFundsException;
import com.cac.homebanking.exception.NotFoundException;
import com.cac.homebanking.mapper.TransferMapper;
import com.cac.homebanking.model.Account;
import com.cac.homebanking.model.DTO.TransferDTO;
import com.cac.homebanking.repository.AccountRepository;
import com.cac.homebanking.repository.TransferRepository;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;
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
    when(accountRepository.findById(1L)).thenReturn(Optional.of(new Account(13123L, BigDecimal.valueOf(1000), 1L)));
    when(accountRepository.findById(2L)).thenReturn(Optional.of(new Account(12313L, BigDecimal.valueOf(1000), 2L)));
    TransferDTO transferDTO = new TransferDTO(1L, 2L, ZonedDateTime.now(), BigDecimal.valueOf(100));
    when(transferRepository.save(any())).thenReturn(TransferMapper.transferDTOToEntity(transferDTO));

    assertEquals(transferService.performTransfer(transferDTO), transferDTO);
  }
}