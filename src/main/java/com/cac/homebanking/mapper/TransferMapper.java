package com.cac.homebanking.mapper;

import com.cac.homebanking.model.DTO.TransferDTO;
import com.cac.homebanking.model.Transfer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransferMapper {

    public TransferDTO transferEntityToDTO(Transfer transfer) {
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setId(transfer.getId());
        transferDTO.setOriginId(transfer.getOriginId());
        transferDTO.setTargetId(transfer.getTargetId());
        transferDTO.setDate(transfer.getDate());
        transferDTO.setAmount(transfer.getAmount());
        return transferDTO;
    }

    public Transfer transferDTOToEntity(TransferDTO transferDTO) {
        Transfer transfer = new Transfer();
        transfer.setId(transferDTO.getId());
        transfer.setOriginId(transferDTO.getOriginId());
        transfer.setTargetId(transferDTO.getTargetId());
        transfer.setDate(transferDTO.getDate());
        transfer.setAmount(transferDTO.getAmount());
        return transfer;
    }
}
