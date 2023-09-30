package com.example.klasha.services;

import com.example.klasha.api.clients.PopulationClient;
import com.example.klasha.api.models.thirdparty.request.PopulationFilterRequest;
import com.example.klasha.api.models.thirdparty.response.CityRecord;
import com.example.klasha.api.models.thirdparty.response.PopulationFilterResponse;
import com.example.klasha.config.CountryProps;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class PopulationService {
    private final PopulationClient populationClient;
    private final CountryProps countryProps;
    public PopulationService(PopulationClient populationClient, CountryProps countryProps) {
        this.populationClient = populationClient;
        this.countryProps = countryProps;
    }

    public List<CityRecord> processPopulationFilter(Long noOfCities){
        PopulationFilterRequest populationFilterRequest = new PopulationFilterRequest();
        populationFilterRequest.setCountry(countryProps.getCountries().get(0).toString());

        try {
            Optional<PopulationFilterResponse> filterResponse = populationClient.filterPopulation(populationFilterRequest);
            if (filterResponse.isPresent()) {
                return filterResponse.get().getData();
            }
            return null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
