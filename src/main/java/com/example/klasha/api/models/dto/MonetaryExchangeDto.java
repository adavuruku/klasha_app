package com.example.klasha.api.models.dto;

import lombok.Data;

@Data
public class MonetaryExchangeDto {
    private String sourceCountry;
    private String targetCountry;
    private Double rate;
}
