package com.example.klasha.api.models.app.response;

import com.example.klasha.api.models.dto.CountryLocationDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CurrencyConversionResponse {
    private Double amount;
    private String currency;
}
