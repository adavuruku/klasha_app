package com.example.klasha.api.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CountryPopulationDetailDto {
    private String country;
    private String iso3;
    private String code;
    List<PopulationRecordDto> populationCounts;
}
