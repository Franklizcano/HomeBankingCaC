package com.cac.homebanking.service;

import com.cac.homebanking.exception.NotFoundException;
import com.cac.homebanking.mapper.TransferMapper;
import com.cac.homebanking.model.Transfer;
import com.cac.homebanking.model.DTO.TransferDTO;
import com.cac.homebanking.repository.TransferRepository;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {
    private final TransferRepository transferRepository;
    private final AccountService accountService;

    TransferService(final TransferRepository transferRepository,
                           final AccountService accountService) {
        this.transferRepository = transferRepository;
        this.accountService = accountService;
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
        Optional<Transfer> trasnferCreated = transferRepository.findById(id);

        if  (trasnferCreated.isPresent()) {
            Transfer entity = trasnferCreated.get();
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

    @Transactional
    public TransferDTO performTransfer(TransferDTO transferDTO) throws NotFoundException {
        accountService.withdraw(transferDTO.getAmount(), transferDTO.getOriginId());
        accountService.deposit(transferDTO.getAmount(), transferDTO.getTargetId());

        Transfer transfer = TransferMapper.transferDTOToEntity(transferDTO);

        ZonedDateTime date = ZonedDateTime.now();
        // Seteamos el objeto fecha actual en el transferDto
        transfer.setDate(date);
        transfer = transferRepository.save(transfer);

        return TransferMapper.transferEntityToDTO(transfer);
    }
}
