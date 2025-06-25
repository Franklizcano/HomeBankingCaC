package com.cac.homebanking.service;

import com.cac.homebanking.client.DTO.USDResponse;
import com.cac.homebanking.client.DollarApiClient;
import com.cac.homebanking.exception.BusinessException;
import com.cac.homebanking.exception.InsufficientFundsException;
import com.cac.homebanking.exception.NotFoundException;
import com.cac.homebanking.mapper.TransferMapper;
import com.cac.homebanking.model.DTO.AccountDTO;
import com.cac.homebanking.model.DTO.TransferDTO;
import com.cac.homebanking.model.Transfer;
import com.cac.homebanking.model.TransferStatus;
import com.cac.homebanking.publisher.TransferPublisher;
import com.cac.homebanking.repository.TransferRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

    public List<TransferDTO> getTransfers() {
        return transferRepository.findAll()
                .stream()
                .map(TransferMapper::transferEntityToDTO)
                .toList();
    }

    public TransferDTO getTransferById(Long transferId) throws NotFoundException {
        Transfer transfer = transferRepository.findById(transferId).orElseThrow(() ->
                new NotFoundException("The transfer is not found with id " + transferId));
        return TransferMapper.transferEntityToDTO(transfer);
    }

    public TransferDTO update(Long id, TransferDTO transferDTO) throws NotFoundException {
        Optional<Transfer> transferCreated = transferRepository.findById(id);

        if  (transferCreated.isPresent()) {
            Transfer entity = transferCreated.get();
            Transfer accountUpdated = TransferMapper.transferDTOToEntity(transferDTO);
            accountUpdated.setId(entity.getId());
            Transfer saved = transferRepository.save(accountUpdated);
            return TransferMapper.transferEntityToDTO(saved);
        } else {
            throw new NotFoundException("User not found with the id " + id);
        }
    }

    public String delete(Long id) {
        if (transferRepository.existsById(id)) {
            transferRepository.deleteById(id);
            return "The transfer has been deleted.";
        } else {
            return "The transfer has not been deleted";
        }
    }

    public void publish(TransferDTO message) {
        transferPublisher.publish(message);
    }

    public TransferDTO performTransfer(TransferDTO transferDTO) throws BusinessException {
        try {
            AccountDTO originAccount = accountService.getAccountById(transferDTO.getOriginId());
            AccountDTO targetAccount = accountService.getAccountById(transferDTO.getTargetId());
            if (originAccount.getId().equals(targetAccount.getId())) {
                throw new BusinessException("The origin and target accounts are the same", HttpStatus.BAD_REQUEST);
            }
            executeTransfer(originAccount, targetAccount, transferDTO.getAmount());
        } catch (Exception e) {
            transferDTO.setStatus(TransferStatus.FAILED);
            transferRepository.save(TransferMapper.transferDTOToEntity(transferDTO));
            throw new BusinessException("An error occurred while performing the transfer: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        transferDTO.setStatus(TransferStatus.COMPLETED);
        Transfer transfer = TransferMapper.transferDTOToEntity(transferDTO);
        return TransferMapper.transferEntityToDTO(transferRepository.save(transfer));
    }

    private Boolean isDifferentCurrency(AccountDTO originAccount, AccountDTO targetAccount) {
      return !originAccount.getCurrency().name()
          .equalsIgnoreCase(targetAccount.getCurrency().name());
    }

    private void executeTransfer(AccountDTO originAccount, AccountDTO targetAccount, BigDecimal amount)
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

    private BigDecimal getConversionAmount(AccountDTO originAccount, AccountDTO targetAccount,
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

