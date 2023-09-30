package com.example.klasha.config;


import com.example.klasha.api.clients.PopulationClientFeignt;
import com.example.klasha.api.clients.spring.PopulationClient;
import com.example.klasha.services.PopulationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class PopulationServiceConfig {
  @Bean
  public PopulationService populationService(PopulationClientFeignt populationClientFeignt, CountryProps countryProps,
                                             PopulationClient populationClient) {
    return new PopulationService(populationClientFeignt,countryProps,populationClient);
  }
}
