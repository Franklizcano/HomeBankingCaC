package com.cac.homebanking.integrationTests.service;

import com.cac.homebanking.exception.InsufficientFundsException;
import com.cac.homebanking.exception.NotFoundException;
import com.cac.homebanking.mapper.TransferMapper;
import com.cac.homebanking.model.Currency;
import com.cac.homebanking.model.IdentifierType;
import com.cac.homebanking.model.TransferStatus;
import com.cac.homebanking.model.dto.AccountDto;
import com.cac.homebanking.model.dto.TransferDto;
import com.cac.homebanking.repository.AccountRepository;
import com.cac.homebanking.repository.TransferRepository;
import com.cac.homebanking.service.AccountService;
import com.cac.homebanking.service.TransferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

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
        UUID originAccountId = UUID.randomUUID();
        UUID targetAccountId = UUID.randomUUID();
        UUID originUserId = UUID.randomUUID();
        UUID targetUserId = UUID.randomUUID();

        AccountDto originAccount = buildAccountDTO(originAccountId, BigDecimal.valueOf(1000), originUserId, Currency.ARS);
        AccountDto destinationAccount = buildAccountDTO(targetAccountId, BigDecimal.valueOf(1000), targetUserId, Currency.ARS);

        when(accountService.getAccountById(originAccountId)).thenReturn(originAccount);
        when(accountService.getAccountById(targetAccountId)).thenReturn(destinationAccount);

        when(accountService.withdraw(any(BigDecimal.class), eq(originAccount)))
                .thenReturn(buildAccountDTO(originAccountId, BigDecimal.valueOf(900), originUserId, Currency.ARS));
        when(accountService.deposit(any(BigDecimal.class), eq(destinationAccount)))
                .thenReturn(buildAccountDTO(targetAccountId, BigDecimal.valueOf(1100), targetUserId, Currency.ARS));

        TransferDto transferDTO = new TransferDto(originAccountId, targetAccountId, IdentifierType.ID, BigDecimal.valueOf(100), TransferStatus.COMPLETED);
        when(transferRepository.save(any())).thenReturn(TransferMapper.transferDTOToEntity(transferDTO));

        TransferDto result = transferService.performTransfer(transferDTO);

        assertEquals(TransferStatus.COMPLETED, result.getStatus());
        assertEquals(transferDTO.getAmount(), result.getAmount());
        assertEquals(transferDTO.getOriginId(), result.getOriginId());
        assertEquals(transferDTO.getTargetId(), result.getTargetId());
    }

    AccountDto buildAccountDTO(UUID id, BigDecimal balance, UUID userId, Currency currency) {
        AccountDto accountDTO = new AccountDto();
        accountDTO.setId(id);
        accountDTO.setBalance(balance);
        accountDTO.setUserId(userId);
        accountDTO.setCurrency(currency); // Assuming currency is a String in AccountDTO
        return accountDTO;
    }
}