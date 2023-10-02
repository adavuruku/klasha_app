package com.example.klasha.api.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CurrencyDto {
    private String name;
    private String currency;
    private String iso3;
    private String iso2;
}
