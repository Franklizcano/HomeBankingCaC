package com.cac.homebanking.listener;

import com.cac.homebanking.exception.BusinessException;
import com.cac.homebanking.model.DTO.TransferDTO;
import com.cac.homebanking.service.TransferService;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransferListener {

  private final TransferService transferService;

  public TransferListener(final TransferService transferService) {
    this.transferService = transferService;
  }

  @RabbitListener(queues = "${queue.name}")
  public void processTransfer(TransferDTO transferDTO, Channel channel,
      @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
    log.info("Message read from queue: test-queue, transferId: {}", transferDTO.getUuid());
    try {
      transferService.performTransfer(transferDTO);
      log.info("Transfer processed successfully for UUID: {}. Sending ACK.", transferDTO.getUuid());
      channel.basicAck(tag, false);
    } catch (BusinessException e) {
      log.error("Error processing transfer UUID: {}. Sending NACK.", transferDTO.getUuid(), e);
      channel.basicNack(tag, false, false);
    }
  }
}