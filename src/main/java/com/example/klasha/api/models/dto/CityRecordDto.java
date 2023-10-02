package com.example.klasha.api.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CityRecordDto {
    private String city;
    private String country;
    private List<PopulationRecordDto> populationCounts;
}
