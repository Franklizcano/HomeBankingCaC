package com.cac.homebanking.mapper;

import com.cac.homebanking.model.dto.TransferDto;
import com.cac.homebanking.model.Transfer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransferMapper {

    public TransferDto transferEntityToDTO(Transfer transfer) {
        TransferDto transferDTO = new TransferDto();
        transferDTO.setId(transfer.getId());
        transferDTO.setOriginId(transfer.getOriginId());
        transferDTO.setTargetId(transfer.getTargetId());
        transferDTO.setDate(transfer.getDate());
        transferDTO.setAmount(transfer.getAmount());
        transferDTO.setStatus(transfer.getStatus());
        return transferDTO;
    }

    public Transfer transferDTOToEntity(TransferDto transferDTO) {
        Transfer transfer = new Transfer();
        transfer.setId(transferDTO.getId());
        transfer.setOriginId(transferDTO.getOriginId());
        transfer.setTargetId(transferDTO.getTargetId());
        transfer.setDate(transferDTO.getDate());
        transfer.setAmount(transferDTO.getAmount());
        transfer.setStatus(transferDTO.getStatus());
        return transfer;
    }
}
