package com.example.klasha.api.clients.spring;

import com.example.klasha.api.models.thirdparty.request.PopulationFilterRequest;
import com.example.klasha.api.models.thirdparty.response.PopulationFilterResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(url ="/population", accept = MediaType.APPLICATION_JSON_VALUE, contentType = MediaType.APPLICATION_JSON_VALUE)
public interface PopulationClient {
    @PostExchange(value = "/cities/filter")
    PopulationFilterResponse filterPopulationByCountry(@RequestBody PopulationFilterRequest populationFilterRequest);

    @GetExchange(value = "/cities/filter/q")
    PopulationFilterResponse filterPopulationByCountryByGet(@RequestParam("country") String country);
}