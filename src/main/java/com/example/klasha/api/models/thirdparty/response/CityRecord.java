package com.example.klasha.api.models.thirdparty.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class CityRecord {
    private String city;
    private String country;
    private List<PopulationRecord> populationCounts;
}
