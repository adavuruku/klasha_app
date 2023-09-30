package com.example.klasha.config;

import com.example.klasha.api.clients.PopulationClientFeignt;
import com.example.klasha.api.clients.feign.PopulationFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public class PopulationClientConfigFeign {

  @Bean
  public PopulationClientFeignt vaultClient(PopulationFeignClient populationFeignClient) {

    return new PopulationClientFeignt(populationFeignClient);
  }
}
