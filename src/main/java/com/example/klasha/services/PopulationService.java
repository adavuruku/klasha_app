package com.example.klasha.services;

import com.example.klasha.api.clients.PopulationClientFeignt;
import com.example.klasha.api.clients.spring.PopulationClient;
import com.example.klasha.api.models.thirdparty.request.PopulationFilterRequest;
import com.example.klasha.api.models.thirdparty.response.CityRecord;
import com.example.klasha.api.models.thirdparty.response.PopulationFilterResponse;
import com.example.klasha.config.CountryProps;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class PopulationService {
    private final PopulationClientFeignt populationClientFeignt;
    private final CountryProps countryProps;

    private final PopulationClient populationClient;
    public PopulationService(PopulationClientFeignt populationClientFeignt, CountryProps countryProps, PopulationClient populationClient) {
        this.populationClient = populationClient;
        this.populationClientFeignt = populationClientFeignt;
        this.countryProps = countryProps;
    }

    public List<CityRecord> processPopulationFilter(Long noOfCities){
        PopulationFilterRequest populationFilterRequest = new PopulationFilterRequest();
        populationFilterRequest.setCountry(countryProps.getCountries().get(0).toString());

        try {
//            Optional<PopulationFilterResponse> filterResponse = populationClient.filterPopulationByCountry(populationFilterRequest);
//            if (filterResponse.isPresent()) {
//                return filterResponse.get().getData();
//            }
//            PopulationFilterResponse filterResponse = populationClient.filterPopulationByCountry(populationFilterRequest);
            PopulationFilterResponse filterResponse = populationClient.filterPopulationByCountryByGet(countryProps.getCountries().get(0).toString());
            log.info("population filter request body {} ", new ObjectMapper().writeValueAsString(filterResponse));
            log.info("population filter request body {} ", new ObjectMapper().writeValueAsString(populationClient.filterPopulationByCountry(populationFilterRequest)));
//
            if (filterResponse != null) {
                return filterResponse.getData();
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
