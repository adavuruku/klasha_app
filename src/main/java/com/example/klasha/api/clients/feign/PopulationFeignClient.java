package com.example.klasha.api.clients.feign;

import com.example.klasha.api.models.thirdparty.request.PopulationFilterRequest;
import com.example.klasha.api.models.thirdparty.response.PopulationFilterResponse;
import com.example.klasha.config.feignclient.population.PopulationFeignClientRequestInterceptor;
import com.example.klasha.config.feignclient.population.PopulationFeignErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "population-service", url = "${population.base_url}",configuration = {
        PopulationFeignClientRequestInterceptor.class, PopulationFeignErrorDecoder.class})
public interface PopulationFeignClient {
  @PostMapping(value = "/population/cities/filter", consumes = APPLICATION_JSON_VALUE)
  PopulationFilterResponse filterPopulationByCountry(@RequestBody PopulationFilterRequest populationFilterRequest);
}
