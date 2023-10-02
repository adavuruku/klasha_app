package com.example.klasha.api.models.app.response;

import com.example.klasha.api.models.dto.PopulationRecordDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
public class PopulationCityResponse {
    private String city;
    private String country;
    private PopulationRecordDto populationCounts;
}
