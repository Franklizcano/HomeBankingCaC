package com.cac.homebanking.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import io.awspring.cloud.sqs.support.converter.SqsMessagingMessageConverter;
import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class SqsConfig {

  @Value("${spring.cloud.aws.sqs.endpoint}")
  private String sqsEndpoint;

  @Bean
  public SqsAsyncClient sqsAsyncClient() {
    return SqsAsyncClient.builder()
        .region(Region.US_EAST_1)
        .endpointOverride(URI.create(sqsEndpoint))
        .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials
            .create("test", "test")))
        .build();
  }

  @Bean
  public SqsTemplate sqsTemplate(SqsAsyncClient sqsAsyncClient, ObjectMapper defaultObjectMapper) {
    var converter = new SqsMessagingMessageConverter();
    converter.setObjectMapper(defaultObjectMapper);
    return SqsTemplate.builder().sqsAsyncClient(sqsAsyncClient).messageConverter(converter).build();
  }
}
