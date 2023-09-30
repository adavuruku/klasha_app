package com.example.klasha.api.models.thirdparty.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class PopulationFilterResponse {
    private String msg;
    private Boolean error;
    private List<CityRecord> data;
}
