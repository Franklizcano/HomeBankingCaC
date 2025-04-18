package com.cac.homebanking.publisher;

import com.cac.homebanking.model.DTO.TransferDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
@Slf4j
public class TransferPublisher {

  private final RabbitTemplate rabbitTemplate;
  private final Queue queue;

  public TransferPublisher(final RabbitTemplate rabbitTemplate, final Queue queue) {
    this.rabbitTemplate = rabbitTemplate;
    this.queue = queue;
  }

  public void publish(TransferDTO transferDTO) {
    rabbitTemplate.convertAndSend(queue.getName(), transferDTO);
    log.info("Message published to queue");
  }
}