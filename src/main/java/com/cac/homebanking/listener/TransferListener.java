package com.cac.homebanking.listener;

import com.cac.homebanking.exception.BusinessException;
import com.cac.homebanking.model.DTO.TransferDTO;
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
  public void processTransfer(TransferDTO transferDTO) {
    log.info("Message read from queue transfers-queue, transferId: {}", transferDTO.getUuid());
    try {
      transferService.performTransfer(transferDTO);
      log.info("Transfer processed successfully for UUID: {}. Sending ACK.", transferDTO.getUuid());
    } catch (Exception e) {
      log.error("Error processing transfer UUID: {}. Sending NACK.", transferDTO.getUuid(), e);
      throw new BusinessException("Failed to process transfer", HttpStatus.INTERNAL_SERVER_ERROR, e);
    }
  }
}