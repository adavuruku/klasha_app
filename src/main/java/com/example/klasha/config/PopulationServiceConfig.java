package com.example.klasha.config;


import com.example.klasha.api.clients.PopulationClient;
import com.example.klasha.services.PopulationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class PopulationServiceConfig {
  @Bean
  public PopulationService populationService(PopulationClient populationClient, CountryProps countryProps) {
    return new PopulationService(populationClient,countryProps);
  }
}
