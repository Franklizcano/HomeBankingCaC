package com.cac.homebanking.event.listener;

import com.cac.homebanking.exception.BusinessException;
import com.cac.homebanking.model.dto.TransferDto;
import com.cac.homebanking.service.TransferService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransferListener {

    private final TransferService transferService;

    public TransferListener(final TransferService transferService) {
        this.transferService = transferService;
    }

    @SqsListener("${queue.transfers}")
    public void processTransfer(TransferDto transferDto) {
        log.info("Message read from queue transfers-queue, transferId: {}", transferDto.getId());
        try {
            transferService.performTransfer(transferDto);
            log.info("Transfer processed successfully for UUID: {}. Sending ACK.", transferDto.getId());
        } catch (Exception e) {
            log.error("Error processing transfer UUID: {}. Sending NACK.", transferDto.getId(), e);
            throw new BusinessException("Failed to process transfer", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}