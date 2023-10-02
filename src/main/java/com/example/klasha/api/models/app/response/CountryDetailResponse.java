package com.example.klasha.api.models.app.response;

import com.example.klasha.api.models.dto.CountryLocationDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CountryDetailResponse {
    private Long population;
    private String capitalCity;
    private CountryLocationDto location;
    private String currency;
    private String iso2;
    private String iso3;
}
