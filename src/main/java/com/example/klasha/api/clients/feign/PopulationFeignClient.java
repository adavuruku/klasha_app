package com.example.klasha.api.clients.feign;

import com.example.klasha.api.models.thirdparty.request.PopulationFilterRequest;
import com.example.klasha.api.models.thirdparty.response.PopulationFilterResponse;
import com.example.klasha.config.feignclient.population.PopulationFeignClientRequestInterceptor;
import com.example.klasha.config.feignclient.population.PopulationFeignErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.http.ResponseEntity;

//@HttpExchange(url ="/api/v0.1/countries/population", accept = MediaType.APPLICATION_JSON_VALUE, contentType = MediaType.APPLICATION_JSON_VALUE)
@FeignClient(name = "population-service", url = "${population.base_url}",configuration = {
        PopulationFeignClientRequestInterceptor.class, PopulationFeignErrorDecoder.class})
public interface PopulationFeignClient {
  @PostExchange(value = "/population/cities/filter")
  PopulationFilterResponse filterPopulationByCountry(@RequestBody PopulationFilterRequest populationFilterRequest);
}
