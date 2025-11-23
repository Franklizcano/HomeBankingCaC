package com.cac.homebanking.event.publisher;

import com.cac.homebanking.exception.BusinessException;
import com.cac.homebanking.model.dto.TransferDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Component
@Slf4j
public class TransferPublisher {

  private final SqsAsyncClient sqsAsyncClient;
  private final ObjectMapper objectMapper;

  @Value("${queue.transfers}")
  private String queueName;

  public TransferPublisher(SqsAsyncClient sqsAsyncClient, ObjectMapper objectMapper) {
    this.sqsAsyncClient = sqsAsyncClient;
    this.objectMapper = objectMapper;
  }

  public void publish(TransferDto transferDTO) {
    try {
      sendMessage(transferDTO);
    } catch (Exception e) {
      log.error("Error sending message to SQS queue: {}, transferId: {}", queueName, transferDTO.getId(), e);
      throw new BusinessException("Failed to send message to SQS queue ", HttpStatus.INTERNAL_SERVER_ERROR ,e);
    }
  }

  private void sendMessage(TransferDto transferDTO) throws JsonProcessingException {
    SendMessageRequest message = SendMessageRequest.builder().queueUrl(queueName)
        .messageBody(objectMapper.writeValueAsString(transferDTO)).build();
    sqsAsyncClient.sendMessage(message).join();
    log.info("Message sent to SQS queue: {}, transferId: {}", queueName, transferDTO.getId());
  }
}