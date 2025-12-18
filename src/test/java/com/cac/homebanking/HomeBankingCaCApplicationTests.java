package com.cac.homebanking;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import io.awspring.cloud.autoconfigure.sqs.SqsAutoConfiguration;

@SpringBootTest
@EnableAutoConfiguration(exclude = {SqsAutoConfiguration.class})
class HomeBankingCaCApplicationTests {

  @Test
  void contextLoads() {
    // This test will pass if the application context loads successfully
  }
}