package com.example.klasha.config;


import com.example.klasha.services.PopulationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
public class PopulationServiceConfig {
  @Bean
  public PopulationService populationService( CountryProps countryProps) {
    return new PopulationService(countryProps);
  }
}
