package com.cac.homebanking.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class ApplicationConfiguration {

    @Bean
    @Primary
    public ObjectMapper defaultObjectMapper() {
      return Jackson2ObjectMapperBuilder.json()
          .propertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)
          .serializationInclusion(JsonInclude.Include.NON_NULL)
          .failOnUnknownProperties(false)
          .failOnEmptyBeans(false)
          .build();
    }
}
