package com.cac.homebanking.listener;

import com.cac.homebanking.model.DTO.TransferDTO;
import com.cac.homebanking.service.TransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransferListener {

  private final TransferService transferService;

  public TransferListener(final TransferService transferService) {
    this.transferService = transferService;
  }

  @RabbitListener(queues = "${queue.name}")
  public void processTransfer(TransferDTO transferDTO) {
    log.info("Message read from myQueue: {}", transferDTO.getUuid());
    transferService.performTransfer(transferDTO);
  }
}