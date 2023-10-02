package com.example.klasha.api.models.app.response;

import com.example.klasha.api.models.dto.CountryLocationDto;
import com.example.klasha.api.models.dto.StateCity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CountryStateCityResponse {
    private String country;
    List<StateCity> stateCity;
}
