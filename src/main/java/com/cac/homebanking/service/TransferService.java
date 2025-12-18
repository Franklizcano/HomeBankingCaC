package com.cac.homebanking.service;

import com.cac.homebanking.client.DTO.USDResponse;
import com.cac.homebanking.client.DollarApiClient;
import com.cac.homebanking.event.publisher.TransferPublisher;
import com.cac.homebanking.exception.BusinessException;
import com.cac.homebanking.exception.InsufficientFundsException;
import com.cac.homebanking.exception.NotFoundException;
import com.cac.homebanking.mapper.TransferMapper;
import com.cac.homebanking.model.Transfer;
import com.cac.homebanking.model.TransferStatus;
import com.cac.homebanking.model.dto.AccountDto;
import com.cac.homebanking.model.dto.TransferDto;
import com.cac.homebanking.repository.TransferRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class TransferService {
    private final TransferRepository transferRepository;
    private final AccountService accountService;
    private final TransferPublisher transferPublisher;
    private final DollarApiClient dollarApiClient;

    TransferService(final TransferRepository transferRepository,
                    final AccountService accountService,
                    final TransferPublisher transferPublisher, DollarApiClient dollarApiClient) {
        this.transferPublisher = transferPublisher;
        this.transferRepository = transferRepository;
        this.accountService = accountService;
        this.dollarApiClient = dollarApiClient;
    }

    public List<TransferDto> getTransfers() {
        return transferRepository.findAll()
                .stream()
                .map(TransferMapper::transferEntityToDTO)
                .toList();
    }

    public TransferDto getTransferById(String transferId) throws NotFoundException {
        Transfer transfer = transferRepository.findById(transferId).orElseThrow(() ->
                new NotFoundException("The transfer is not found with id " + transferId));
        return TransferMapper.transferEntityToDTO(transfer);
    }

    public void publish(TransferDto message) {
        transferPublisher.publish(message);
    }

    private record AccountPair(AccountDto originAccount, AccountDto targetAccount) {}

    public TransferDto performTransfer(TransferDto transferDTO) throws BusinessException {
        try {
            AccountPair accounts = resolveAccounts(transferDTO);
            if (accounts.originAccount.getCbu().equals(accounts.targetAccount.getCbu())) {
                throw new BusinessException("The origin and target accounts are the same", HttpStatus.BAD_REQUEST);
            }
            executeTransfer(accounts.originAccount, accounts.targetAccount, transferDTO.getAmount());
        } catch (Exception e) {
            transferDTO.setStatus(TransferStatus.FAILED);
            transferRepository.save(TransferMapper.transferDTOToEntity(transferDTO));
            throw new BusinessException("An error occurred while performing the transfer: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        transferDTO.setStatus(TransferStatus.COMPLETED);
        Transfer transfer = TransferMapper.transferDTOToEntity(transferDTO);
        return TransferMapper.transferEntityToDTO(transferRepository.save(transfer));
    }

    private AccountPair resolveAccounts(TransferDto transferDTO) throws BusinessException, NotFoundException {
        return switch (transferDTO.getIdentifierType()) {
            case ID -> new AccountPair(
                    accountService.getAccountById(transferDTO.getOriginId()),
                    accountService.getAccountById(transferDTO.getTargetId())
            );
            case CBU -> new AccountPair(
                    accountService.getAccountByCBU(transferDTO.getOriginId()),
                    accountService.getAccountByCBU(transferDTO.getTargetId())
            );
            case ALIAS -> new AccountPair(
                    accountService.getAccountByAlias(transferDTO.getOriginId()),
                    accountService.getAccountByAlias(transferDTO.getTargetId())
            );
        };
    }

    private Boolean isDifferentCurrency(AccountDto originAccount, AccountDto targetAccount) {
        return !originAccount.getCurrency().name()
                .equalsIgnoreCase(targetAccount.getCurrency().name());
    }

    private void executeTransfer(AccountDto originAccount, AccountDto targetAccount, BigDecimal amount)
            throws InsufficientFundsException {
        if (isDifferentCurrency(originAccount, targetAccount)) {
            USDResponse dollarResponse = dollarApiClient.getOfficialUSD();
            BigDecimal conversionRate = new BigDecimal(dollarResponse.getSellPrice());
            BigDecimal targetAmount = getConversionAmount(originAccount, targetAccount, amount, conversionRate);

            accountService.withdraw(amount, originAccount);
            accountService.deposit(targetAmount, targetAccount);

        } else {
            accountService.withdraw(amount, originAccount);
            accountService.deposit(amount, targetAccount);
        }
    }

    private BigDecimal getConversionAmount(AccountDto originAccount, AccountDto targetAccount,
                                           BigDecimal originAmount, BigDecimal conversionRate) {
        if (originAccount.getCurrency().name().equalsIgnoreCase("ARS") &&
                targetAccount.getCurrency().name().equalsIgnoreCase("USD")) {
            // De ARS a USD
            return originAmount.divide(conversionRate, 2, RoundingMode.HALF_UP);
        } else {
            return originAmount.multiply(conversionRate);
        }
    }
}