package com.example.klasha.config;


import com.example.klasha.api.clients.okhttp.OkHttpProcessing;
import com.example.klasha.services.PopulationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

@Slf4j
@Configuration
public class PopulationServiceConfig {
  @Bean
  public PopulationService populationService(CountryProps countryProps, OkHttpProcessing okHttpProcessing) {
    return new PopulationService(countryProps, okHttpProcessing);
  }
//  @Bean
//  public MethodValidationPostProcessor methodValidationPostProcessor() {
//    return new MethodValidationPostProcessor();
//  }

  @Bean
  public Executor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(75);
    executor.setMaxPoolSize(100);
    executor.setQueueCapacity(75);
    executor.setThreadNamePrefix("Klasha -");
    executor.initialize();
    return executor;
  }
}
