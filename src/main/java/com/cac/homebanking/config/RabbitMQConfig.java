package com.cac.homebanking.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  @Value("${queue.name}")
  private String queueName;

  @Bean
  Queue transferQueue() {
    return new Queue(queueName, true);
  }

  /*@Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
      ConnectionFactory connectionFactory) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
    return factory;
  }*/

  @Bean
  public MessageConverter jsonToMapMessageConverter() {
    DefaultClassMapper defaultClassMapper = new DefaultClassMapper();
    defaultClassMapper.setTrustedPackages("com.cac.homebanking.model.DTO"); // trusted packages
    Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
    jackson2JsonMessageConverter.setClassMapper(defaultClassMapper);
    return jackson2JsonMessageConverter;
  }
}